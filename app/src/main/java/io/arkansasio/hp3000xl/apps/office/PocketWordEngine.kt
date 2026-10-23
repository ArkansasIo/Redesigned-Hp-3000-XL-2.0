package io.arkansasio.hp3000xl.apps.office

data class WordDocument(val title: String, val text: String, val modifiedAt: Long = System.currentTimeMillis())

class PocketWordEngine {
    private var document = WordDocument("Untitled", "")
    fun open(doc: WordDocument) { document = doc }
    fun append(text: String) { document = document.copy(text = document.text + text, modifiedAt = System.currentTimeMillis()) }
    fun replace(text: String) { document = document.copy(text = text, modifiedAt = System.currentTimeMillis()) }
    fun document(): WordDocument = document
}
