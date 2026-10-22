package io.arkansasio.hp3000xl.packages

interface HpAppLoader {
    fun validate(manifest: HpAppManifest): List<String>
    fun install(manifest: HpAppManifest, payload: ByteArray): Result<Unit>
    fun uninstall(appId: String): Boolean
}
