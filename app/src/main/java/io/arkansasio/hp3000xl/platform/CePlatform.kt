package io.arkansasio.hp3000xl.platform

import io.arkansasio.hp3000xl.os.OsPermission

data class CeAppDescriptor(val id: String, val name: String, val version: String, val category: String)
data class CePackage(val id: String, val version: String, val entryPoint: String, val permissions: Set<OsPermission>)
data class CeUpdate(val version: String, val channel: String, val url: String, val sha256: String)

enum class CeAppState { INSTALLED, ENABLED, DISABLED, RUNNING }

class CePackageRegistry {
    private val packages = linkedMapOf<String, CePackage>()
    fun install(pkg: CePackage) { packages[pkg.id] = pkg }
    fun uninstall(id: String): Boolean = packages.remove(id) != null
    fun get(id: String): CePackage? = packages[id]
    fun all(): List<CePackage> = packages.values.toList()
}

class CeUpdateChannel(private val channel: String = "pre-alpha") {
    fun accepts(update: CeUpdate): Boolean = update.channel == channel && update.url.startsWith("https://") && update.sha256.length == 64
}
