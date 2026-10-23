package io.arkansasio.hp3000xl.apps.games

class MinesweeperEngine(val width: Int = 9, val height: Int = 9, val mines: Int = 10) {
    private val revealed = mutableSetOf<Pair<Int, Int>>()
    fun reveal(x: Int, y: Int): Boolean { require(x in 0 until width && y in 0 until height); return revealed.add(x to y) }
    fun isRevealed(x: Int, y: Int): Boolean = x to y in revealed
}

class SolitaireEngine {
    private val moves = mutableListOf<String>()
    fun move(description: String) { moves += description }
    fun history(): List<String> = moves.toList()
}
