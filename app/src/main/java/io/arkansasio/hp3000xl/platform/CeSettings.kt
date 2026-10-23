package io.arkansasio.hp3000xl.platform

data class CeSettings(
    val theme: String = "classic-blue",
    val soundEnabled: Boolean = true,
    val volumePercent: Int = 70,
    val wifiEnabled: Boolean = true,
    val bluetoothEnabled: Boolean = false,
    val autoLockMinutes: Int = 10
)

class CeSettingsStore {
    private var settings = CeSettings()
    fun get(): CeSettings = settings
    fun update(value: CeSettings) { settings = value.copy(volumePercent = value.volumePercent.coerceIn(0, 100), autoLockMinutes = value.autoLockMinutes.coerceAtLeast(0)) }
}
