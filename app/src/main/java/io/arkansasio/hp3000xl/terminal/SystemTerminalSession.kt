package io.arkansasio.hp3000xl.terminal

import io.arkansasio.hp3000xl.BuildInfo
import io.arkansasio.hp3000xl.core.CeOs
import io.arkansasio.hp3000xl.storage.AndroidPreferenceStore

/**
 * System terminal session wired to the OS core: real service/task listings,
 * settings persistence via [AndroidPreferenceStore], and OS event history.
 * Commands are allow-listed; no arbitrary code is executed.
 */
class SystemTerminalSession(
    private val profile: TerminalProfile = TerminalProfile(),
    private val store: AndroidPreferenceStore? = null,
) {
    private val history = mutableListOf<String>()

    fun prompt(): String = profile.prompt

    fun execute(command: String): String {
        val input = command.trim()
        if (input.isNotEmpty()) history += input
        val cmd = input.substringBefore(' ')
        val arg = input.removePrefix("$cmd ").trim()
        return when (cmd) {
            "help" -> TerminalCommands.builtIns.sorted().joinToString("  ")
            "clear" -> "\u000C"
            "pwd" -> profile.home
            "echo" -> arg
            "history" -> history.mapIndexed { i, v -> "${i + 1}  $v" }.joinToString("\n").ifEmpty { "(empty)" }
            "info" -> "${BuildInfo.PRODUCT_NAME} ${BuildInfo.VERSION_NAME} | OS ${CeOs.name} ${CeOs.version} (${CeOs.buildId})"
            "date" -> java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
            "time" -> java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
            "services" -> CeOs.runningServices().joinToString("  ").ifEmpty { "(none)" }
            "tasks" -> CeOs.runningTasks().joinToString("\n") { "pid=${it.pid}  ${it.title}  [${it.state}]" }.ifEmpty { "(no tasks)" }
            "log" -> CeOs.recentEvents(10).joinToString("\n") { "[${it.source}] ${it.message}" }.ifEmpty { "(empty)" }
            "get" -> store?.get(arg).let { if (it.isNullOrEmpty()) "(unset)" else it }
            "set" -> if (arg.contains('=')) {
                val key = arg.substringBefore('=').trim()
                val value = arg.substringAfter('=', "").trim()
                store?.put(key, value)
                "saved $key"
            } else "usage: set key=value"
            "version" -> "${BuildInfo.VERSION_NAME} (${BuildInfo.BUILD_ID})"
            "" -> ""
            else -> "Command not found: $cmd  (type 'help' for the allow-list)"
        }
    }
}
