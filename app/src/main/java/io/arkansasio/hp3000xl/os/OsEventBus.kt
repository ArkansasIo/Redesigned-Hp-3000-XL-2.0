package io.arkansasio.hp3000xl.os

class OsEventBus {
    private val listeners = mutableSetOf<(OsEvent) -> Unit>()

    fun subscribe(listener: (OsEvent) -> Unit) { listeners += listener }
    fun unsubscribe(listener: (OsEvent) -> Unit) { listeners -= listener }
    fun publish(event: OsEvent) { listeners.toList().forEach { it(event) } }
}
