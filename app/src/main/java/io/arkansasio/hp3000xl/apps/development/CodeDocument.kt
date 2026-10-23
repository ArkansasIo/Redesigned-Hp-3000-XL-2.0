package io.arkansasio.hp3000xl.apps.development

data class CodeDocument(val path: String, var language: String, var text: String, var dirty: Boolean = false)

class CodeDocumentStore {
    private val documents = linkedMapOf<String, CodeDocument>()
    fun open(path: String, language: String = "text"): CodeDocument = documents.getOrPut(path) { CodeDocument(path, language, "") }
    fun save(document: CodeDocument) { document.dirty = false; documents[document.path] = document }
    fun list(): List<CodeDocument> = documents.values.toList()
}
