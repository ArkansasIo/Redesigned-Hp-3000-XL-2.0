package io.arkansasio.hp3000xl.hardware

data class AudioState(var enabled: Boolean = true, var volumePercent: Int = 70)

class AudioModel {
    val state = AudioState()
    fun setVolume(percent: Int) { state.volumePercent = percent.coerceIn(0, 100) }
    fun mute() { state.enabled = false }
    fun unmute() { state.enabled = true }
}
