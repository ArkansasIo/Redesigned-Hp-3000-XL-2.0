package io.arkansasio.hp3000xl.hardware

data class StorageVolume(val id: String, val label: String, val totalBytes: Long, val freeBytes: Long, val writable: Boolean)

class StorageModel {
    private val volumes = linkedMapOf<String, StorageVolume>()
    fun register(volume: StorageVolume) { volumes[volume.id] = volume }
    fun list(): List<StorageVolume> = volumes.values.toList()
    fun find(id: String): StorageVolume? = volumes[id]
}
