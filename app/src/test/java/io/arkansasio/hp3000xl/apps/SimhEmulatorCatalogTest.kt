package io.arkansasio.hp3000xl.apps

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SimhEmulatorCatalogTest {
    @Test fun emulatorAppearsInOsCatalogAndBuiltInList() {
        assertNotNull(OsAppCatalog.find("simh-emulator"))
        assertTrue(BuiltInApps.ids.contains("simh-emulator"))
    }
}
