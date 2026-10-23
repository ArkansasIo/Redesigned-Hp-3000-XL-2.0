package io.arkansasio.hp3000xl.os

/** Deterministic startup sequence for the Android-hosted handheld shell. */
class OsBootManager(private val kernel: OsKernel) {
    enum class Stage { POWER, MEMORY, DISPLAY, INPUT, STORAGE, NETWORK, AUDIO, SERVICES, SHELL, COMPLETE }

    private val completed = mutableListOf<Stage>()

    fun start(): List<Stage> {
        if (kernel.state.booted) return completed.toList()
        completed.clear()
        Stage.values().dropLast(1).forEach { completed += it }
        kernel.boot()
        completed += Stage.COMPLETE
        return completed.toList()
    }

    fun stages(): List<Stage> = completed.toList()
}
