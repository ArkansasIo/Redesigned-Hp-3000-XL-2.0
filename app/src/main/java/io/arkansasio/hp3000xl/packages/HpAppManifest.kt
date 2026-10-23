package io.arkansasio.hp3000xl.packages

data class HpAppManifest(
    val id: String,
    val name: String,
    val version: String,
    val entryPoint: String,
    val minApi: Int = 1,
    val permissions: List<String> = emptyList(),
    val publisher: String = "ArkansasIo"
)

object HpAppManifestValidator {
    fun validate(manifest: HpAppManifest): List<String> {
        val errors = mutableListOf<String>()
        if (!manifest.id.matches(Regex("[a-z0-9][a-z0-9._-]*"))) errors += "invalid application id"
        if (manifest.name.isBlank()) errors += "missing application name"
        if (manifest.version.isBlank()) errors += "missing version"
        if (manifest.entryPoint.isBlank()) errors += "missing entry point"
        if (manifest.minApi < 1) errors += "invalid minimum API"
        return errors
    }
}
