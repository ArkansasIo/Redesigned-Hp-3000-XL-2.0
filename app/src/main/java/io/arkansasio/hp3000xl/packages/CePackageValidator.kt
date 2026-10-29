package io.arkansasio.hp3000xl.packages

/** Validates package metadata before it is shown to the user or installed. */
class CePackageValidator(private val currentApi: Int) {
    init {
        require(currentApi >= 1) { "Current API must be positive." }
    }

    fun validate(pkg: CePackage): List<String> = buildList {
        if (!pkg.id.matches(Regex("[a-z0-9][a-z0-9._-]{0,127}"))) {
            add("Package ID must be 1-128 lowercase letters, digits, dots, underscores or hyphens and start with a letter or digit")
        }
        if (pkg.name.isBlank()) add("Package name is required")
        if (pkg.version.isBlank()) add("Package version is required")
        if (pkg.publisher.isBlank()) add("Package publisher is required")
        if (pkg.minApi < 1) add("Package minimum API must be positive")
        else if (pkg.minApi > currentApi) add("Package requires Android API ${pkg.minApi}")
        if (pkg.entryPoint.isBlank()) add("Package entry point is required")
        if (pkg.permissions.any { it.isBlank() }) add("Package contains an empty permission")
    }

    fun isValid(pkg: CePackage): Boolean = validate(pkg).isEmpty()
}
