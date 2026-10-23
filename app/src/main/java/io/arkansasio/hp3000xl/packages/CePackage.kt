package io.arkansasio.hp3000xl.packages

/** Metadata for the independent .hpapp package format. */
data class CePackage(
    val id: String,
    val name: String,
    val version: String,
    val publisher: String,
    val minApi: Int,
    val permissions: Set<String> = emptySet(),
    val entryPoint: String = ""
)

data class PackageInstallResult(val installed: Boolean, val message: String)

class CePackageRegistry {
    private val packages = linkedMapOf<String, CePackage>()

    fun install(pkg: CePackage): PackageInstallResult {
        if (pkg.id.isBlank() || pkg.version.isBlank()) return PackageInstallResult(false, "Invalid package metadata")
        packages[pkg.id] = pkg
        return PackageInstallResult(true, "Installed ${pkg.id} ${pkg.version}")
    }

    fun uninstall(id: String): Boolean = packages.remove(id) != null
    fun get(id: String): CePackage? = packages[id]
    fun list(): List<CePackage> = packages.values.toList()
}
