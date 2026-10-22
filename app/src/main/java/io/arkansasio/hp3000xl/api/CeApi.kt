package io.arkansasio.hp3000xl.api

interface CeApi {
    fun version(): String
    fun applications(): List<String>
    fun getSetting(key: String): String?
    fun setSetting(key: String, value: String)
}
