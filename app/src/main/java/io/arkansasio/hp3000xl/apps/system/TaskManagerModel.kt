package io.arkansasio.hp3000xl.apps.system

data class TaskInfo(val pid: Int, val appId: String, val state: String, val foreground: Boolean)

class TaskManagerModel {
    private val tasks = mutableListOf<TaskInfo>()
    fun replace(value: List<TaskInfo>) { tasks.clear(); tasks.addAll(value) }
    fun tasks(): List<TaskInfo> = tasks.toList()
}
