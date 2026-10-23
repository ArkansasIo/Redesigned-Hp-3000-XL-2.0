package io.arkansasio.hp3000xl.apps

data class OsApp(
    val id: String,
    val title: String,
    val category: String,
    val version: String = "1.0.0",
    val system: Boolean = false
)
