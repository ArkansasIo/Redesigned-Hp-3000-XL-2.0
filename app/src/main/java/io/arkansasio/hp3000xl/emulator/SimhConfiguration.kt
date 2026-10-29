package io.arkansasio.hp3000xl.emulator

/** Configuration for the optional SIMH native emulator integration. */
data class SimhConfiguration(
    val enabled: Boolean = false,
    val machine: SimhMachine = SimhMachine.HP3000,
    val consolePort: Int = 0,
    val workingDirectoryName: String = "simh/hp3000",
    val userSuppliedSystemImageName: String? = null
) {
    init {
        require(consolePort in 0..65535) { "Console port must be between 0 and 65535." }
        require(workingDirectoryName.startsWith("simh/")) { "SIMH data must stay under the SIMH app directory." }
    }
}

enum class SimhMachine(val label: String, val simulatorName: String, val description: String) {
    HP3000("HP 3000", "hp3000", "Classic HP 3000 minicomputer simulator"),
    PDP11("PDP-11", "pdp11", "Digital Equipment Corporation PDP-11 simulator"),
    VAX("VAX", "vax", "Digital Equipment Corporation VAX simulator")
}

enum class SimhRuntimeState { NOT_INSTALLED, READY, STARTING, RUNNING, STOPPED, FAILED }

data class SimhRuntimeStatus(
    val state: SimhRuntimeState = SimhRuntimeState.NOT_INSTALLED,
    val message: String = "SIMH native runtime is not installed for this device.",
    val machine: SimhMachine = SimhMachine.HP3000,
    val lastUpdatedEpochMs: Long = System.currentTimeMillis()
)
