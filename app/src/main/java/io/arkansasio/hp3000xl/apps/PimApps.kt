package io.arkansasio.hp3000xl.apps

data class Contact(val id: Long, val name: String, val phone: String = "", val email: String = "")
data class CalendarEvent(val id: Long, val title: String, val startEpochMs: Long, val endEpochMs: Long)

class ContactsApp {
    val descriptor = OsAppCatalog.find("contacts")!!
    private val contacts = mutableListOf<Contact>()
    fun add(contact: Contact) { contacts += contact }
    fun all(): List<Contact> = contacts.toList()
}

class CalendarApp {
    val descriptor = OsAppCatalog.find("calendar")!!
    private val events = mutableListOf<CalendarEvent>()
    fun add(event: CalendarEvent) { events += event }
    fun upcoming(nowMs: Long): List<CalendarEvent> = events.filter { it.endEpochMs >= nowMs }.sortedBy { it.startEpochMs }
}
