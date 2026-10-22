package io.arkansasio.hp3000xl.update

data class UpdateManifest(
    val version: String,
    val buildId: String,
    val channel: String,
    val releaseUrl: String,
    val sha256: String
)
