package io.arkansasio.hp3000xl.services

data class PowerState(val batteryPercent: Int, val charging: Boolean)

interface PowerService {
    fun state(): PowerState
    fun requestSleep()
}
