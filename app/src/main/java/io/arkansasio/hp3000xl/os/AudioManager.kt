package io.arkansasio.hp3000xl.os

class AudioManager(private val state: OsState) : OsService {
    override val id = "audio"
    override fun start() = Unit
    override fun stop() = Unit
    fun setEnabled(enabled: Boolean) { state.soundEnabled = enabled }
    fun setVolume(percent: Int) { state.volumePercent = percent.coerceIn(0, 100) }
    fun playSystemTone(name: String): String = if (state.soundEnabled) "tone:$name" else "muted"
}
