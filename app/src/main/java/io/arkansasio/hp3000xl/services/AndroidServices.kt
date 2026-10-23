package io.arkansasio.hp3000xl.services

import android.content.Context
import android.widget.Toast

/** Android notification adapter retained as a lightweight pre-alpha shell service. */
class AndroidNotificationService(private val context: Context) : NotificationService {
    override fun notify(title: String, message: String) {
        Toast.makeText(context, "$title — $message", Toast.LENGTH_SHORT).show()
    }
}
