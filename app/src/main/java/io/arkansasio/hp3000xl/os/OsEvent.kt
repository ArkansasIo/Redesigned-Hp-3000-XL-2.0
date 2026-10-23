package io.arkansasio.hp3000xl.os

sealed interface OsEvent {
    data object BootStarted : OsEvent
    data object BootCompleted : OsEvent
    data class AppLaunched(val appId: String) : OsEvent
    data class AppClosed(val appId: String) : OsEvent
    data class Notification(val title: String, val message: String) : OsEvent
    data class PowerChanged(val batteryPercent: Int) : OsEvent
    data class NetworkChanged(val connected: Boolean) : OsEvent
    data class StorageChanged(val freeBytes: Long) : OsEvent
}
