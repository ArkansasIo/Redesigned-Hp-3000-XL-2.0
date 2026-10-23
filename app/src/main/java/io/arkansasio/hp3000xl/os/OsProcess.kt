package io.arkansasio.hp3000xl.os

import java.util.concurrent.atomic.AtomicInteger

/** Lightweight process model for applications hosted by the recreation. */
data class OsProcess(
    val pid: Int,
    val appId: String,
    val name: String,
    var state: ProcessState = ProcessState.READY,
    var foreground: Boolean = false
)

enum class ProcessState { READY, RUNNING, SUSPENDED, STOPPED }

class OsProcessManager {
    private val nextPid = AtomicInteger(100)
    private val processes = linkedMapOf<Int, OsProcess>()

    @Synchronized
    fun launch(appId: String, name: String): OsProcess {
        val process = OsProcess(nextPid.getAndIncrement(), appId, name, ProcessState.RUNNING)
        processes[process.pid] = process
        return process
    }

    @Synchronized
    fun stop(pid: Int): Boolean = processes.remove(pid)?.let { it.state = ProcessState.STOPPED; true } ?: false

    @Synchronized
    fun suspend(pid: Int): Boolean = processes[pid]?.let { it.state = ProcessState.SUSPENDED; true } ?: false

    @Synchronized
    fun resume(pid: Int): Boolean = processes[pid]?.let { it.state = ProcessState.RUNNING; true } ?: false

    @Synchronized
    fun list(): List<OsProcess> = processes.values.toList()

    @Synchronized
    fun foreground(pid: Int): Boolean {
        if (!processes.containsKey(pid)) return false
        processes.values.forEach { it.foreground = false }
        processes[pid]?.foreground = true
        return true
    }
}
