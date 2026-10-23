package io.arkansasio.hp3000xl.platform

enum class CeInputKey { UP, DOWN, LEFT, RIGHT, SELECT, BACK, MENU, HOME, PAGE_UP, PAGE_DOWN, TAB, ENTER }

data class CePointer(val x: Float, val y: Float, val pressed: Boolean = false)
data class CeKeyEvent(val key: CeInputKey, val pressed: Boolean = true)

class CeInputRouter {
    private val keyHandlers = mutableListOf<(CeKeyEvent) -> Unit>()
    private val pointerHandlers = mutableListOf<(CePointer) -> Unit>()
    fun onKey(handler: (CeKeyEvent) -> Unit) { keyHandlers += handler }
    fun onPointer(handler: (CePointer) -> Unit) { pointerHandlers += handler }
    fun dispatch(event: CeKeyEvent) = keyHandlers.toList().forEach { it(event) }
    fun dispatch(pointer: CePointer) = pointerHandlers.toList().forEach { it(pointer) }
}
