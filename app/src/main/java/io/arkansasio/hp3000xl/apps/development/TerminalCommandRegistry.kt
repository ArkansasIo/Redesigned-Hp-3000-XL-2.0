package io.arkansasio.hp3000xl.apps.development

class TerminalCommandRegistry {
    private val commands = linkedMapOf<String, (List<String>) -> String>()
    fun register(name: String, handler: (List<String>) -> String) { commands[name] = handler }
    fun execute(line: String): String {
        val parts = line.trim().split(Regex("\\s+")).filter(String::isNotEmpty)
        if (parts.isEmpty()) return ""
        val handler = commands[parts.first()] ?: return "command not found: ${parts.first()}"
        return handler(parts.drop(1))
    }
    fun names(): List<String> = commands.keys.toList()
}
