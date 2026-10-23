package io.arkansasio.hp3000xl.os

interface OsService { val id: String; fun start(); fun stop() }

class OsServiceRegistry {
    private val services = linkedMapOf<String, OsService>()
    fun register(service: OsService) { services[service.id] = service }
    fun get(id: String): OsService? = services[id]
    fun all(): List<OsService> = services.values.toList()
    fun startAll() = services.values.forEach(OsService::start)
    fun stopAll() = services.values.reversed().forEach(OsService::stop)
}
