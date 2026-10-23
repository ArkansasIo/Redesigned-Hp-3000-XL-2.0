package io.arkansasio.hp3000xl.services

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class AndroidClipboardService(context: Context) : ClipboardService {
    private val clipboard = context.getSystemService(ClipboardManager::class.java)

    override fun copy(text: String) {
        clipboard?.setPrimaryClip(ClipData.newPlainText("HP3000XL", text))
    }

    override fun paste(): String? = clipboard?.primaryClip?.getItemAt(0)?.coerceToText(null)?.toString()
}
