package io.arkansasio.hp3000xl.services

data class DeviceInfo(
    val manufacturer: String,
    val model: String,
    val androidRelease: String,
    val apiLevel: Int
)

interface DeviceService {
    fun info(): DeviceInfo
}
