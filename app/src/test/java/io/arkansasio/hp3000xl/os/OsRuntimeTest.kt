package io.arkansasio.hp3000xl.os

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OsRuntimeTest {
    @Test fun bootCompletes() {
        val runtime = OsRuntime()
        val stages = runtime.start()
        assertEquals(OsBootManager.Stage.COMPLETE, stages.last())
        assertTrue(runtime.kernel.state.booted)
    }

    @Test fun builtInApplicationGetsProcess() {
        val runtime = OsRuntime()
        runtime.start()
        val process = runtime.applications.launch("calculator")
        assertTrue(process != null)
        assertEquals("calculator", runtime.kernel.state.activeAppId)
        assertTrue(runtime.applications.close(process!!.pid))
        assertFalse(runtime.kernel.state.activeAppId != null)
    }

    @Test fun permissionManagerIsolatedByApp() {
        val permissions = OsPermissionManager()
        permissions.grant("calculator", OsPermission.CLIPBOARD)
        assertTrue(permissions.check("calculator", OsPermission.CLIPBOARD))
        assertFalse(permissions.check("terminal", OsPermission.CLIPBOARD))
    }
}
