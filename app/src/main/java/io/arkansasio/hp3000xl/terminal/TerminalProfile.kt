package io.arkansasio.hp3000xl.terminal

data class TerminalProfile(
    val name: String = "CE Terminal",
    val prompt: String = "hp3000xl$ ",
    val home: String = "/storage/emulated/0/HP3000XL",
    val shell: String = "ce-shell"
)

object TerminalCommands {
    val builtIns = setOf(
        "help", "clear", "pwd", "ls", "cd", "cat", "echo", "date", "time",
        "apps", "info", "settings", "update", "lang", "run", "history", "exit"
    )
}
