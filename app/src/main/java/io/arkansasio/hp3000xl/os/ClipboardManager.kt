package io.arkansasio.hp3000xl.os

class ClipboardManager : OsService {
    override val id = "clipboard"
    private var text: String = ""
    override fun start() = Unit
    override fun stop() = Unit
    fun copy(value: String) { text = value }
    fun paste(): String = text
    fun clear() { text = "" }
}
