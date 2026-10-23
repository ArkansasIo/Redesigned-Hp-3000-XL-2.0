package io.arkansasio.hp3000xl.services

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import java.io.File

/**
 * Android-backed implementations of the HP 3000 XL service interfaces.
 * Each facade only exposes what the CE-style shell needs; no privileged
 * operations are performed.
 */

class AndroidDeviceService(private val context: Context) : DeviceService {
    override fun info(): DeviceInfo = DeviceInfo(
        manufacturer = Build.MANUFACTURER ?: "unknown",
        model = Build.MODEL ?: "unknown",
        androidRelease = Build.VERSION.RELEASE ?: "unknown",
        apiLevel = Build.VERSION.SDK_INT,
    )
}

class AndroidFileSystemService(private val context: Context) : FileSystemService {
    private fun resolve(path: String): File {
        val root = context.filesDir
        val cleaned = path.trimStart('\\', '/')
        return if (cleaned.isEmpty()) root else File(root, cleaned)
    }

    override fun list(path: String): List<String> {
        val dir = resolve(path)
        if (!dir.exists()) return emptyList()
        return dir.listFiles()?.map { if (it.isDirectory) it.name + "/" else it.name } ?: emptyList()
    }

    override fun read(path: String): ByteArray? {
        val file = resolve(path)
        return if (file.isFile) file.readBytes() else null
    }

    override fun write(path: String, data: ByteArray) {
        val file = resolve(path)
        file.parentFile?.mkdirs()
        file.writeBytes(data)
    }

    override fun delete(path: String): Boolean = resolve(path).delete()
}

class AndroidPowerService(private val context: Context) : PowerService {
    override fun state(): PowerState {
        val intent = context.registerReceiver(null, Intent(Intent.ACTION_BATTERY_CHANGED))
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, 100) ?: 100
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val percent = if (level >= 0 && scale > 0) level * 100 / scale else 0
        return PowerState(
            batteryPercent = percent.coerceIn(0, 100),
            charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL,
        )
    }

    override fun requestSleep() {
        val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager ?: return
        // Does not force sleep; only hints the system that the user is inactive.
        pm.userActivity(SystemClock_Uptime(), false)
    }

    private fun SystemClock_Uptime(): Long = android.os.SystemClock.uptimeMillis()
}

class AndroidNotificationService(private val context: Context) : NotificationService {
    override fun notify(title: String, message: String) {
        android.widget.Toast.makeText(context, "$title — $message", android.widget.Toast.LENGTH_SHORT).show()
    }
}

class AndroidClipboardService(private val context: Context) : ClipboardService {
    private val clipboard get() = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager

    override fun copy(text: String) {
        clipboard.setPrimaryClip(android.content.ClipData.newPlainText("hp3000xl", text))
    }

    override fun paste(): String? =
        clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}

class AndroidNetworkService(private val context: Context) : NetworkService {
    override fun state(): NetworkState {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? android.net.ConnectivityManager
        val network = cm?.activeNetwork
        val caps = network?.let { cm.getNetworkCapabilities(it) }
        val connected = caps?.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        val transport = when {
            caps == null -> "none"
            caps.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> "wifi"
            caps.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> "cellular"
            else -> "other"
        }
        return NetworkState(connected, transport, null)
    }
}
