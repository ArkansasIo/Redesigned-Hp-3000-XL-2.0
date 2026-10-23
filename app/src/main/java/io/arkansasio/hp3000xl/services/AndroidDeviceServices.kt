package io.arkansasio.hp3000xl.services

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.StatFs

class AndroidDeviceService(private val context: Context) : DeviceService {
    override fun info(): DeviceInfo = DeviceInfo(
        manufacturer = Build.MANUFACTURER,
        model = Build.MODEL,
        androidRelease = Build.VERSION.RELEASE ?: "unknown",
        apiLevel = Build.VERSION.SDK_INT
    )
}

class AndroidPowerService(private val context: Context) : PowerService {
    override fun state(): PowerState {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, 100) ?: 100
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val percent = if (level >= 0 && scale > 0) (level * 100 / scale).coerceIn(0, 100) else 0
        val charging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        return PowerState(percent, charging)
    }

    override fun requestSleep() {
        // Android owns device power policy; applications do not force suspend here.
    }
}

class AndroidNetworkService(private val context: Context) : NetworkService {
    override fun state(): NetworkState {
        val manager = context.getSystemService(ConnectivityManager::class.java)
            ?: return NetworkState(false, "none", null)
        val network = manager.activeNetwork ?: return NetworkState(false, "none", null)
        val capabilities = manager.getNetworkCapabilities(network)
            ?: return NetworkState(false, "none", null)
        val transport = when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "wifi"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "cellular"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "ethernet"
            else -> "other"
        }
        return NetworkState(true, transport, null)
    }
}

class AndroidStorageService(private val context: Context) {
    data class StorageState(val totalBytes: Long, val freeBytes: Long)

    fun state(): StorageState {
        val stats = StatFs(context.filesDir.absolutePath)
        return StorageState(stats.totalBytes, stats.availableBytes)
    }
}
