package io.arkansasio.hp3000xl.packages

data class HpAppManifest(
    val id: String,
    val name: String,
    val version: String,
    val entryPoint: String,
    val minApi: Int = 1,
    val permissions: List<String> = emptyList()
)
