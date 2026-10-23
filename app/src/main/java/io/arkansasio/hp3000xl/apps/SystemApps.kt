package io.arkansasio.hp3000xl.apps

class ControlPanelApp {
    val descriptor = OsAppCatalog.find("control-panel")!!
    var displayScale = 1.0f
    var darkMode = false
    var notificationsEnabled = true
}

class TaskManagerApp {
    val descriptor = OsAppCatalog.find("task-manager")!!
    private val tasks = mutableListOf<String>()
    fun add(id: String) { if (id !in tasks) tasks += id }
    fun remove(id: String) { tasks -= id }
    fun list(): List<String> = tasks.toList()
}

class UpdateManagerApp {
    val descriptor = OsAppCatalog.find("update-manager")!!
    fun channel(): String = "pre-alpha"
    fun manifestUrl(): String = "https://github.com/ArkansasIo/Redesigned-Hp-3000-XL-2.0/releases"
}

class HelpApp {
    val descriptor = OsAppCatalog.find("help")!!
    fun topics(): List<String> = listOf("Getting Started", "Applications", "Files", "Network", "Input", "Updates", "Development")
}
