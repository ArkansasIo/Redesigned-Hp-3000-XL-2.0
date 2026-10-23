package io.arkansasio.hp3000xl.os

class PowerManager(private val state: OsState) : OsService {
    override val id = "power"
    override fun start() = Unit
    override fun stop() = Unit
    fun setBattery(percent: Int) { state.batteryPercent = percent.coerceIn(0, 100) }
    fun sleep() { state.locked = true }
    fun wake() { state.locked = false }
}
