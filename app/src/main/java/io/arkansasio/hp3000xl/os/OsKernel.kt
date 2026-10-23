package io.arkansasio.hp3000xl.os

import java.time.Instant

/** Android-hosted kernel facade; this is not a replacement for the Android/Linux kernel. */
class OsKernel {
    val state = OsState()
    val events = OsEventBus()
    val services = OsServiceRegistry()

    fun boot() {
        if (state.booted) return
        events.publish(OsEvent.BootStarted)
        state.lastBoot = Instant.now()
        state.booted = true
        services.startAll()
        events.publish(OsEvent.BootCompleted)
    }

    fun shutdown() {
        if (!state.booted) return
        services.stopAll()
        state.booted = false
    }
}
