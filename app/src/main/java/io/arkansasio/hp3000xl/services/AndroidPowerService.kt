package io.arkansasio.hp3000xl.services

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.PowerManager

class AndroidPowerService(private val context: Context) : PowerService {
    override fun state(): PowerState {
        val battery = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = battery?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = battery?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val percent = if (level >= 0 && scale > 0) ((level * 100f) / scale).toInt().coerceIn(0, 100) else -1
        val status = battery?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        return PowerState(percent, status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL)
    }

    override fun requestSleep() {
        val manager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        // Android does not allow ordinary applications to force device sleep.
        // Respect platform policy; this method intentionally makes no privileged call.
        manager?.isInteractive
    }
}
