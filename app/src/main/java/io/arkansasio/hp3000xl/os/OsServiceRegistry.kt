package io.arkansasio.hp3000xl.os

/** Lightweight service registry for the Android-hosted HP3000XL OS layer. */
class OsServiceRegistry {
    data class Service(val id: String, var running: Boolean = false)

    private val services = linkedMapOf<String, Service>()

    init {
        listOf("power", "storage", "clipboard", "notifications", "device", "network", "input", "audio", "packages", "updates")
            .forEach { register(it) }
    }

    fun register(id: String) {
        services.putIfAbsent(id, Service(id))
    }

    fun start(id: String): Boolean = services[id]?.let { it.running = true; true } ?: false

    fun stop(id: String): Boolean = services[id]?.let { it.running = false; true } ?: false

    fun startAll() { services.values.forEach { it.running = true } }

    fun stopAll() { services.values.forEach { it.running = false } }

    fun isRunning(id: String): Boolean = services[id]?.running == true

    fun runningIds(): List<String> = services.values.filter { it.running }.map { it.id }
}
