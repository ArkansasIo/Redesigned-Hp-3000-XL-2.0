package io.arkansasio.hp3000xl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.arkansasio.hp3000xl.ui.Hp3000Theme
import io.arkansasio.hp3000xl.ui.HpBorder
import io.arkansasio.hp3000xl.ui.HpChrome
import io.arkansasio.hp3000xl.ui.HpDarkChrome
import io.arkansasio.hp3000xl.ui.HpDesktopBlue
import io.arkansasio.hp3000xl.ui.HpNavy
import io.arkansasio.hp3000xl.terminal.SystemTerminalSession
import io.arkansasio.hp3000xl.apps.SimhEmulatorApp
import io.arkansasio.hp3000xl.apps.SimhEmulatorScreen
import androidx.compose.ui.platform.LocalContext
import io.arkansasio.hp3000xl.core.CeOs
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Navy = HpNavy
private val Window = Color(0xFFF4F6FA)
private val Border = Color(0xFF6E7785)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Boot the OS core before the shell comes up.
        CeOs.boot(listOf("power", "storage", "clipboard", "notifications", "device", "network"))
        setContent {
            Hp3000Theme {
                var booted by remember { mutableStateOf(false) }
                if (booted) Hp3000Shell() else BootScreen(onBootComplete = { booted = true })
            }
        }
    }
}

private enum class CeApp(val title: String, val icon: String) {
    FileExplorer("File Explorer", "📁"), PocketWord("Pocket Word", "📝"), PocketExcel("Pocket Excel", "📊"),
    Inbox("Inbox / Contacts", "✉️"), Calendar("Calendar", "📅"), Calculator("Calculator", "🧮"),
    Internet("Internet Explorer", "🌐"), Terminal("Terminal", "⌨️"), ControlPanel("Control Panel", "⚙️"),
    TaskManager("Task Manager", "📈"), Help("Help", "❔"), Solitaire("Solitaire", "🃏"),
    Minesweeper("Minesweeper", "💣"), SimhEmulator("SIMH Emulator", "🖥️"), About("System Information", "ℹ️")
}

@Composable
private fun Hp3000Shell() {
    var app by remember { mutableStateOf<CeApp?>(null) }
    var start by remember { mutableStateOf(false) }
    var dark by remember { mutableStateOf(false) }
    var volume by remember { mutableStateOf(true) }
    val background = if (dark) HpDarkChrome else HpDesktopBlue

    BackHandler(enabled = app != null || start) {
        if (start) start = false else app = null
    }

    MaterialTheme(colorScheme = lightColorScheme(primary = Navy, surface = Window)) {
        Box(Modifier.fillMaxSize().background(background)) {
            if (app == null) Desktop { app = it } else AppWindow(
                app = app!!, dark = dark, volume = volume,
                onDark = { dark = !dark }, onVolume = { volume = !volume }, onClose = { app = null }
            )
            if (start) StartMenu(onOpen = { app = it; start = false }, onClose = { start = false })
            Taskbar(startOpen = start, volume = volume, onStart = { start = !start }, onHome = { app = null; start = false })
        }
    }
}

@Composable
private fun Desktop(onOpen: (CeApp) -> Unit) {
    Column(Modifier.fillMaxSize().padding(bottom = 46.dp)) {
        Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("HP 3000 XL", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Color Mobile Edition", color = Color.White, fontSize = 12.sp)
        }
        Column(Modifier.padding(10.dp).verticalScroll(rememberScrollState())) {
            CeApp.entries.chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { DesktopTile(it, onOpen) }
                    repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
                }
            }
        }
    }
}

@Composable
private fun RowScope.DesktopTile(app: CeApp, onOpen: (CeApp) -> Unit) {
    Card(Modifier.weight(1f).height(82.dp).clickable { onOpen(app) }, colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .93f)), shape = RoundedCornerShape(4.dp)) {
        Column(Modifier.fillMaxSize().padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(app.icon, fontSize = 24.sp)
            Text(app.title, fontSize = 10.sp, textAlign = TextAlign.Center, maxLines = 2)
        }
    }
}

