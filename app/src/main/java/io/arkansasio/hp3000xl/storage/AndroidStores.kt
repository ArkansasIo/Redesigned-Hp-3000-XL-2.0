package io.arkansasio.hp3000xl.storage

import android.content.Context
import android.content.SharedPreferences
import java.io.File

/** SharedPreferences-backed key/value store for shell settings. */
class AndroidPreferenceStore(context: Context) : PreferenceStore {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("hp3000xl_prefs", Context.MODE_PRIVATE)

    override fun get(key: String, default: String): String =
        prefs.getString(key, default) ?: default

    override fun put(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    override fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
}

/** File-backed document store inside app-private storage. */
class AndroidDocumentStore(private val context: Context) : DocumentStore {
    private val dir: File get() = context.filesDir.resolve("documents").apply { mkdirs() }
    private val metaFile: File get() = dir.resolve("meta.tsv")

    private fun loadMeta(): MutableList<Triple<Long, String, String>> {
        if (!metaFile.exists()) return mutableListOf()
        return metaFile.readLines().filter { it.isNotBlank() }.mapNotNull { line ->
            val parts = line.split('\t')
            if (parts.size >= 3) Triple(parts[0].toLongOrNull() ?: return@mapNotNull null, parts[1], parts[2]) else null
        }.toMutableList()
    }

    private fun saveMeta(meta: List<Triple<Long, String, String>>) {
        metaFile.writeText(meta.joinToString("\n") { "${it.first}\t${it.second}\t${it.third}" })
    }

    override fun list(): List<DocumentRecord> =
        loadMeta().map { DocumentRecord(it.first, it.second, it.third, dir.resolve(it.first.toString()).lastModified()) }

    override fun save(name: String, mimeType: String, data: ByteArray): Long {
        val meta = loadMeta()
        val id = (meta.maxOfOrNull { it.first } ?: 0L) + 1
        dir.resolve(id.toString()).writeBytes(data)
        meta.add(Triple(id, name, mimeType))
        saveMeta(meta)
        return id
    }

    override fun load(id: Long): ByteArray? {
        val file = dir.resolve(id.toString())
        return if (file.isFile) file.readBytes() else null
    }
}
