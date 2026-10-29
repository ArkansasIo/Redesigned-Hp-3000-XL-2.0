package io.arkansasio.hp3000xl.services

import android.content.Context
import android.os.Build

class AndroidDeviceService(@Suppress("UNUSED_PARAMETER") context: Context) : DeviceService {
    override fun info(): DeviceInfo = DeviceInfo(
        manufacturer = Build.MANUFACTURER.orEmpty(),
        model = Build.MODEL.orEmpty(),
        androidRelease = Build.VERSION.RELEASE.orEmpty(),
        apiLevel = Build.VERSION.SDK_INT
    )
}
