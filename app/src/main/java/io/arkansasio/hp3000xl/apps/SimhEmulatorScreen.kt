package io.arkansasio.hp3000xl.apps

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import java.util.Locale

/** SIMH front-end panel; launch stays disabled until a real machine engine is linked. */
@Composable
fun SimhEmulatorScreen(controller: SimhEmulatorApp, modifier: Modifier = Modifier) {
    var machine by remember { mutableStateOf(SimhMachine.HP3000) }
    var refresh by remember { mutableStateOf(0) }
    var mediaMessage by remember { mutableStateOf("No media imported yet.") }
    val prerequisites = remember(machine, refresh) { controller.launchPrerequisites(machine) }
    val nativeCapability = remember(refresh) { controller.nativeCapability() }
    val mediaPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri == null) {
            mediaMessage = "Media selection cancelled."
        } else {
            mediaMessage = try {
                val imported = controller.importMedia(uri, machine)
                refresh++
                "Imported ${imported.file.name} • ${String.format(Locale.US, "%,d", imported.bytesCopied)} bytes"
            } catch (error: Exception) {
                "Import failed: ${error.message ?: error.javaClass.simpleName}"
            }
        }
    }

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
                Text("Native bridge: ${nativeCapability.status} — ${nativeCapability.message}")
                Text("Target ABI: ${prerequisites.abi}")
                Text("Machine data directory", fontWeight = FontWeight.Bold)
                Text(prerequisites.dataDirectory, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.bodySmall)
                Button(onClick = { refresh++ }, modifier = Modifier.fillMaxWidth()) { Text("Refresh runtime status") }
            }
        }
        Text("System media", fontWeight = FontWeight.Bold)
        Text("Choose legally obtained tape, disk, or configuration media. The app copies the selected file into its private workspace; it does not bundle proprietary HP operating-system images.")
        Button(
            onClick = { mediaPicker.launch(arrayOf("*/*")) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Import media file") }
        Text(mediaMessage, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(4.dp))
        Button(
            onClick = { refresh++ },
            enabled = prerequisites.ready,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (prerequisites.ready) "Launch emulator" else "Emulator engine not available")
        }
        Text(
            "The current native library is only an integration scaffold. HP 3000 SIMH machine sources still need to be ported, linked, and tested with a valid guest image before the emulator can launch.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
