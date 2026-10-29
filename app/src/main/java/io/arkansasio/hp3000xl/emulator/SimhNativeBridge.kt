package io.arkansasio.hp3000xl.emulator

/**
 * Optional JNI boundary for the native SIMH port.
 *
 * The current C++ scaffold reports ENGINE_NOT_LINKED and must never be treated
 * as a runnable simulator. Calls are guarded so devices without the library
 * continue to use the rest of the HP 3000 XL app normally.
 */
object SimhNativeBridge {
    enum class EngineStatus(val code: Int) {
        AVAILABLE(0),
        ENGINE_NOT_LINKED(1),
        INVALID_ARGUMENT(2),
        NOT_RUNNING(3),
        INTERNAL_ERROR(4),
        LIBRARY_UNAVAILABLE(-1)
    }

    data class Capability(
        val bridgeVersion: String?,
        val status: EngineStatus,
        val message: String
    ) {
        val canLaunchMachine: Boolean
            get() = status == EngineStatus.AVAILABLE
    }

    private val loaded: Boolean by lazy {
        try {
            System.loadLibrary("simh_android_bridge")
            true
        } catch (_: UnsatisfiedLinkError) {
            false
        } catch (_: SecurityException) {
            false
        }
    }

    fun capability(): Capability {
        if (!loaded) {
            return Capability(
                bridgeVersion = null,
                status = EngineStatus.LIBRARY_UNAVAILABLE,
                message = "Native SIMH bridge is not packaged for this device."
            )
        }

        return try {
            val code = nativeEngineStatus()
            val status = EngineStatus.entries.firstOrNull { it.code == code }
                ?: EngineStatus.INTERNAL_ERROR
            Capability(
                bridgeVersion = nativeBridgeVersion(),
                status = status,
                message = nativeEngineStatusMessage()
            )
        } catch (error: UnsatisfiedLinkError) {
            Capability(
                bridgeVersion = null,
                status = EngineStatus.INTERNAL_ERROR,
                message = "Native bridge is incomplete: ${error.message ?: "missing JNI method"}"
            )
        }
    }

    private external fun nativeBridgeVersion(): String
    private external fun nativeEngineStatus(): Int
    private external fun nativeEngineStatusMessage(): String
}
