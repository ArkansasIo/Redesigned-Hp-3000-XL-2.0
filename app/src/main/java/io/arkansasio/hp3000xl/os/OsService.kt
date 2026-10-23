package io.arkansasio.hp3000xl.os

/** Lifecycle contract for host services exposed by the recreated handheld OS. */
interface OsService {
    val id: String
    fun start()
    fun stop()
}
