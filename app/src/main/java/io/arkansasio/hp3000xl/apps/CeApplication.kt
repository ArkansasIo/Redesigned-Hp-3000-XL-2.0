package io.arkansasio.hp3000xl.apps

interface CeApplication {
    val id: String
    val name: String
    val version: String
    fun launch()
    fun close()
}
