package io.arkansasio.hp3000xl.os

import io.arkansasio.hp3000xl.apps.BuiltInApps

/** Coordinates application registration and process lifecycle. */
class OsApplicationHost(private val kernel: OsKernel) {
    val processes = OsProcessManager()
    val permissions = OsPermissionManager()

    fun launch(appId: String): OsProcess? {
        if (appId !in BuiltInApps.ids) return null
        val process = processes.launch(appId, appId)
        kernel.state.activeAppId = appId
        process.foreground = true
        kernel.events.publish(OsEvent.AppLaunched(appId))
        return process
    }

    fun close(pid: Int): Boolean {
        val process = processes.list().firstOrNull { it.pid == pid } ?: return false
        val closed = processes.stop(pid)
        if (closed) {
            if (kernel.state.activeAppId == process.appId) kernel.state.activeAppId = null
            kernel.events.publish(OsEvent.AppClosed(process.appId))
        }
        return closed
    }
}
