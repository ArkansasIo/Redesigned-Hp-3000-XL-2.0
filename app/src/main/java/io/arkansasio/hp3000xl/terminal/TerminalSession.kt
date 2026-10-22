package io.arkansasio.hp3000xl.terminal

class TerminalSession(private val profile: TerminalProfile = TerminalProfile()) {
    private val history = mutableListOf<String>()
    fun prompt(): String = profile.prompt
    fun execute(command: String): String {
        val input = command.trim()
        if (input.isNotEmpty()) history += input
        return when (input.substringBefore(' ')) {
            "help" -> TerminalCommands.builtIns.sorted().joinToString("  ")
            "clear" -> "\u000C"
            "pwd" -> profile.home
            "echo" -> input.removePrefix("echo ").trim()
            "history" -> history.mapIndexed { i, v -> "${i + 1}  $v" }.joinToString("\n")
            "info" -> "Redesigned HP 3000 XL 2.0 | Android 10+ | Build 1.2.0 Pre-Alpha"
            "exit" -> "Session closed"
            "" -> ""
            else -> "Command not found: $input"
        }
    }
}