@Composable
private fun BoxScope.Taskbar(startOpen: Boolean, volume: Boolean, onStart: () -> Unit, onHome: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(44.dp).align(Alignment.BottomCenter).background(Color(0xFFE7EAF0)).border(1.dp, Border).padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = onStart, modifier = Modifier.height(34.dp), shape = RoundedCornerShape(2.dp)) { Text(if (startOpen) "START ▲" else "START", fontSize = 11.sp) }
        Spacer(Modifier.width(6.dp))
        OutlinedButton(onClick = onHome, modifier = Modifier.height(34.dp), shape = RoundedCornerShape(2.dp)) { Text("▣") }
        Spacer(Modifier.weight(1f))
        Text(if (volume) "🔊" else "🔇")
        Spacer(Modifier.width(8.dp))
        Text(SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()), fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun BoxScope.StartMenu(onOpen: (CeApp) -> Unit, onClose: () -> Unit) {
    Column(Modifier.width(310.dp).align(Alignment.BottomStart).padding(start = 4.dp, bottom = 46.dp).background(Color.White).border(1.dp, Navy).padding(5.dp)) {
        Text("HP 3000 XL  •  PRE-ALPHA", Modifier.fillMaxWidth().background(Navy).padding(9.dp), color = Color.White, fontWeight = FontWeight.Bold)
        listOf(CeApp.FileExplorer, CeApp.PocketWord, CeApp.PocketExcel, CeApp.Inbox, CeApp.Calendar, CeApp.Calculator, CeApp.Internet, CeApp.SimhEmulator, CeApp.ControlPanel, CeApp.About).forEach { MenuItem("${it.icon}  ${it.title}") { onOpen(it) } }
        HorizontalDivider()
        MenuItem("⏻  Close Menu") { onClose() }
    }
}

@Composable
private fun AppWindow(app: CeApp, dark: Boolean, volume: Boolean, onDark: () -> Unit, onVolume: () -> Unit, onClose: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(6.dp, 6.dp, 6.dp, 50.dp).background(Window).border(1.dp, Border)) {
        Row(Modifier.fillMaxWidth().background(Navy).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("${app.icon}  ${app.title}", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("×", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { onClose() })
        }
        Row(Modifier.fillMaxWidth().background(Color(0xFFE8ECF3)).border(1.dp, Border)) {
            listOf("File", "Edit", "View", "Tools", "Help").forEach { Text(it, Modifier.padding(horizontal = 10.dp, vertical = 5.dp), fontSize = 11.sp) }
        }
        Box(Modifier.fillMaxSize().padding(8.dp)) {
            when (app) {
                CeApp.About -> AboutView()
                CeApp.ControlPanel -> SettingsView(dark, volume, onDark, onVolume)
                CeApp.FileExplorer -> SimpleList("\\", listOf("My Documents", "Program Files", "Windows", "Temp", "My Pictures", "Storage Card"))
                CeApp.PocketWord -> TextEditor("Pocket Word", "Welcome to HP 3000 XL Pocket Word.\n\nType, edit and organize documents.")
                CeApp.PocketExcel -> TextEditor("Pocket Excel", "A1     B1     C1\n1      10     20\n2      30     40\n3      =SUM(B1:C2)")
                CeApp.Inbox -> SimpleList("Inbox / Contacts", listOf("Welcome to HP 3000 XL", "System update available", "Meeting reminder", "Home", "Work"))
                CeApp.Calendar -> SimpleList("October 2026", listOf("09:00 Morning briefing", "13:00 Lunch", "17:00 Daily shutdown"))
                CeApp.Calculator -> CalculatorView()
                CeApp.Internet -> SimpleList("Internet Explorer", listOf("Home", "Favorites", "History", "Network status: available"))
                CeApp.TaskManager -> TaskListView()
                CeApp.Terminal -> TerminalView()
                CeApp.SimhEmulator -> {
                    val context = LocalContext.current
                    SimhEmulatorScreen(controller = remember(context) { SimhEmulatorApp(context) })
                }
                CeApp.Help -> SimpleList("Help", listOf("Desktop", "Start Menu", "File Explorer", "System Settings", "System Updates", "Application Packages"))
                CeApp.Solitaire -> SimpleList("Solitaire", listOf("♠  ♥  ♣  ♦", "New Game", "Save Game"))
                CeApp.Minesweeper -> SimpleList("Minesweeper", listOf("▦  ▦  ▦", "▦  1  2", "▦  2  💣"))
            }
        }
    }
}

@Composable
private fun AboutView() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(BuildInfo.PRODUCT_NAME, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Navy)
        Text(BuildInfo.CODENAME, fontSize = 15.sp)
        HorizontalDivider()
        InfoRow("Version", BuildInfo.VERSION_NAME)
        InfoRow("Build ID", BuildInfo.BUILD_ID)
        InfoRow("Channel", BuildInfo.RELEASE_CHANNEL.uppercase())
        InfoRow("Developer", BuildInfo.DEVELOPER)
        InfoRow("Package", BuildInfo.PACKAGE_NAME)
        InfoRow("Minimum Android", "API ${BuildInfo.MIN_ANDROID_API}")
        InfoRow("Target SDK", BuildInfo.TARGET_ANDROID_API.toString())
        Text("Independent recreation. Microsoft Windows CE and HP proprietary firmware, ROMs, binaries and copyrighted system assets are not included.", fontSize = 12.sp)
    }
}

