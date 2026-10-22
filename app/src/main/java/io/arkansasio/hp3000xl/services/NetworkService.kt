package io.arkansasio.hp3000xl.services

data class NetworkState(val connected: Boolean, val transport: String, val address: String?)

interface NetworkService {
    fun state(): NetworkState
}
