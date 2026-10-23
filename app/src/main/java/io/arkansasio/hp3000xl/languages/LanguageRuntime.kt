package io.arkansasio.hp3000xl.languages

/** Runtime capability metadata; this layer does not bundle native compilers. */
data class LanguageRuntime(
    val languageId: String,
    val available: Boolean,
    val executable: String? = null,
    val notes: String = ""
)

class LanguageRuntimeRegistry {
    private val runtimes = linkedMapOf<String, LanguageRuntime>()

    init {
        LanguageCatalog.supported.forEach { definition ->
            runtimes[definition.id] = LanguageRuntime(
                languageId = definition.id,
                available = false,
                notes = "Toolchain integration point"
            )
        }
    }

    fun register(runtime: LanguageRuntime) { runtimes[runtime.languageId] = runtime }
    fun get(languageId: String): LanguageRuntime? = runtimes[languageId]
    fun all(): List<LanguageRuntime> = runtimes.values.toList()
}
