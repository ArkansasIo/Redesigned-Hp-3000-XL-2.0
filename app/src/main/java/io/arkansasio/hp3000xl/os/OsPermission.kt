package io.arkansasio.hp3000xl.os

enum class OsPermission {
    FILE_READ, FILE_WRITE, NETWORK, BLUETOOTH, NOTIFICATIONS, CLIPBOARD, POWER, SYSTEM_SETTINGS
}

data class AppPermissions(val appId: String, val granted: Set<OsPermission> = emptySet()) {
    fun allows(permission: OsPermission): Boolean = permission in granted
}

class OsPermissionManager {
    private val permissions = mutableMapOf<String, MutableSet<OsPermission>>()

    @Synchronized
    fun grant(appId: String, permission: OsPermission) {
        permissions.getOrPut(appId) { mutableSetOf() }.add(permission)
    }

    @Synchronized
    fun revoke(appId: String, permission: OsPermission) {
        permissions[appId]?.remove(permission)
    }

    @Synchronized
    fun check(appId: String, permission: OsPermission): Boolean =
        permissions[appId]?.contains(permission) == true

    @Synchronized
    fun snapshot(appId: String): AppPermissions =
        AppPermissions(appId, permissions[appId]?.toSet() ?: emptySet())
}
