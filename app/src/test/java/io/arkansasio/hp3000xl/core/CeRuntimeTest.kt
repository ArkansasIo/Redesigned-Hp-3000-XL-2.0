package io.arkansasio.hp3000xl.core

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CeRuntimeTest {
    @Test fun runtimeStartsAndStops() {
        val runtime = CeRuntime()
        assertFalse(runtime.state.running)
        runtime.start()
        assertTrue(runtime.state.running)
        runtime.stop()
        assertFalse(runtime.state.running)
    }
}
