package io.arkansasio.hp3000xl.apps

data class LanguageProfile(val id: String, val name: String, val extension: String)

object DevelopmentApps {
    val languages = listOf(
        LanguageProfile("kotlin", "Kotlin", ".kt"),
        LanguageProfile("java", "Java", ".java"),
        LanguageProfile("c", "C", ".c"),
        LanguageProfile("cpp", "C++", ".cpp"),
        LanguageProfile("rust", "Rust", ".rs"),
        LanguageProfile("python", "Python", ".py"),
        LanguageProfile("lua", "Lua", ".lua"),
        LanguageProfile("javascript", "JavaScript", ".js"),
        LanguageProfile("typescript", "TypeScript", ".ts"),
        LanguageProfile("shell", "Shell", ".sh"),
        LanguageProfile("basic", "BASIC 2026", ".bas"),
        LanguageProfile("assembly", "Assembly", ".asm")
    )
}

class CodeEditorApp {
    val descriptor = OsAppCatalog.find("code-editor")!!
    var fileName = "untitled.txt"
    var source = ""
    var language = DevelopmentApps.languages.first()
    fun setLanguage(id: String) { language = DevelopmentApps.languages.firstOrNull { it.id == id } ?: language }
}
