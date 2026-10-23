package io.arkansasio.hp3000xl.os

import java.time.Instant

/** Runtime state shared by OS services. */
data class OsState(
    var booted: Boolean = false,
    var locked: Boolean = false,
    var airplaneMode: Boolean = false,
    var wifiEnabled: Boolean = true,
    var bluetoothEnabled: Boolean = false,
    var soundEnabled: Boolean = true,
    var volumePercent: Int = 70,
    var batteryPercent: Int = 100,
    var activeAppId: String? = null,
    var lastBoot: Instant? = null
)
