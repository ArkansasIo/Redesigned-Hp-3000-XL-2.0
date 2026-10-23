package io.arkansasio.hp3000xl.apps.office

class PocketExcelEngine(private val rows: Int = 64, private val columns: Int = 26) {
    private val cells = mutableMapOf<Pair<Int, Int>, String>()
    fun setCell(row: Int, column: Int, value: String) { require(row in 0 until rows && column in 0 until columns); cells[row to column] = value }
    fun getCell(row: Int, column: Int): String = cells[row to column] ?: ""
    fun nonEmptyCells(): Map<Pair<Int, Int>, String> = cells.toMap()
}
