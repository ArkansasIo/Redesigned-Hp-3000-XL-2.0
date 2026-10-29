package io.arkansasio.hp3000xl.packages

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CePackageValidatorTest {
    private val validator = CePackageValidator(currentApi = 35)

    private fun validPackage(
        id: String = "com.example.notes",
        minApi: Int = 29,
        permissions: Set<String> = setOf("storage.read")
    ) = CePackage(
        id = id,
        name = "Notes",
        version = "1.0.0",
        publisher = "Example",
        minApi = minApi,
        permissions = permissions,
        entryPoint = "notes"
    )

    @Test
    fun acceptsValidPackageMetadata() {
        assertTrue(validator.validate(validPackage()).isEmpty())
        assertTrue(validator.isValid(validPackage()))
    }

    @Test
    fun rejectsMalformedPackageId() {
        assertFalse(validator.isValid(validPackage(id = "../notes")))
        assertFalse(validator.isValid(validPackage(id = "UpperCase")))
    }

    @Test
    fun rejectsUnsupportedAndInvalidMinimumApi() {
        assertTrue(validator.validate(validPackage(minApi = 36)).any { it.contains("requires Android API 36") })
        assertTrue(validator.validate(validPackage(minApi = 0)).any { it.contains("must be positive") })
    }

    @Test
    fun rejectsEmptyPermissions() {
        assertTrue(validator.validate(validPackage(permissions = setOf("storage.read", " ")))
            .any { it.contains("empty permission") })
    }

    @Test
    fun rejectsMissingRequiredMetadata() {
        val invalid = CePackage(
            id = "",
            name = " ",
            version = "",
            publisher = "",
            minApi = 1,
            entryPoint = " "
        )
        assertFalse(validator.isValid(invalid))
        assertTrue(validator.validate(invalid).size >= 5)
    }
}
