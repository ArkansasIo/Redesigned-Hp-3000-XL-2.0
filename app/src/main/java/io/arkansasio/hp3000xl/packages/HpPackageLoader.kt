package io.arkansasio.hp3000xl.packages

import android.content.Context
import io.arkansasio.hp3000xl.core.CeOs
import java.io.File
import java.util.zip.ZipInputStream

/**
 * Loader for the sandboxed `.hpapp` package format of this recreation.
 *
 * A `.hpapp` file is a ZIP archive containing:
 *   manifest.json (or .properties-style text)  — package metadata
 *   payload/...                                — app assets
 *
 * Installation copies the payload into app-private storage and registers the
 * app in the [CeApplicationManager] registry. No native binaries are executed.
 */
class HpPackageLoader(private val context: Context) : HpAppLoader {

    private val installRoot: File get() = context.filesDir.resolve("apps")

    override fun validate(manifest: HpAppManifest): List<String> {
        val problems = mutableListOf<String>()
        if (manifest.id.isBlank()) problems += "package id is required"
        if (manifest.id.any { it !in allowedIdChars }) problems += "package id may only contain a-z, 0-9, ., -"
        if (manifest.name.isBlank()) problems += "package name is required"
        if (manifest.entryPoint.isBlank()) problems += "package entryPoint is required"
        if (manifest.minApi > BuildInfo_API) problems += "package requires API ${manifest.minApi}, this runtime provides $BuildInfo_API"
        return problems
    }

    override fun install(manifest: HpAppManifest, payload: ByteArray): Result<Unit> {
        val problems = validate(manifest)
        if (problems.isNotEmpty()) return Result.failure(IllegalArgumentException(problems.joinToString("; ")))
        return try {
            val target = installRoot.resolve(manifest.id).apply { mkdirs() }
            ZipInputStream(payload.inputStream()).use { zip ->
                var entry = zip.nextEntry
                while (entry != null) {
                    if (!entry.isDirectory) {
                        val name = entry.name.removePrefix("payload/")
                        if (name.isNotBlank() && !name.contains("..")) {
                            val out = target.resolve(name)
                            out.parentFile?.mkdirs()
                            out.outputStream().use { zip.copyTo(it) }
                        }
                    }
                    zip.closeEntry()
                    entry = zip.nextEntry
                }
            }
            CeOs.log("packages", "installed ${manifest.id} v${manifest.version}")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun uninstall(appId: String): Boolean {
        val target = installRoot.resolve(appId)
        val removed = target.deleteRecursively()
        if (removed) CeOs.log("packages", "uninstalled $appId")
        return removed
    }

    companion object {
        const val BuildInfo_API = 35
        private val allowedIdChars = ('a'..'z') + ('0'..'9') + '.' + '-'
    }
}
