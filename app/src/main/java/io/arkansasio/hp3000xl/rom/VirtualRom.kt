package io.arkansasio.hp3000xl.rom

import android.content.Context
import io.arkansasio.hp3000xl.BuildInfo

/**
 * Loads the original virtual ROM/profile shipped inside the APK.
 * It is application data, not flashable HP firmware.
 */
object VirtualRom {
    fun read(context: Context): String =
        context.assets.open(BuildInfo.ROM_ASSET).bufferedReader().use { it.readText() }

    fun compatibilityImage(context: Context): String =
        context.assets.open(BuildInfo.IOS_COMPATIBILITY_ASSET).bufferedReader().use { it.readText() }
}
