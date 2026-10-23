package io.arkansasio.hp3000xl.apps.development

data class DevelopmentTool(val id: String, val name: String, val fileExtensions: List<String>, val executionMode: String)

object DevelopmentCatalog {
    val tools = listOf(
        DevelopmentTool("code-editor", "Code Editor", listOf(".txt", ".md", ".code"), "editor"),
        DevelopmentTool("basic", "BASIC 2026", listOf(".bas"), "sandbox"),
        DevelopmentTool("lua", "Lua", listOf(".lua"), "sandbox"),
        DevelopmentTool("python", "Python", listOf(".py"), "sandbox"),
        DevelopmentTool("javascript", "JavaScript", listOf(".js"), "sandbox"),
        DevelopmentTool("typescript", "TypeScript", listOf(".ts", ".tsx"), "sandbox"),
        DevelopmentTool("shell", "Shell", listOf(".sh"), "allow-listed"),
        DevelopmentTool("c", "C", listOf(".c", ".h"), "external-toolchain"),
        DevelopmentTool("cpp", "C++", listOf(".cpp", ".hpp"), "external-toolchain"),
        DevelopmentTool("rust", "Rust", listOf(".rs"), "external-toolchain"),
        DevelopmentTool("java", "Java", listOf(".java"), "external-toolchain"),
        DevelopmentTool("kotlin", "Kotlin", listOf(".kt", ".kts"), "external-toolchain"),
        DevelopmentTool("assembly", "Assembly", listOf(".asm", ".s"), "architecture-specific")
    )
}
