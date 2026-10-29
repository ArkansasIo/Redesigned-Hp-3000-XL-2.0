package io.arkansasio.hp3000xl.services

import android.content.Context
import android.widget.Toast

/** Android notification adapter retained as a lightweight pre-alpha shell service. */
class AndroidNotificationService(private val context: Context) : NotificationService {
    override fun notify(title: String, message: String) {
        Toast.makeText(context, "$title — $message", Toast.LENGTH_SHORT).show()
    }
}

/** Backward-compatible concrete adapter names used by CeRuntimeApi. */
class AndroidDeviceService(context: Context) : DeviceService by io.arkansasio.hp3000xl.services.AndroidDeviceService()
class AndroidPowerService(context: Context) : PowerService by io.arkansasio.hp3000xl.services.AndroidPowerService(context)
class AndroidNetworkService(context: Context) : NetworkService by io.arkansasio.hp3000xl.services.AndroidNetworkService(context)
