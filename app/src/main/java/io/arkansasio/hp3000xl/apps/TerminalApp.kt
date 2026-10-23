package io.arkansasio.hp3000xl.apps

class TerminalApp {
    val descriptor = OsAppCatalog.find("terminal")!!
    private val history = mutableListOf<String>()
    fun execute(commandLine: String): String {
        val command = commandLine.trim()
        if (command.isEmpty()) return ""
        history += command
        return when (command.substringBefore(' ')) {
            "help" -> "apps info settings update pwd ls cd cat lang run history"
            "apps" -> OsAppCatalog.all.joinToString("\n") { "${it.id}  ${it.title}" }
            "info" -> "${descriptor.title} / ${descriptor.version}"
            "pwd" -> "/"
            "history" -> history.withIndex().joinToString("\n") { "${it.index + 1}: ${it.value}" }
            "clear" -> ""
            else -> "Command not found: $command"
        }
    }
}
