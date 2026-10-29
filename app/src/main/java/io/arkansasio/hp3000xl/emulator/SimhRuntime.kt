package io.arkansasio.hp3000xl.emulator

import android.content.Context
import android.os.Build
import java.io.File

/**
 * Runtime boundary between the HP 3000 XL shell and the native SIMH port.
 * A packaged bridge is not enough: the native capability check must confirm
 * that the actual machine engine is linked before the UI reports READY.
 */
class SimhRuntime(private val context: Context) {
    private val simulatorDirectory: File
        get() = File(context.filesDir, "simh").apply { mkdirs() }

    fun supportedAbi(): String = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown"

    fun status(machine: SimhMachine = SimhMachine.HP3000): SimhRuntimeStatus {
        val capability = SimhNativeBridge.capability()
        return if (capability.canLaunchMachine) {
            SimhRuntimeStatus(
                state = SimhRuntimeState.READY,
                message = "Native engine reports available for ${machine.label}; guest boot is not confirmed until a boot smoke test passes.",
                machine = machine
            )
        } else {
            val detail = when (capability.status) {
                SimhNativeBridge.EngineStatus.LIBRARY_UNAVAILABLE ->
                    "Native bridge is not packaged for ABI ${supportedAbi()}."
                SimhNativeBridge.EngineStatus.ENGINE_NOT_LINKED ->
                    "Native bridge is present, but the SIMH engine is not linked. No guest machine can start."
                else -> capability.message
            }
            SimhRuntimeStatus(
                state = SimhRuntimeState.NOT_INSTALLED,
                message = "$detail Emulator status: ${capability.status.name}.",
                machine = machine
            )
        }
    }

    fun machineDataDirectory(machine: SimhMachine = SimhMachine.HP3000): File {
        val base = simulatorDirectory.canonicalFile
        val target = File(base, machine.simulatorName).canonicalFile
        require(target.path.startsWith(base.path + File.separator)) {
            "Invalid SIMH machine data path"
        }
        if (!target.exists()) target.mkdirs()
        return target
    }
}
