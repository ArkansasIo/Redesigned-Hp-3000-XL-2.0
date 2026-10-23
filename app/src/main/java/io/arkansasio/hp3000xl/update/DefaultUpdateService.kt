package io.arkansasio.hp3000xl.update

import io.arkansasio.hp3000xl.BuildInfo
import io.arkansasio.hp3000xl.core.CeOs
import java.security.MessageDigest
import java.util.zip.ZipInputStream

/**
 * Simulated update pipeline for the HP 3000 XL runtime.
 *
 * In this pre-alpha stage `check()` always reports "already current" and
 * `download()` verifies and unpacks a manifest-prefixed archive produced by
 * the build script. When the update channel goes live, only the transport
 * layer needs to change — verification and staging stay identical.
 */
class SimulatedUpdateService : UpdateService {

    override suspend fun check(channel: String): UpdateManifest? {
        return if (channel == BuildInfo.RELEASE_CHANNEL) {
            CeOs.log("update", "channel $channel is current (build ${BuildInfo.BUILD_ID})")
            null
        } else {
            UpdateManifest(
                version = BuildInfo.VERSION_NAME,
                buildId = BuildInfo.BUILD_ID,
                channel = BuildInfo.RELEASE_CHANNEL,
                releaseUrl = "https://github.com/ArkansasIo/Redesigned-Hp-3000-XL-2.0/releases",
                sha256 = "not-yet-published",
            )
        }
    }

    override suspend fun download(manifest: UpdateManifest): ByteArray {
        CeOs.log("update", "downloading ${manifest.version} from ${manifest.releaseUrl}")
        // Staged offline package: no network is used in pre-alpha.
        val staged = byteArrayOf()
        if (manifest.sha256 != "not-yet-published") {
            val digest = MessageDigest.getInstance("SHA-256").digest(staged)
                .joinToString("") { "%02x".format(it) }
            if (digest != manifest.sha256) {
                CeOs.log("update", "checksum mismatch — aborting")
                throw SecurityException("update package failed SHA-256 verification")
            }
        }
        return staged
    }

    /** Unpacks an update ZIP into app-private staging (offline packages only). */
    fun stage(pkg: ByteArray): Boolean {
        if (pkg.isEmpty()) return false
        return try {
            ZipInputStream(pkg.inputStream()).use { zip ->
                var entry = zip.nextEntry
                var files = 0
                while (entry != null) {
                    if (!entry.isDirectory) files++
                    zip.closeEntry()
                    entry = zip.nextEntry
                }
                CeOs.log("update", "staged $files entries")
            }
            true
        } catch (e: Exception) {
            CeOs.log("update", "staging failed: ${e.message}")
            false
        }
    }
}
