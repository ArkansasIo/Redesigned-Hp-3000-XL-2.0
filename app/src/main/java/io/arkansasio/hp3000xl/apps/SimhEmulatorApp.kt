package io.arkansasio.hp3000xl.apps

import android.content.Context
import io.arkansasio.hp3000xl.emulator.SimhConfiguration
import io.arkansasio.hp3000xl.emulator.SimhMachine
import io.arkansasio.hp3000xl.emulator.SimhRuntime
import io.arkansasio.hp3000xl.emulator.SimhRuntimeState
import io.arkansasio.hp3000xl.emulator.SimhRuntimeStatus

/** App-facing controller; does not fake a simulator when native code is absent. */
class SimhEmulatorApp(context: Context) {
    val configuration = SimhConfiguration()
    private val runtime = SimhRuntime(context.applicationContext)

    fun availableMachines(): List<SimhMachine> = SimhMachine.entries
    fun status(machine: SimhMachine = configuration.machine): SimhRuntimeStatus = runtime.status(machine)
    fun workingDirectory(machine: SimhMachine = configuration.machine): String =
        runtime.machineDataDirectory(machine).absolutePath

    fun launchPrerequisites(machine: SimhMachine = configuration.machine): LaunchPrerequisites {
        val current = runtime.status(machine)
        return LaunchPrerequisites(
            ready = current.state == SimhRuntimeState.READY,
            status = current.message,
            machine = machine,
            abi = runtime.supportedAbi(),
            dataDirectory = runtime.machineDataDirectory(machine).absolutePath
        )
    }
}

data class LaunchPrerequisites(
    val ready: Boolean,
    val status: String,
    val machine: SimhMachine,
    val abi: String,
    val dataDirectory: String
)
