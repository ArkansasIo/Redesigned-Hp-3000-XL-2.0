package io.arkansasio.hp3000xl.hardware

data class DisplayMetrics(val widthPx: Int, val heightPx: Int, val density: Float)

enum class DisplayMode { COLOR, MONOCHROME }

class DisplayModel(private val metrics: DisplayMetrics, var mode: DisplayMode = DisplayMode.COLOR) {
    fun metrics(): DisplayMetrics = metrics
    fun isColor(): Boolean = mode == DisplayMode.COLOR
}
