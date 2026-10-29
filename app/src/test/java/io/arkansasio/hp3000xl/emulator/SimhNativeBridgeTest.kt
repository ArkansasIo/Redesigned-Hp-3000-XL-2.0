package io.arkansasio.hp3000xl.emulator

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SimhNativeBridgeTest {
    @Test fun engineNotLinkedCannotLaunch() {
        val capability = SimhNativeBridge.Capability(
            bridgeVersion = "0.1.0-scaffold",
            status = SimhNativeBridge.EngineStatus.ENGINE_NOT_LINKED,
            message = "SIMH engine is not linked."
        )
        assertFalse(capability.canLaunchMachine)
    }

    @Test fun onlyAvailableStatusCanLaunch() {
        val available = SimhNativeBridge.Capability(
            bridgeVersion = "1.0.0",
            status = SimhNativeBridge.EngineStatus.AVAILABLE,
            message = "Engine is available."
        )
        assertTrue(available.canLaunchMachine)
    }

    @Test fun missingLibraryCannotLaunch() {
        val capability = SimhNativeBridge.Capability(
            bridgeVersion = null,
            status = SimhNativeBridge.EngineStatus.LIBRARY_UNAVAILABLE,
            message = "Native bridge unavailable."
        )
        assertFalse(capability.canLaunchMachine)
    }
}
