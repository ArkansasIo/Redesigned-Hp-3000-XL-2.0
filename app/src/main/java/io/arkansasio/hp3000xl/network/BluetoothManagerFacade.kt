package io.arkansasio.hp3000xl.network

data class BluetoothStatus(val enabled: Boolean, val connectedDevices: Int)

interface BluetoothManagerFacade {
    fun status(): BluetoothStatus
}
