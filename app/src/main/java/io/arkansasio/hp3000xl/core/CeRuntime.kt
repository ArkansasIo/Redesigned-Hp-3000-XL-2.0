package io.arkansasio.hp3000xl.core

/** Central runtime coordinator for the Color Mobile Edition shell. */
class CeRuntime {
    val state = RuntimeState()
    fun start() { state.running = true }
    fun stop() { state.running = false }
}

data class RuntimeState(var running: Boolean = false)
