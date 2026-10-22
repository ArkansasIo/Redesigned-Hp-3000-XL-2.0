package io.arkansasio.hp3000xl.input

import android.view.KeyEvent
import android.view.MotionEvent

object AndroidInputMapper {
    fun key(event: KeyEvent): CeInputAction? = when (event.keyCode) {
        KeyEvent.KEYCODE_DPAD_UP -> CeInputAction.Up
        KeyEvent.KEYCODE_DPAD_DOWN -> CeInputAction.Down
        KeyEvent.KEYCODE_DPAD_LEFT -> CeInputAction.Left
        KeyEvent.KEYCODE_DPAD_RIGHT -> CeInputAction.Right
        KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_DPAD_CENTER -> CeInputAction.Select
        KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> CeInputAction.Back
        KeyEvent.KEYCODE_MENU -> CeInputAction.Menu
        else -> null
    }

    fun pointer(event: MotionEvent): Pair<Float, Float> = event.x to event.y
}
