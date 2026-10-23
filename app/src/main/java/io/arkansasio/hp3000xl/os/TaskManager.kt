package io.arkansasio.hp3000xl.os

data class OsTask(val id: String, val title: String, var active: Boolean = true)

class TaskManager : OsService {
    override val id = "tasks"
    private val tasks = linkedMapOf<String, OsTask>()
    override fun start() = Unit
    override fun stop() { tasks.clear() }
    fun launch(id: String, title: String) { tasks[id] = OsTask(id, title) }
    fun close(id: String) { tasks.remove(id) }
    fun list(): List<OsTask> = tasks.values.toList()
}
