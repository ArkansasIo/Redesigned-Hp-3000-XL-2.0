package io.arkansasio.hp3000xl.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class AndroidNetworkService(context: Context) : NetworkService {
    private val connectivity = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun state(): NetworkState {
        val network = connectivity.activeNetwork
        val capabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && network != null) {
            connectivity.getNetworkCapabilities(network)
        } else null
        val transport = when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "Wi-Fi"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Cellular"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> "Ethernet"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) == true -> "Bluetooth"
            capabilities != null -> "Other"
            else -> "Offline"
        }
        return NetworkState(
            connected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true,
            transport = transport,
            address = null
        )
    }
}
