package io.arkansasio.hp3000xl.storage

data class DocumentRecord(val id: Long, val name: String, val mimeType: String, val modifiedAt: Long)

interface DocumentStore {
    fun list(): List<DocumentRecord>
    fun save(name: String, mimeType: String, data: ByteArray): Long
    fun load(id: Long): ByteArray?
}
