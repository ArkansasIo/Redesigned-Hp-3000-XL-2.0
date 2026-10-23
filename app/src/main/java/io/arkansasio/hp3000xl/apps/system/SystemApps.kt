package io.arkansasio.hp3000xl.apps.system

data class SystemAppInfo(val id: String, val name: String, val category: String)

object SystemApps {
    val all = listOf(
        SystemAppInfo("file-explorer", "File Explorer", "System"),
        SystemAppInfo("control-panel", "Control Panel", "System"),
        SystemAppInfo("task-manager", "Task Manager", "System"),
        SystemAppInfo("system-info", "System Information", "System"),
        SystemAppInfo("update-manager", "Update Manager", "System"),
        SystemAppInfo("help", "Help", "System"),
        SystemAppInfo("terminal", "Terminal", "Development")
    )
}
