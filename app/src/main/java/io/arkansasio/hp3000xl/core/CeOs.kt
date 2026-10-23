package io.arkansasio.hp3000xl.core

import io.arkansasio.hp3000xl.BuildInfo

/**
 * HP3000XL OS core — the "kernel" of the recreation.
 *
 * A cooperative, single-process operating environment that boots with the
 * shell: it owns the service registry, the running-task table and the system
 * event bus. No native code is executed; everything runs inside the app's
 * own process, like the original CE-style shell concept.
 */
object CeOs {

    data class OsTask(
        val pid: Int,
        val appId: String,
        val title: String,
        val startedAt: Long,
        var state: TaskState = TaskState.RUNNING
    )

    enum class TaskState { RUNNING, BACKGROUND, CLOSED }

    data class OsEvent(
        val time: Long,
        val source: String,
        val message: String
    )

    val name = "HP3000XL OS"
    val version = BuildInfo.BUILD_VERSION
    val buildId = BuildInfo.BUILD_ID

    private var nextPid = 100
    private val tasks = linkedMapOf<Int, OsTask>()
    private val events = mutableListOf<OsEvent>()
    private val services = linkedMapOf<String, CeServiceHandle>()
    private val listeners = mutableListOf<(OsEvent) -> Unit>()

    fun uptimeMs(): Long = android.os.SystemClock.elapsedRealtime()

    fun boot(serviceIds: List<String>) {
        log("os", "$name $version ($buildId) starting")
        serviceIds.forEach { startService(it) }
        log("os", "all services started — system ready")
    }

    // ---- services -------------------------------------------------------

    data class CeServiceHandle(val serviceId: String, var running: Boolean)

    fun startService(serviceId: String) {
        services[serviceId] = CeServiceHandle(serviceId, true)
        log("service", "started $serviceId")
    }

    fun stopService(serviceId: String) {
        services[serviceId]?.let { it.running = false }
        log("service", "stopped $serviceId")
    }

    fun runningServices(): List<String> =
        services.values.filter { it.running }.map { it.serviceId }

    // ---- tasks ----------------------------------------------------------

    fun spawnTask(appId: String, title: String): Int {
        val pid = nextPid++
        tasks[pid] = OsTask(pid, appId, title, System.currentTimeMillis())
        log("task", "spawned pid=$pid app=$appId")
        return pid
    }

    fun setState(pid: Int, state: TaskState) {
        tasks[pid]?.state = state
    }

    fun closeTask(pid: Int): Boolean {
        tasks.remove(pid)?.also { log("task", "closed pid=$pid app=${it.appId}") }
        return tasks.isEmpty()
    }

    fun runningTasks(): List<OsTask> = tasks.values.toList()

    // ---- event bus ------------------------------------------------------

    fun addListener(listener: (OsEvent) -> Unit) {
        listeners.add(listener)
    }

    fun log(source: String, message: String) {
        val event = OsEvent(System.currentTimeMillis(), source, message)
        events.add(event)
        if (events.size > 500) events.removeAt(0)
        listeners.forEach { it(event) }
    }

    fun recentEvents(count: Int = 50): List<OsEvent> = events.takeLast(count)
}
