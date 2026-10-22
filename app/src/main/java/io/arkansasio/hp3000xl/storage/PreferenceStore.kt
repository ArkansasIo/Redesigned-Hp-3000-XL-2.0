package io.arkansasio.hp3000xl.storage

interface PreferenceStore {
    fun get(key: String, default: String = ""): String
    fun put(key: String, value: String)
    fun remove(key: String)
}
