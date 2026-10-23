package io.arkansasio.hp3000xl.apps

class PocketWordApp {
    val descriptor = OsAppCatalog.find("pocket-word")!!
    var documentName = "Document1"
    var text = ""
    fun newDocument() { documentName = "Document1"; text = "" }
}

class PocketExcelApp {
    val descriptor = OsAppCatalog.find("pocket-excel")!!
    private val cells = mutableMapOf<String, String>()
    fun setCell(address: String, value: String) { cells[address.uppercase()] = value }
    fun getCell(address: String): String = cells[address.uppercase()].orEmpty()
    fun snapshot(): Map<String, String> = cells.toMap()
}
