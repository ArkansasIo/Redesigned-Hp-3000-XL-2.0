package io.arkansasio.hp3000xl.apps

data class FileEntry(val name: String, val directory: Boolean, val size: Long = 0)

class FileExplorerApp {
    val descriptor = OsAppCatalog.find("file-explorer")!!
    private var currentPath = "/"
    private val entries = linkedMapOf<String, MutableList<FileEntry>>("/" to mutableListOf())
    fun path(): String = currentPath
    fun list(): List<FileEntry> = entries[currentPath].orEmpty().toList()
    fun mkdir(name: String) { entries.getOrPut(currentPath) { mutableListOf() } += FileEntry(name, true) }
    fun cd(path: String) { currentPath = if (path.startsWith("/")) path else "/$path"; entries.getOrPut(currentPath) { mutableListOf() } }
}
