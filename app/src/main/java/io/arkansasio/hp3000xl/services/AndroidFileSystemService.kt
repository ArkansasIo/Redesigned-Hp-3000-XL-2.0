package io.arkansasio.hp3000xl.services

import android.content.Context
import java.io.File

class AndroidFileSystemService(private val context: Context) : FileSystemService {
    private fun resolve(path: String): File {
        val root = context.filesDir.canonicalFile
        val requested = File(root, path.trimStart('/')).canonicalFile
        require(requested.path == root.path || requested.path.startsWith(root.path + File.separator)) {
            "Path escapes application storage"
        }
        return requested
    }

    override fun list(path: String): List<String> = resolve(path).list()?.sorted() ?: emptyList()

    override fun read(path: String): ByteArray? {
        val file = resolve(path)
        return if (file.isFile) file.readBytes() else null
    }

    override fun write(path: String, data: ByteArray) {
        val file = resolve(path)
        file.parentFile?.mkdirs()
        file.writeBytes(data)
    }

    override fun delete(path: String): Boolean = resolve(path).delete()
}
