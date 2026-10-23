package io.arkansasio.hp3000xl.os

class StorageManager(private val events: OsEventBus) : OsService {
    override val id = "storage"
    private var freeBytes: Long = 0L
    private var totalBytes: Long = 0L
    override fun start() = Unit
    override fun stop() = Unit
    fun update(total: Long, free: Long) {
        totalBytes = total.coerceAtLeast(0)
        freeBytes = free.coerceIn(0, totalBytes)
        events.publish(OsEvent.StorageChanged(freeBytes))
    }
    fun totalBytes() = totalBytes
    fun freeBytes() = freeBytes
}
