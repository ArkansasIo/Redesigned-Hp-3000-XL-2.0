package io.arkansasio.hp3000xl.packages

/** Update-channel policy. Network download is intentionally kept outside this model. */
class CeUpdateManager(private val channel: String = "pre-alpha") {
    data class UpdateInfo(val version: String, val url: String, val sha256: String)

    private var pending: UpdateInfo? = null

    fun channel(): String = channel
    fun stage(update: UpdateInfo) { pending = update }
    fun pending(): UpdateInfo? = pending
    fun clear() { pending = null }

    fun isNewer(current: String, candidate: String): Boolean = compare(current, candidate) < 0

    private fun compare(a: String, b: String): Int {
        fun parts(v: String) = v.removePrefix("v").substringBefore('-').split('.').map { it.toIntOrNull() ?: 0 }
        val left = parts(a)
        val right = parts(b)
        for (i in 0 until maxOf(left.size, right.size)) {
            val x = left.getOrElse(i) { 0 }
            val y = right.getOrElse(i) { 0 }
            if (x != y) return x.compareTo(y)
        }
        return 0
    }
}
