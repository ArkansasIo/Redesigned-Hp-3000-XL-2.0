package io.arkansasio.hp3000xl.update

interface UpdateService {
    suspend fun check(channel: String = "pre-alpha"): UpdateManifest?
    suspend fun download(manifest: UpdateManifest): ByteArray
}
