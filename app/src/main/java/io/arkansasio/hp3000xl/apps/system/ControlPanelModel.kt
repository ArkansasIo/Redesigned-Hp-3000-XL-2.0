package io.arkansasio.hp3000xl.apps.system

data class ControlPanelSettings(
    var sound: Boolean = true,
    var volume: Int = 70,
    var wifi: Boolean = true,
    var bluetooth: Boolean = false,
    var darkMode: Boolean = false,
    var notifications: Boolean = true
)

class ControlPanelModel { val settings = ControlPanelSettings() }
