package io.arkansasio.hp3000xl.services

interface FileSystemService {
    fun list(path: String): List<String>
    fun read(path: String): ByteArray?
    fun write(path: String, data: ByteArray)
    fun delete(path: String): Boolean
}
