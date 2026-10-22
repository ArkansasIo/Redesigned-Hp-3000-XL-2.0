package io.arkansasio.hp3000xl.languages

data class CodeFile(val path: String, val languageId: String, val source: String)

class CodeWorkspace {
    private val files = linkedMapOf<String, CodeFile>()
    fun put(file: CodeFile) { files[file.path] = file }
    fun get(path: String): CodeFile? = files[path]
    fun list(): List<CodeFile> = files.values.toList()
}
