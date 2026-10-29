package io.arkansasio.hp3000xl.apps

object OsAppCatalog {
    val all: List<OsApp> = listOf(
        OsApp("file-explorer", "File Explorer", "System", system = true),
        OsApp("pocket-word", "Pocket Word", "Office"),
        OsApp("pocket-excel", "Pocket Excel", "Office"),
        OsApp("pocket-outlook", "Pocket Outlook", "PIM"),
        OsApp("calendar", "Calendar", "PIM"),
        OsApp("contacts", "Contacts", "PIM"),
        OsApp("calculator", "Calculator", "Utilities"),
        OsApp("internet", "Internet", "Network"),
        OsApp("terminal", "Terminal", "Development"),
        OsApp("simh-emulator", "SIMH Machine Emulator", "System Tools", system = true),
        OsApp("code-editor", "Code Editor", "Development"),
        OsApp("basic-2026", "BASIC 2026", "Development"),
        OsApp("python", "Python", "Development"),
        OsApp("lua", "Lua", "Development"),
        OsApp("javascript", "JavaScript", "Development"),
        OsApp("typescript", "TypeScript", "Development"),
        OsApp("c-cpp", "C / C++", "Development"),
        OsApp("rust", "Rust", "Development"),
        OsApp("kotlin-java", "Kotlin / Java", "Development"),
        OsApp("assembly", "Assembly", "Development"),
        OsApp("control-panel", "Control Panel", "System", system = true),
        OsApp("task-manager", "Task Manager", "System", system = true),
        OsApp("update-manager", "Update Manager", "System", system = true),
        OsApp("help", "Help", "System", system = true),
        OsApp("solitaire", "Solitaire", "Games"),
        OsApp("minesweeper", "Minesweeper", "Games")
    )

    fun find(id: String): OsApp? = all.firstOrNull { it.id == id }
}
