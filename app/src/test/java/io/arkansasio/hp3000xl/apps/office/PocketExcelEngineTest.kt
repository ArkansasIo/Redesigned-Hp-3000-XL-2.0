package io.arkansasio.hp3000xl.apps.office

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class PocketExcelEngineTest {
    @Test
    fun storesAndReadsCellValues() {
        val sheet = PocketExcelEngine(rows = 3, columns = 4)
        sheet.setCell(1, 2, "Quarter 1")
        assertEquals("Quarter 1", sheet.getCell(1, 2))
        assertEquals("", sheet.getCell(0, 0))
    }

    @Test
    fun emptyValueClearsCell() {
        val sheet = PocketExcelEngine()
        sheet.setCell(0, 0, "draft")
        sheet.setCell(0, 0, "")
        assertEquals("", sheet.getCell(0, 0))
        assertTrue(sheet.nonEmptyCells().isEmpty())
    }

    @Test
    fun nonEmptyCellsAreReturnedInRowMajorOrder() {
        val sheet = PocketExcelEngine(rows = 4, columns = 4)
        sheet.setCell(2, 0, "C1")
        sheet.setCell(0, 3, "A4")
        sheet.setCell(0, 1, "A2")

        assertEquals(
            listOf(0 to 1, 0 to 3, 2 to 0),
            sheet.nonEmptyCells().keys.toList()
        )
    }

    @Test
    fun rejectsOutOfRangeCoordinates() {
        val sheet = PocketExcelEngine(rows = 2, columns = 2)
        assertThrows(IllegalArgumentException::class.java) { sheet.setCell(2, 0, "bad") }
        assertThrows(IllegalArgumentException::class.java) { sheet.getCell(0, 2) }
    }

    @Test
    fun rejectsInvalidDimensions() {
        assertThrows(IllegalArgumentException::class.java) { PocketExcelEngine(rows = 0) }
        assertThrows(IllegalArgumentException::class.java) { PocketExcelEngine(columns = -1) }
    }
}
