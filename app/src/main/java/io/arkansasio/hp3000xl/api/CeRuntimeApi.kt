package io.arkansasio.hp3000xl.api

import android.content.Context
import io.arkansasio.hp3000xl.CeApplicationManager
import io.arkansasio.hp3000xl.CeDatabase
import io.arkansasio.hp3000xl.CeSystemServices
import io.arkansasio.hp3000xl.BuildInfo
import io.arkansasio.hp3000xl.core.CeOs
import io.arkansasio.hp3000xl.services.AndroidClipboardService
import io.arkansasio.hp3000xl.services.AndroidDeviceService
import io.arkansasio.hp3000xl.services.AndroidFileSystemService
import io.arkansasio.hp3000xl.services.AndroidNetworkService
import io.arkansasio.hp3000xl.services.AndroidNotificationService
import io.arkansasio.hp3000xl.services.AndroidPowerService
import io.arkansasio.hp3000xl.services.ClipboardService
import io.arkansasio.hp3000xl.services.DeviceService
import io.arkansasio.hp3000xl.services.FileSystemService
import io.arkansasio.hp3000xl.services.NetworkService
import io.arkansasio.hp3000xl.services.NotificationService
import io.arkansasio.hp3000xl.services.PowerService
import io.arkansasio.hp3000xl.storage.AndroidDocumentStore
import io.arkansasio.hp3000xl.storage.AndroidPreferenceStore
import io.arkansasio.hp3000xl.storage.DocumentStore
import io.arkansasio.hp3000xl.storage.PreferenceStore

/**
 * Public application API for HP3000XL apps — the CE-style SDK surface.
 * Apps receive a [CeAppContext] plus typed service handles and never touch
 * Android internals directly.
 */
class CeRuntimeApi(private val context: Context) : CeApi {

    // Core singletons
    val database by lazy { CeDatabase(context) }
    val applicationManager by lazy { CeApplicationManager(context) }
    val systemServices by lazy { CeSystemServices(context) }
    val os get() = CeOs

    // Typed service facades
    val device: DeviceService by lazy { AndroidDeviceService(context) }
    val fileSystem: FileSystemService by lazy { AndroidFileSystemService(context) }
    val power: PowerService by lazy { AndroidPowerService(context) }
    val clipboard: ClipboardService by lazy { AndroidClipboardService(context) }
    val notifier: NotificationService by lazy { AndroidNotificationService(context) }
    val network: NetworkService by lazy { AndroidNetworkService(context) }
    val preferences: PreferenceStore by lazy { AndroidPreferenceStore(context) }
    val documents: DocumentStore by lazy { AndroidDocumentStore(context) }

    fun boot() {
        if (CeOs.runningServices().isEmpty()) {
            CeOs.boot(listOf("power", "storage", "clipboard", "notifications", "device", "network"))
        }
    }

    fun appContextFor(appId: String): CeAppContext = CeAppContext(
        appId = appId,
        displayName = applicationManager.find(appId)?.name ?: appId,
        version = applicationManager.find(appId)?.version ?: "1.0",
        dataDirectory = context.filesDir.resolve("apps").resolve(appId)
            .apply { mkdirs() }.absolutePath
    )

    // ---- CeApi ----------------------------------------------------------

    override fun version(): String =
        "${BuildInfo.PRODUCT_NAME} ${BuildInfo.VERSION_NAME} (${BuildInfo.BUILD_ID})"

    override fun applications(): List<String> =
        applicationManager.all().filter { it.enabled }.map { it.id }

    override fun getSetting(key: String): String? = database.getSetting(key)

    override fun setSetting(key: String, value: String) = database.putSetting(key, value)
}
