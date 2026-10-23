package io.arkansasio.hp3000xl.apps.system

data class FileEntry(val name: String, val path: String, val directory: Boolean, val size: Long = 0)

class FileExplorerModel {
    private var currentPath = "/"
    private val entries = mutableListOf<FileEntry>()
    fun path(): String = currentPath
    fun navigate(path: String) { currentPath = if (path.startsWith('/')) path else "/$path" }
    fun setEntries(value: List<FileEntry>) { entries.clear(); entries.addAll(value) }
    fun entries(): List<FileEntry> = entries.toList()
}
