package io.arkansasio.hp3000xl.apps

class SolitaireApp {
    val descriptor = OsAppCatalog.find("solitaire")!!
    var score = 0
    fun reset() { score = 0 }
}

class MinesweeperApp {
    val descriptor = OsAppCatalog.find("minesweeper")!!
    var rows = 9
    var columns = 9
    var mines = 10
    fun configure(rows: Int, columns: Int, mines: Int) {
        this.rows = rows.coerceAtLeast(1)
        this.columns = columns.coerceAtLeast(1)
        this.mines = mines.coerceIn(0, this.rows * this.columns - 1)
    }
}
