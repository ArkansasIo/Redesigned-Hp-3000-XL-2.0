package io.arkansasio.hp3000xl.platform

import android.content.Context
import java.io.File

class CeStorage(private val context: Context) {
    val root: File get() = context.filesDir
    fun resolve(relative: String): File {
        val base = root.canonicalFile
        val target = File(base, relative).canonicalFile
        require(target.path == base.path || target.path.startsWith(base.path + File.separator)) { "Path escapes application storage" }
        return target
    }
    fun list(relative: String = ""): List<String> = resolve(relative).list()?.toList() ?: emptyList()
    fun read(relative: String): ByteArray? = resolve(relative).takeIf { it.isFile }?.readBytes()
    fun write(relative: String, data: ByteArray) { resolve(relative).apply { parentFile?.mkdirs(); writeBytes(data) } }
    fun delete(relative: String): Boolean = resolve(relative).delete()
}
