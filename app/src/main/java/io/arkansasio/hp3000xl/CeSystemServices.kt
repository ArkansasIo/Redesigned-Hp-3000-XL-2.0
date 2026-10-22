package io.arkansasio.hp3000xl

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs

/** System-information and device service facade used by the CE-style shell. */
class CeSystemServices(private val context: Context) {
    val deviceModel: String get() = Build.MODEL
    val androidVersion: String get() = Build.VERSION.RELEASE ?: "unknown"
    val sdk: Int get() = Build.VERSION.SDK_INT

    fun batteryPercent(): Int {
        val manager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).coerceIn(0, 100)
    }

    fun storageInfo(): StorageInfo {
        val stat = StatFs(Environment.getDataDirectory().path)
        return StorageInfo(stat.blockCountLong * stat.blockSizeLong, stat.availableBlocksLong * stat.blockSizeLong)
    }

    fun deviceSummary(): String {
        val storage = storageInfo()
        val totalMb = storage.totalBytes / 1024 / 1024
        val freeMb = storage.freeBytes / 1024 / 1024
        return "HP 3000 XL Runtime\nDevice: $deviceModel\nAndroid: $androidVersion (SDK $sdk)\nStorage: ${freeMb}MB free / ${totalMb}MB"
    }
}

data class StorageInfo(val totalBytes: Long, val freeBytes: Long)

interface CeService {
    val serviceId: String
    fun start()
    fun stop()
}

class CeClipboardService : CeService {
    override val serviceId = "clipboard"
    override fun start() = Unit
    override fun stop() = Unit
}

class CeNotificationService : CeService {
    override val serviceId = "notifications"
    override fun start() = Unit
    override fun stop() = Unit
}

class CePowerService : CeService {
    override val serviceId = "power"
    override fun start() = Unit
    override fun stop() = Unit
}
