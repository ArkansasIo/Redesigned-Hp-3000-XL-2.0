package io.arkansasio.hp3000xl.network

data class WifiStatus(val enabled: Boolean, val connected: Boolean, val ssid: String?)

interface WifiManagerFacade {
    fun status(): WifiStatus
}
