package io.arkansasio.hp3000xl.core

sealed interface CeEvent {
    data class LaunchApp(val appId: String): CeEvent
    data class CloseApp(val appId: String): CeEvent
    data class MenuSelected(val path: String): CeEvent
    data class SettingChanged(val key: String, val value: String): CeEvent
}
