package io.arkansasio.hp3000xl.apps.pim

data class Contact(val id: Long = 0, val name: String, val email: String = "", val phone: String = "")
data class CalendarEvent(val id: Long = 0, val title: String, val startMillis: Long, val endMillis: Long, val notes: String = "")
data class MailMessage(val id: Long = 0, val sender: String, val subject: String, val body: String, val read: Boolean = false)

class PimRepository {
    private val contacts = linkedMapOf<Long, Contact>()
    private val events = linkedMapOf<Long, CalendarEvent>()
    private val mail = linkedMapOf<Long, MailMessage>()
    private var nextId = 1L
    fun add(contact: Contact): Contact = contact.copy(id = nextId++).also { contacts[it.id] = it }
    fun add(event: CalendarEvent): CalendarEvent = event.copy(id = nextId++).also { events[it.id] = it }
    fun add(message: MailMessage): MailMessage = message.copy(id = nextId++).also { mail[it.id] = it }
    fun contacts(): List<Contact> = contacts.values.toList()
    fun events(): List<CalendarEvent> = events.values.sortedBy { it.startMillis }
    fun messages(): List<MailMessage> = mail.values.toList()
}
