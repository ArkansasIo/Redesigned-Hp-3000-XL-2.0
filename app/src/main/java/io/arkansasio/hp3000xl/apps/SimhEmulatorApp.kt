package io.arkansasio.hp3000xl.apps

import android.content.Context
import android.net.Uri
import io.arkansasio.hp3000xl.emulator.ImportedSimhMedia
import io.arkansasio.hp3000xl.emulator.SimhConfiguration
import io.arkansasio.hp3000xl.emulator.SimhMachine
import io.arkansasio.hp3000xl.emulator.SimhMediaImporter
import io.arkansasio.hp3000xl.emulator.SimhNativeBridge
import io.arkansasio.hp3000xl.emulator.SimhRuntime
import io.arkansasio.hp3000xl.emulator.SimhRuntimeState
import io.arkansasio.hp3000xl.emulator.SimhRuntimeStatus

/** App-facing controller; does not fake a simulator when native code is absent. */
class SimhEmulatorApp(context: Context) {
    private val appContext = context.applicationContext
    val configuration = SimhConfiguration()
    private val runtime = SimhRuntime(appContext)
    private val mediaImporter = SimhMediaImporter(appContext.contentResolver, runtime)

    fun availableMachines(): List<SimhMachine> = SimhMachine.entries
    fun status(machine: SimhMachine = configuration.machine): SimhRuntimeStatus = runtime.status(machine)
    fun workingDirectory(machine: SimhMachine = configuration.machine): String =
        runtime.machineDataDirectory(machine).absolutePath

    fun nativeCapability(): SimhNativeBridge.Capability = SimhNativeBridge.capability()

    fun importMedia(uri: Uri, machine: SimhMachine = configuration.machine): ImportedSimhMedia =
        mediaImporter.importImage(uri, machine)

    fun launchPrerequisites(machine: SimhMachine = configuration.machine): LaunchPrerequisites {
        val current = runtime.status(machine)
        val native = nativeCapability()
        val ready = current.state == SimhRuntimeState.READY && native.canLaunchMachine
        val status = when {
            current.state != SimhRuntimeState.READY -> current.message
            !native.canLaunchMachine -> "Native bridge: ${native.message}"
            else -> "Native runtime and bridge report available."
        }
        return LaunchPrerequisites(
            ready = ready,
            status = status,
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
