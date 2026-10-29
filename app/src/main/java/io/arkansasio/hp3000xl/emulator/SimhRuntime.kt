package io.arkansasio.hp3000xl.emulator

import android.content.Context
import android.os.Build
import java.io.File

/**
 * Runtime boundary between the HP 3000 XL shell and a future SIMH native build.
 * It never executes arbitrary commands or downloads native binaries.
 */
class SimhRuntime(private val context: Context) {
    private val simulatorDirectory: File
        get() = File(context.filesDir, "simh").apply { mkdirs() }

    fun supportedAbi(): String = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown"

    fun status(machine: SimhMachine = SimhMachine.HP3000): SimhRuntimeStatus {
        val library = nativeLibraryFor(machine)
        return if (library != null && library.isFile && library.canRead()) {
            SimhRuntimeStatus(
                state = SimhRuntimeState.READY,
                message = "Native library detected; machine launch still requires a tested JNI bridge.",
                machine = machine
            )
        } else {
            SimhRuntimeStatus(
                state = SimhRuntimeState.NOT_INSTALLED,
                message = "SIMH ${machine.simulatorName} is not packaged for ${supportedAbi()}. Emulation has not started.",
                machine = machine
            )
        }
    }

    fun machineDataDirectory(machine: SimhMachine = SimhMachine.HP3000): File {
        val base = simulatorDirectory.canonicalFile
        val target = File(base, machine.simulatorName).canonicalFile
        require(target.path.startsWith(base.path + File.separator)) { "Invalid SIMH machine data path" }
        if (!target.exists()) target.mkdirs()
        return target
    }

    private fun nativeLibraryFor(machine: SimhMachine): File? {
        val library = File(context.applicationInfo.nativeLibraryDir, "libsimh_${machine.simulatorName}.so")
        return library.takeIf { it.isFile }
    }
}
