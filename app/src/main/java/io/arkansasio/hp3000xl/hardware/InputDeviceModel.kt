package io.arkansasio.hp3000xl.hardware

enum class InputDeviceType { TOUCHSCREEN, KEYBOARD, MOUSE, GAMEPAD, STYLUS }

data class InputEventModel(val type: InputDeviceType, val code: Int, val x: Float = 0f, val y: Float = 0f, val pressed: Boolean = true)

class InputDeviceModel {
    private val supported = linkedSetOf(
        InputDeviceType.TOUCHSCREEN,
        InputDeviceType.KEYBOARD,
        InputDeviceType.MOUSE,
        InputDeviceType.STYLUS
    )

    fun supports(type: InputDeviceType): Boolean = type in supported
    fun devices(): List<InputDeviceType> = supported.toList()
}
