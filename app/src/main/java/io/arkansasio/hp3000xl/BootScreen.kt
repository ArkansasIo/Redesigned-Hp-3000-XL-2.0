package io.arkansasio.hp3000xl

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * Boot splash: runs the simulated bootloader POST, then the simulated Linux
 * kernel console log, then hands off to the CE-style shell.
 *
 * Everything on screen is text-only simulation — nothing touches firmware.
 */
@Composable
fun BootScreen(onBootComplete: () -> Unit) {
    val black = Color(0xFF000000)
    val green = Color(0xFF9FE870)
    val amber = Color(0xFFFFC857)
    val gray = Color(0xFFB9C0CC)

    var stage by remember { mutableStateOf(0) } // 0 = bootloader POST, 1 = kernel, 2 = done
    var postIndex by remember { mutableStateOf(0) }
    var kernelIndex by remember { mutableStateOf(0) }
    var initIndex by remember { mutableStateOf(0) }

    val post = remember { BootLoader.postSequence() }
    val kernelLog = remember { LinuxKernelSim.bootLog("hp3000xl", Runtime.getRuntime().maxMemory() / 1024, Runtime.getRuntime().availableProcessors()) }
    val initLog = remember { LinuxKernelSim.initLog() }
    val scroll = rememberScrollState()

    // progress bar
    val total = post.size + kernelLog.size + initLog.size
    val done = postIndex + kernelIndex + initIndex
    val progress by animateFloatAsState(targetValue = done.toFloat() / total, animationSpec = tween(120))

    LaunchedEffect(Unit) {
        // bootloader stage
        for (i in post.indices) { postIndex = i + 1; delay(post[i].delayMs) }
        stage = 1
        // kernel stage
        for (i in kernelLog.indices) { kernelIndex = i + 1; delay(kernelLog[i].delayMs) }
        // init stage
        for (i in initLog.indices) { initIndex = i + 1; delay(initLog[i].delayMs) }
        stage = 2
        delay(300)
        onBootComplete()
    }

    LaunchedEffect(done) { scroll.animateScrollTo(scroll.maxValue) }

    Column(
        Modifier.fillMaxSize().background(black).padding(16.dp)
    ) {
        // Header
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                if (stage == 0) "${BootLoader.NAME} ${BootLoader.VERSION}"
                else "hp3000xl-boot console",
                color = amber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Text("HP 3000 XL", color = gray, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        }
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(4.dp), color = green, trackColor = Color(0xFF20242C))

        // Console log
        Column(
            Modifier.weight(1f).fillMaxWidth().padding(top = 10.dp).verticalScroll(scroll)
                .background(Color(0xFF0B0E12), RoundedCornerShape(6.dp)).padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // Bootloader POST lines
            post.take(postIndex).forEach {
                Text("hp3k-bl: ${it.label} ... ${it.detail}", color = green, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
            }
            if (postIndex == post.size && stage >= 1) {
                Text("hp3k-bl: jumping to kernel entry point (simulated)", color = amber, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
                Spacer(Modifier.height(6.dp))
            }
            // Kernel console lines
            kernelLog.take(kernelIndex).forEach {
                Text(it.text, color = gray, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
            }
            // init lines
            initLog.take(initIndex).forEach {
                Text(it.text, color = green, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
            }
        }

        // Footer status
        Spacer(Modifier.height(8.dp))
        Text(
            when (stage) {
                0 -> "POST ${postIndex}/${post.size}"
                1 -> "kernel ${kernelIndex}/${kernelLog.size}  init ${initIndex}/${initLog.size}"
                else -> "boot complete — starting shell"
            },
            color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 11.sp
        )
    }
}
