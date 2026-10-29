package io.arkansasio.hp3000xl.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.arkansasio.hp3000xl.emulator.SimhMachine

/** SIMH front-end panel; launch remains disabled until the native port is shipped. */
@Composable
fun SimhEmulatorScreen(controller: SimhEmulatorApp, modifier: Modifier = Modifier) {
    var machine by remember { mutableStateOf(SimhMachine.HP3000) }
    var refresh by remember { mutableStateOf(0) }
    val prerequisites = remember(machine, refresh) { controller.launchPrerequisites(machine) }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("SIMH Machine Emulator", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("HP 3000 XL 2.0 • Native emulator integration")
        HorizontalDivider()
        Text("Machine", fontWeight = FontWeight.Bold)
        controller.availableMachines().forEach { option ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (machine == option) {
                    Button(onClick = { machine = option }, modifier = Modifier.weight(1f)) { Text(option.label) }
                } else {
                    OutlinedButton(onClick = { machine = option }, modifier = Modifier.weight(1f)) { Text(option.label) }
                }
            }
            Text(option.description, style = MaterialTheme.typography.bodySmall)
        }
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF0F7))) {
            Column(Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Runtime status", fontWeight = FontWeight.Bold)
                Text(prerequisites.status)
                Text("Target ABI: ${prerequisites.abi}")
                Text("Machine data directory", fontWeight = FontWeight.Bold)
                Text(prerequisites.dataDirectory, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.bodySmall)
                Button(onClick = { refresh++ }, modifier = Modifier.fillMaxWidth()) { Text("Refresh runtime status") }
            }
        }
        Text("System media", fontWeight = FontWeight.Bold)
        Text("Import your own legally obtained operating-system tapes, disk images and configuration files after the native runtime is available. Proprietary HP firmware, ROM images and operating-system media are not bundled.")
        Spacer(Modifier.height(4.dp))
        Button(onClick = { refresh++ }, enabled = prerequisites.ready, modifier = Modifier.fillMaxWidth()) {
            Text(if (prerequisites.ready) "Native runtime detected — launch bridge required" else "Emulator not installed")
        }
        Text(
            "The SIMH repository contains HP 3000 C sources; this UI does not compile them. An Android NDK/CMake/JNI port and device-ABI tests are required before emulation can run.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
