package io.arkansasio.hp3000xl.emulator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SimhConfigurationTest {
    @Test fun defaultsToHp3000AndDisabled() {
        val config = SimhConfiguration()
        assertEquals(false, config.enabled)
        assertEquals(SimhMachine.HP3000, config.machine)
        assertEquals("simh/hp3000", config.workingDirectoryName)
    }

    @Test fun rejectsInvalidConsolePort() {
        assertThrows(IllegalArgumentException::class.java) {
            SimhConfiguration(consolePort = 65536)
        }
    }

    @Test fun rejectsPathOutsideSimhWorkspace() {
        assertThrows(IllegalArgumentException::class.java) {
            SimhConfiguration(workingDirectoryName = "../downloads")
        }
    }
}
