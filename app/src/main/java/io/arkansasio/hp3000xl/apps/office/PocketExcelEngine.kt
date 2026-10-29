package io.arkansasio.hp3000xl.apps.office

/**
 * Small in-memory spreadsheet cell model used by the Pocket Excel application.
 * Rows and columns are zero-based; an empty string clears a cell.
 */
class PocketExcelEngine(private val rows: Int = 64, private val columns: Int = 26) {
    private val cells = mutableMapOf<Pair<Int, Int>, String>()

    init {
        require(rows > 0) { "Spreadsheet must have at least one row." }
        require(columns > 0) { "Spreadsheet must have at least one column." }
    }

    fun setCell(row: Int, column: Int, value: String) {
        require(row in 0 until rows && column in 0 until columns) {
            "Cell coordinate is outside the spreadsheet."
        }
        val key = row to column
        if (value.isEmpty()) {
            cells.remove(key)
        } else {
            cells[key] = value
        }
    }

    fun getCell(row: Int, column: Int): String {
        require(row in 0 until rows && column in 0 until columns) {
            "Cell coordinate is outside the spreadsheet."
        }
        return cells[row to column] ?: ""
    }

    /** Returns a stable row-major snapshot containing only populated cells. */
    fun nonEmptyCells(): Map<Pair<Int, Int>, String> =
        cells.entries
            .sortedWith(compareBy({ it.key.first }, { it.key.second }))
            .associateTo(linkedMapOf()) { it.key to it.value }
}
