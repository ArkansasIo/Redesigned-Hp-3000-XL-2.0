package io.arkansasio.hp3000xl.apps.pim

/** Canonical MailMessage model lives in PimModels.kt. */
class InboxModel {
    private val messages = mutableListOf<MailMessage>()
    fun add(message: MailMessage) { messages.removeAll { it.id == message.id }; messages += message }
    fun markRead(id: Long) { messages.replaceAll { if (it.id == id) it.copy(read = true) else it } }
    fun list(): List<MailMessage> = messages.toList()
    fun unreadCount(): Int = messages.count { !it.read }
}
