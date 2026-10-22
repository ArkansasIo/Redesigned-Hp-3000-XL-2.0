package io.arkansasio.hp3000xl.input

enum class CeInputAction { Up, Down, Left, Right, Select, Back, Menu, Start }

interface InputRouter {
    fun dispatch(action: CeInputAction): Boolean
}
