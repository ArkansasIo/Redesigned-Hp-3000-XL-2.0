package io.arkansasio.hp3000xl.apps.pim

/** Canonical calendar event model lives in PimModels.kt. */
class CalendarModel {
    private val events = mutableListOf<CalendarEvent>()
    fun add(event: CalendarEvent) { events.removeAll { it.id == event.id }; events += event }
    fun remove(id: Long) { events.removeAll { it.id == id } }
    fun between(start: Long, end: Long): List<CalendarEvent> = events.filter { it.startMillis < end && it.endMillis > start }
    fun list(): List<CalendarEvent> = events.sortedBy { it.startMillis }
}
