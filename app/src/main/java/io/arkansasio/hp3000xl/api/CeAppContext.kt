package io.arkansasio.hp3000xl.api

data class CeAppContext(
    val appId: String,
    val displayName: String,
    val version: String,
    val dataDirectory: String
)
