package io.arkansasio.hp3000xl.os

/** Top-level runtime object for the recreated handheld operating environment. */
class OsRuntime {
    val kernel = OsKernel()
    val boot = OsBootManager(kernel)
    val applications = OsApplicationHost(kernel)

    fun start() = boot.start()

    fun stop() {
        applications.processes.list().forEach { applications.processes.stop(it.pid) }
        kernel.shutdown()
    }
}
