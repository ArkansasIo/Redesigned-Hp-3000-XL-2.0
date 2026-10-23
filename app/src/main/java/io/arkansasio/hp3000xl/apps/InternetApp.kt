package io.arkansasio.hp3000xl.apps

class InternetApp {
    val descriptor = OsAppCatalog.find("internet")!!
    private var currentUrl = "about:home"
    fun navigate(url: String) { currentUrl = url.trim().ifEmpty { "about:blank" } }
    fun url(): String = currentUrl
}
