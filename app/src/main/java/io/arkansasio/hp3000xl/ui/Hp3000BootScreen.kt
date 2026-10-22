package io.arkansasio.hp3000xl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.arkansasio.hp3000xl.BuildInfo

private val BootBackground = Color(0xFFB8C9D4)
private val BootPanel = Color(0xFFD4D9DD)
private val BootEdge = Color(0xFF7B8790)
private val CommandBlue = Color(0xFF164A78)
private val DeepBlue = Color(0xFF092B4A)
private val TextColor = Color(0xFF10202C)
private val Success = Color(0xFF357A50)

@Composable
fun Hp3000BootScreen(progress: Float = 1f) {
    Box(Modifier.fillMaxSize().background(BootBackground).padding(14.dp)) {
        Column(
            Modifier.fillMaxSize().border(2.dp, BootEdge).padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("HP 3000 XL", color = DeepBlue, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("COLOR MOBILE EDITION", color = CommandBlue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Column(Modifier.fillMaxWidth().background(BootPanel).border(1.dp, BootEdge).padding(10.dp)) {
                Text("SYSTEM STARTUP", color = DeepBlue, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                BootCheck("MEMORY")
                BootCheck("DISPLAY")
                BootCheck("TOUCH")
                BootCheck("KEYBOARD")
                BootCheck("MOUSE")
                BootCheck("STORAGE")
                BootCheck("AUDIO")
                BootCheck("SYSTEM SERVICES")
                Spacer(Modifier.height(10.dp))
                LinearProgressIndicator(progress = { progress.coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(5.dp))
                Text("Starting System...", color = TextColor, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
            }

            Column {
                Text("BUILD ${BuildInfo.BUILD_VERSION} PRE-ALPHA", color = DeepBlue, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
                Text(BuildInfo.BUILD_ID, color = TextColor, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
                Text("ANDROID 10+ / API ${BuildInfo.MIN_ANDROID_API}", color = TextColor, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
            }
        }
    }
}

@Composable
private fun BootCheck(label: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("[ OK ]", color = Success, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        Spacer(Modifier.width(8.dp))
        Text(label, color = TextColor, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
    }
}

@Composable
fun Hp3000LoadingScreen(message: String = "Loading Color Mobile Edition...") {
    Box(Modifier.fillMaxSize().background(BootBackground), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("HP 3000 XL", color = DeepBlue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(message, color = TextColor, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(modifier = Modifier.width(240.dp))
        }
    }
}
