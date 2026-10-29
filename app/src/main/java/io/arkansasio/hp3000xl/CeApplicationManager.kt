package io.arkansasio.hp3000xl

import android.content.Context

data class CeApplicationDescriptor(
    val id: String,
    val name: String,
    val version: String = "1.0",
    val category: String = "Programs",
    val enabled: Boolean = true
)

/** Registry/lifecycle facade for built-in and future installable HP3000XL apps. */
class CeApplicationManager(private val context: Context) {
    private val database = CeDatabase(context)
    private val apps = linkedMapOf<String, CeApplicationDescriptor>()

    init {
        registerDefaults()
    }

    private fun registerDefaults() {
        listOf(
            CeApplicationDescriptor("file.explorer", "File Explorer", category = "System"),
            CeApplicationDescriptor("office.word", "Pocket Word", category = "Office"),
            CeApplicationDescriptor("office.excel", "Pocket Excel", category = "Office"),
            CeApplicationDescriptor("pim.outlook", "Pocket Outlook", category = "PIM"),
            CeApplicationDescriptor("pim.calendar", "Calendar", category = "PIM"),
            CeApplicationDescriptor("pim.contacts", "Contacts", category = "PIM"),
            CeApplicationDescriptor("system.calculator", "Calculator", category = "Accessories"),
            CeApplicationDescriptor("network.internet", "Internet", category = "Internet"),
            CeApplicationDescriptor("system.terminal", "Terminal", category = "System"),
            CeApplicationDescriptor("system.simh-emulator", "SIMH Machine Emulator", category = "System"),
            CeApplicationDescriptor("system.controlpanel", "Control Panel", category = "System"),
            CeApplicationDescriptor("system.taskmanager", "Task Manager", category = "System"),
            CeApplicationDescriptor("system.help", "Help", category = "System"),
            CeApplicationDescriptor("games.solitaire", "Solitaire", category = "Games"),
            CeApplicationDescriptor("games.minesweeper", "Minesweeper", category = "Games")
        ).forEach { register(it) }
    }

    fun register(descriptor: CeApplicationDescriptor) {
        apps[descriptor.id] = descriptor
        database.writableDatabase.execSQL(
            "INSERT OR REPLACE INTO installed_apps(id,name,version,enabled) VALUES(?,?,?,?)",
            arrayOf(descriptor.id, descriptor.name, descriptor.version, if (descriptor.enabled) 1 else 0)
        )
    }

    fun all(): List<CeApplicationDescriptor> = apps.values.toList()

    fun byCategory(category: String): List<CeApplicationDescriptor> =
        apps.values.filter { it.category.equals(category, ignoreCase = true) && it.enabled }

    fun find(id: String): CeApplicationDescriptor? = apps[id]
}
