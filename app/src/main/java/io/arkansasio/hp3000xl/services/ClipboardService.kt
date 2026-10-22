package io.arkansasio.hp3000xl.services

interface ClipboardService {
    fun copy(text: String)
    fun paste(): String?
}
