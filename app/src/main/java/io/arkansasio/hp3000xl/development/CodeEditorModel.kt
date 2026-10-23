package io.arkansasio.hp3000xl.development

data class EditorBuffer(val path: String, var text: String = "", var languageId: String = "text", var dirty: Boolean = false)

class CodeEditorModel {
    private val buffers = linkedMapOf<String, EditorBuffer>()
    var activePath: String? = null
        private set
    fun open(path: String, languageId: String = "text"): EditorBuffer = buffers.getOrPut(path) { EditorBuffer(path, languageId = languageId) }.also { activePath = path }
    fun setText(text: String) { activePath?.let { buffers[it]?.apply { this.text=text; dirty=true } } }
    fun save(path: String): EditorBuffer? = buffers[path]?.also { it.dirty = false }
    fun buffers(): List<EditorBuffer> = buffers.values.toList()
}
