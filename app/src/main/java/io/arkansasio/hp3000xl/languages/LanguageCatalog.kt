package io.arkansasio.hp3000xl.languages

data class LanguageDefinition(
    val id: String,
    val name: String,
    val extensions: List<String>,
    val runtime: String,
    val enabledByDefault: Boolean = false
)

object LanguageCatalog {
    val supported = listOf(
        LanguageDefinition("kotlin", "Kotlin", listOf(".kt", ".kts"), "Android/Kotlin toolchain", true),
        LanguageDefinition("java", "Java", listOf(".java"), "JVM/Android toolchain", true),
        LanguageDefinition("cpp", "C++", listOf(".cpp", ".cc", ".h", ".hpp"), "NDK/native toolchain"),
        LanguageDefinition("c", "C", listOf(".c", ".h"), "NDK/native toolchain"),
        LanguageDefinition("rust", "Rust", listOf(".rs"), "Rust toolchain"),
        LanguageDefinition("python", "Python", listOf(".py"), "Python runtime"),
        LanguageDefinition("lua", "Lua", listOf(".lua"), "Embedded Lua runtime"),
        LanguageDefinition("javascript", "JavaScript", listOf(".js", ".mjs"), "JavaScript runtime"),
        LanguageDefinition("typescript", "TypeScript", listOf(".ts", ".tsx"), "TypeScript/Node toolchain"),
        LanguageDefinition("shell", "Shell", listOf(".sh"), "POSIX-compatible shell"),
        LanguageDefinition("basic", "BASIC 2026", listOf(".bas"), "Embedded BASIC interpreter"),
        LanguageDefinition("asm", "Assembly", listOf(".asm", ".s"), "Architecture-specific assembler")
    )
}
