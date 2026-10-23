package io.arkansasio.hp3000xl.packages

class CePackageValidator(private val currentApi: Int) {
    fun validate(pkg: CePackage): List<String> = buildList {
        if (pkg.id.isBlank()) add("Package ID is required")
        if (pkg.name.isBlank()) add("Package name is required")
        if (pkg.version.isBlank()) add("Package version is required")
        if (pkg.minApi > currentApi) add("Package requires Android API ${pkg.minApi}")
        if (pkg.entryPoint.isBlank()) add("Package entry point is required")
        if (pkg.permissions.any { it.isBlank() }) add("Package contains an empty permission")
    }

    fun isValid(pkg: CePackage): Boolean = validate(pkg).isEmpty()
}
