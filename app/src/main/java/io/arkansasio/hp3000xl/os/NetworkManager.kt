package io.arkansasio.hp3000xl.os

data class NetworkState(val wifi: Boolean, val bluetooth: Boolean, val connected: Boolean)

class NetworkManager(private val state: OsState, private val events: OsEventBus) : OsService {
    override val id = "network"
    private var connected = false
    override fun start() = Unit
    override fun stop() { connected = false }
    fun setWifiEnabled(enabled: Boolean) { state.wifiEnabled = enabled; if (!enabled) setConnected(false) }
    fun setBluetoothEnabled(enabled: Boolean) { state.bluetoothEnabled = enabled }
    fun setConnected(value: Boolean) { connected = value; events.publish(OsEvent.NetworkChanged(value)) }
    fun snapshot() = NetworkState(state.wifiEnabled, state.bluetoothEnabled, connected)
}