@Composable private fun InfoRow(label: String, value: String) { Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) { Text(label, Modifier.width(125.dp), fontWeight = FontWeight.Bold); Text(value) } }

@Composable private fun SettingsView(dark: Boolean, volume: Boolean, onDark: () -> Unit, onVolume: () -> Unit) {
    Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Control Panel", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Button(onClick = onDark) { Text(if (dark) "Use Light Theme" else "Use Dark Theme") }
        Button(onClick = onVolume) { Text(if (volume) "Mute Sound" else "Enable Sound") }
        Text("System Update", fontWeight = FontWeight.Bold)
        Text("Update channel: ${BuildInfo.RELEASE_CHANNEL}")
        Text("Current build: ${BuildInfo.BUILD_ID}")
        Text("Updates are verified before installation.")
    }
}

@Composable private fun SimpleList(title: String, items: List<String>) { Column(Modifier.verticalScroll(rememberScrollState())) { Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Navy); Spacer(Modifier.height(8.dp)); items.forEach { MenuItem(it) {} } } }

@Composable private fun TextEditor(title: String, initial: String) { var text by remember { mutableStateOf(initial) }; Column { Text(title, fontWeight = FontWeight.Bold); Spacer(Modifier.height(6.dp)); BasicTextField(text, { text = it }, Modifier.fillMaxSize().background(Color.White).border(1.dp, Border).padding(10.dp), textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 13.sp)) } }

@Composable private fun CalculatorView() { var value by remember { mutableStateOf("") }; Column { Text(value.ifEmpty { "0" }, Modifier.fillMaxWidth().background(Color.White).border(1.dp, Border).padding(12.dp), textAlign = TextAlign.End, fontSize = 22.sp); Spacer(Modifier.height(8.dp)); listOf("7 8 9 /", "4 5 6 *", "1 2 3 -", "0 . = +").forEach { row -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { row.split(" ").forEach { key -> Button(onClick = { value = if (key == "=") value else value + key }, modifier = Modifier.weight(1f)) { Text(key) } } } } } }

@Composable private fun TerminalView() {
    val session = remember { SystemTerminalSession() }
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf(session.prompt()) }
    Column {
        Text("HP3000XL Terminal", fontWeight = FontWeight.Bold)
        Text("Restricted command shell. No arbitrary code execution.", fontSize = 11.sp)
        Spacer(Modifier.height(8.dp))
        Text(output, Modifier.fillMaxSize().weight(1f).background(Color.Black).padding(10.dp).verticalScroll(rememberScrollState()), color = Color(0xFF9FEF9F), fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(">", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(6.dp))
            BasicTextField(input, { input = it }, Modifier.weight(1f).background(Color.White).border(1.dp, Border).padding(8.dp), textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace), singleLine = true)
            Spacer(Modifier.width(6.dp))
            Button(onClick = {
                val result = session.execute(input)
                output = if (result == "\u000C") session.prompt() else output + "\n> " + input + "\n" + result + "\n" + session.prompt()
                input = ""
            }) { Text("Run") }
        }
    }
}

@Composable private fun TaskListView() {
    Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Task Manager", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Navy)
        Spacer(Modifier.height(4.dp))
        Text("OS: ${CeOs.name} ${CeOs.version} (${CeOs.buildId})", fontWeight = FontWeight.Bold)
        Text("Uptime: ${CeOs.uptimeMs() / 1000}s")
        Spacer(Modifier.height(6.dp))
        Text("Services", fontWeight = FontWeight.Bold)
        CeOs.runningServices().forEach { MenuItem("● $it — running") {} }
        Spacer(Modifier.height(6.dp))
        Text("Tasks", fontWeight = FontWeight.Bold)
        val tasks = CeOs.runningTasks()
        if (tasks.isEmpty()) Text("(no user tasks)") else tasks.forEach { MenuItem("pid=${it.pid}  ${it.title}  [${it.state}]") {} }
        Spacer(Modifier.height(6.dp))
        Text("Recent events", fontWeight = FontWeight.Bold)
        CeOs.recentEvents(12).forEach { Text("[${it.source}] ${it.message}", fontSize = 11.sp, fontFamily = FontFamily.Monospace) }
    }
}

@Composable private fun MenuItem(label: String, onClick: () -> Unit) { Row(Modifier.fillMaxWidth().clickable { onClick() }.padding(9.dp), verticalAlignment = Alignment.CenterVertically) { Text(label, fontSize = 13.sp) }
}
