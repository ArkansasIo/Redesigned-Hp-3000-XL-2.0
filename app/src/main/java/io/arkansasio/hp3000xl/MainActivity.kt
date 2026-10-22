package io.arkansasio.hp3000xl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Navy = Color(0xFF003399)
private val Blue = Color(0xFF316AC5)
private val BlueLight = Color(0xFFDCEBFF)
private val Window = Color(0xFFF4F6FA)
private val Border = Color(0xFF6E7785)
private val TextDark = Color(0xFF18202A)
private val Green = Color(0xFF16834B)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HP3000XLWindowsCE() }
    }
}

private sealed class ScreenState {
    data object Desktop : ScreenState()
    data class App(val app: CeApp) : ScreenState()
    data class Menu(val menu: CeMenu) : ScreenState()
}

private enum class CeApp(val title: String, val icon: String) {
    FileExplorer("File Explorer", "📁"), PocketWord("Pocket Word", "📝"), PocketExcel("Pocket Excel", "📊"),
    PocketOutlook("Inbox / Contacts", "✉️"), Calendar("Calendar", "📅"), Calculator("Calculator", "🧮"),
    Internet("Internet Explorer", "🌐"), Terminal("Terminal", "⌨️"), ControlPanel("Control Panel", "⚙️"),
    TaskManager("Task Manager", "📈"), Help("Help", "❔"), Solitaire("Solitaire", "🃏"),
    Minesweeper("Minesweeper", "💣"), About("About HP 3000 XL", "ℹ️")
}

private enum class CeMenu(val title: String) { Start("Start"), Programs("Programs"), Settings("Settings"), Documents("Documents"), Favorites("Favorites") }

@Composable
private fun HP3000XLWindowsCE() {
    var state by remember { mutableStateOf<ScreenState>(ScreenState.Desktop) }
    var startOpen by remember { mutableStateOf(false) }
    var menu by remember { mutableStateOf<CeMenu?>(null) }
    var dark by remember { mutableStateOf(false) }
    var volume by remember { mutableStateOf(true) }

    BackHandler(enabled = state != ScreenState.Desktop || startOpen || menu != null) {
        state = ScreenState.Desktop; startOpen = false; menu = null
    }

    val bg = if (dark) Color(0xFF1D2633) else Color(0xFF5B8FD9)
    val fg = if (dark) Color.White else TextDark
    MaterialTheme(colorScheme = lightColorScheme(primary = Navy, background = bg, surface = Window, onSurface = TextDark)) {
        Box(Modifier.fillMaxSize().background(bg)) {
            when (val s = state) {
                ScreenState.Desktop -> Desktop(onOpen = { state = ScreenState.App(it) }, fg = fg)
                is ScreenState.App -> CeWindow(s.app, onClose = { state = ScreenState.Desktop }, dark = dark,
                    onDark = { dark = !dark }, volume = volume, onVolume = { volume = !volume })
                is ScreenState.Menu -> MenuPage(s.menu, onBack = { state = ScreenState.Desktop }, onOpen = { state = ScreenState.App(it) })
            }
            if (startOpen) StartMenu(
                onOpen = { state = ScreenState.App(it); startOpen = false },
                onMenu = { menu = it; startOpen = false },
                onClose = { startOpen = false }
            )
            menu?.let { PopupMenu(it, onOpen = { state = ScreenState.App(it); menu = null }, onClose = { menu = null }) }
            Taskbar(startOpen = startOpen, onStart = { startOpen = !startOpen; menu = null }, onHome = { state = ScreenState.Desktop; startOpen = false; menu = null }, volume = volume)
        }
    }
}

@Composable
private fun Desktop(onOpen: (CeApp) -> Unit, fg: Color) {
    Column(Modifier.fillMaxSize().padding(bottom = 42.dp)) {
        Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("HP 3000 XL", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Windows CE-style Mobile Desktop", color = Color.White, fontSize = 12.sp)
        }
        val apps = listOf(CeApp.FileExplorer, CeApp.PocketWord, CeApp.PocketExcel, CeApp.PocketOutlook, CeApp.Calendar, CeApp.Internet, CeApp.Calculator, CeApp.Solitaire, CeApp.ControlPanel, CeApp.Help)
        Column(Modifier.padding(12.dp).verticalScroll(rememberScrollState())) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DesktopTile(CeApp.FileExplorer, onOpen); DesktopTile(CeApp.PocketOutlook, onOpen); DesktopTile(CeApp.Calendar, onOpen)
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DesktopTile(CeApp.PocketWord, onOpen); DesktopTile(CeApp.PocketExcel, onOpen); DesktopTile(CeApp.Internet, onOpen)
            }
            Spacer(Modifier.height(18.dp))
            Text("Programs", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            apps.drop(3).chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) { row.forEach { DesktopTile(it, onOpen) } }
            }
        }
    }
}

@Composable
private fun DesktopTile(app: CeApp, onOpen: (CeApp) -> Unit) {
    Card(Modifier.weight(1f).height(88.dp).clickable { onOpen(app) }, colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .92f)), shape = RoundedCornerShape(4.dp)) {
        Column(Modifier.fillMaxSize().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(app.icon, fontSize = 26.sp); Text(app.title, fontSize = 11.sp, textAlign = TextAlign.Center, maxLines = 2)
        }
    }
}

@Composable
private fun Taskbar(startOpen: Boolean, onStart: () -> Unit, onHome: () -> Unit, volume: Boolean) {
    Row(Modifier.fillMaxWidth().height(42.dp).align(Alignment.BottomCenter).background(Color(0xFFE7EAF0)).border(1.dp, Border).padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = onStart, modifier = Modifier.height(34.dp), contentPadding = PaddingValues(horizontal = 10.dp), shape = RoundedCornerShape(2.dp)) { Text(if (startOpen) "START ▲" else "START", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        Spacer(Modifier.width(6.dp)); OutlinedButton(onClick = onHome, modifier = Modifier.height(34.dp), contentPadding = PaddingValues(8.dp), shape = RoundedCornerShape(2.dp)) { Text("▣", fontSize = 15.sp) }
        Spacer(Modifier.weight(1f)); Text(if (volume) "🔊" else "🔇", fontSize = 14.sp); Spacer(Modifier.width(8.dp));
        Text(SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()), fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun StartMenu(onOpen: (CeApp) -> Unit, onMenu: (CeMenu) -> Unit, onClose: () -> Unit) {
    Column(Modifier.width(310.dp).align(Alignment.BottomStart).padding(start = 4.dp, bottom = 43.dp).background(Color.White).border(1.dp, Navy).padding(5.dp)) {
        Row(Modifier.fillMaxWidth().background(Navy).padding(8.dp), verticalAlignment = Alignment.CenterVertically) { Text("HP 3000 XL", color = Color.White, fontWeight = FontWeight.Bold); Spacer(Modifier.weight(1f)); Text("CE", color = Color.White) }
        val quick = listOf(CeApp.FileExplorer, CeApp.PocketWord, CeApp.PocketOutlook, CeApp.Calendar, CeApp.Internet, CeApp.Calculator)
        quick.forEach { MenuItem("${it.icon}  ${it.title}") { onOpen(it) } }
        HorizontalDivider()
        MenuItem("▸  Programs") { onMenu(CeMenu.Programs) }; MenuItem("▸  Settings") { onMenu(CeMenu.Settings) }; MenuItem("▸  Documents") { onMenu(CeMenu.Documents) }; MenuItem("▸  Favorites") { onMenu(CeMenu.Favorites) }
        HorizontalDivider(); MenuItem("❔  Help") { onOpen(CeApp.Help) }; MenuItem("⏻  Suspend / Power") { onClose() }
    }
}

@Composable
private fun PopupMenu(menu: CeMenu, onOpen: (CeApp) -> Unit, onClose: () -> Unit) {
    val items = when (menu) {
        CeMenu.Programs -> listOf(CeApp.PocketWord, CeApp.PocketExcel, CeApp.PocketOutlook, CeApp.Calendar, CeApp.Calculator, CeApp.Internet, CeApp.Terminal, CeApp.Solitaire, CeApp.Minesweeper, CeApp.FileExplorer)
        CeMenu.Settings -> listOf(CeApp.ControlPanel, CeApp.TaskManager, CeApp.About)
        CeMenu.Documents -> listOf(CeApp.FileExplorer, CeApp.PocketWord, CeApp.PocketExcel)
        CeMenu.Favorites -> listOf(CeApp.Internet, CeApp.FileExplorer, CeApp.Calendar)
        CeMenu.Start -> emptyList()
    }
    Column(Modifier.width(300.dp).align(Alignment.BottomStart).padding(start = 300.dp, bottom = 43.dp).background(Color.White).border(1.dp, Navy).padding(5.dp)) {
        Text(menu.title, Modifier.fillMaxWidth().background(Navy).padding(8.dp), color = Color.White, fontWeight = FontWeight.Bold)
        items.forEach { MenuItem("${it.icon}  ${it.title}") { onOpen(it) } }
        MenuItem("← Back") { onClose() }
    }
}

@Composable
private fun MenuItem(label: String, onClick: () -> Unit) { Row(Modifier.fillMaxWidth().clickable { onClick() }.padding(9.dp), verticalAlignment = Alignment.CenterVertically) { Text(label, fontSize = 13.sp) } }

@Composable
private fun CeWindow(app: CeApp, onClose: () -> Unit, dark: Boolean, onDark: () -> Unit, volume: Boolean, onVolume: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(6.dp, 6.dp, 6.dp, 47.dp).background(Window).border(1.dp, Border)) {
        Row(Modifier.fillMaxWidth().background(Navy).padding(horizontal = 8.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("${app.icon}  ${app.title}", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f)); Text("□", color = Color.White); Spacer(Modifier.width(12.dp)); Text("×", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable { onClose() })
        }
        Row(Modifier.fillMaxWidth().background(Color(0xFFE8ECF3)).border(1.dp, Border).padding(2.dp)) { listOf("File", "Edit", "View", "Tools", "Help").forEach { Text(it, Modifier.padding(horizontal = 10.dp, vertical = 5.dp), fontSize = 11.sp) } }
        Box(Modifier.fillMaxSize().padding(8.dp)) { AppContent(app, dark, onDark, volume, onVolume) }
    }
}

@Composable
private fun AppContent(app: CeApp, dark: Boolean, onDark: () -> Unit, volume: Boolean, onVolume: () -> Unit) {
    when (app) {
        CeApp.FileExplorer -> FileExplorer()
        CeApp.PocketWord -> Editor("Pocket Word", "Welcome to HP 3000 XL Pocket Word.\n\nType, edit, save and organize documents on your handheld.")
        CeApp.PocketExcel -> Editor("Pocket Excel", "A1     B1     C1\n1      10     20\n2      30     40\n3      =SUM(B1:C2)")
        CeApp.PocketOutlook -> ContactsInbox()
        CeApp.Calendar -> CalendarView()
        CeApp.Calculator -> Calculator()
        CeApp.Internet -> BrowserMock()
        CeApp.Terminal -> Terminal()
        CeApp.ControlPanel -> SettingsPanel(dark, onDark, volume, onVolume)
        CeApp.TaskManager -> TaskManager()
        CeApp.Help -> HelpView()
        CeApp.Solitaire -> GameView("Solitaire", "♠  ♥  ♣  ♦")
        CeApp.Minesweeper -> GameView("Minesweeper", "▦  ▦  ▦\n▦  1  2\n▦  2  💣")
        CeApp.About -> AboutView()
    }
}

@Composable private fun FileExplorer() { val files = listOf("My Documents", "Program Files", "Windows", "Temp", "My Pictures", "My Music", "Network", "Storage Card"); Column { Text("\\", fontWeight = FontWeight.Bold); files.forEach { Row(Modifier.fillMaxWidth().clickable { }.padding(10.dp), verticalAlignment = Alignment.CenterVertically) { Text(if (it.contains(".") ) "📄" else "📁", fontSize = 20.sp); Spacer(Modifier.width(10.dp)); Text(it) } } } }

@Composable private fun Editor(title: String, initial: String) { var text by remember { mutableStateOf(initial) }; Column { Row(Modifier.fillMaxWidth().padding(bottom = 6.dp)) { Button(onClick = { }) { Text("New") }; Spacer(Modifier.width(5.dp)); Button(onClick = { }) { Text("Save") }; Spacer(Modifier.width(5.dp)); Button(onClick = { text = "" }) { Text("Clear") } }; BasicTextField(value = text, onValueChange = { text = it }, modifier = Modifier.fillMaxSize().background(Color.White).border(1.dp, Border).padding(10.dp), textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 14.sp)) } }

@Composable private fun ContactsInbox() { Column(Modifier.verticalScroll(rememberScrollState())) { Text("Inbox", fontWeight = FontWeight.Bold, fontSize = 18.sp); listOf("Welcome to HP 3000 XL", "System update available", "Meeting reminder").forEach { MenuItem("✉  $it") {} }; HorizontalDivider(); Text("Contacts", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(top = 12.dp)); listOf("Home", "Work", "Emergency").forEach { MenuItem("👤  $it") {} } } }

@Composable private fun CalendarView() { Column(Modifier.verticalScroll(rememberScrollState())) { Text("October 2026", fontWeight = FontWeight.Bold, fontSize = 20.sp); Text("Mo Tu We Th Fr Sa Su", modifier = Modifier.padding(top = 12.dp)); Text("     1  2  3  4\n 5  6  7  8  9 10 11\n12 13 14 15 16 17 18\n19 20 21 22 23 24 25\n26 27 28 29 30 31", fontFamily = FontFamily.Monospace, fontSize = 18.sp); HorizontalDivider(Modifier.padding(vertical = 12.dp)); Text("Appointments", fontWeight = FontWeight.Bold); MenuItem("09:00  Morning briefing") {}; MenuItem("13:00  Lunch") {} } }

@Composable private fun Calculator() { var value by remember { mutableStateOf("") }; val keys = listOf("7","8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"); Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(value.ifEmpty { "0" }, Modifier.fillMaxWidth().background(Color.Black).padding(12.dp), color = Color.Green, textAlign = TextAlign.End, fontFamily = FontFamily.Monospace, fontSize = 24.sp); keys.chunked(4).forEach { row -> Row { row.forEach { k -> Button(onClick = { value = if (k == "=") evaluate(value) else value + k }, modifier = Modifier.padding(2.dp).size(70.dp), shape = RoundedCornerShape(2.dp)) { Text(k) } } }; Spacer(Modifier.height(2.dp)) }; Button(onClick = { value = "" }) { Text("Clear") } } }
private fun evaluate(v: String): String = try { val p = v.split("+"); if (p.size == 2) (p[0].toDouble() + p[1].toDouble()).toString() else v } catch (_: Exception) { "Error" }

@Composable private fun BrowserMock() { Column { Row(Modifier.fillMaxWidth().background(Color.White).border(1.dp, Border).padding(7.dp)) { Text("http://", color = Color.Gray); Text("hp3000xl.local", modifier = Modifier.weight(1f)); Text("↻") }; Spacer(Modifier.height(10.dp)); Text("HP 3000 XL Portal", fontWeight = FontWeight.Bold, fontSize = 22.sp); Text("Welcome to the built-in mobile web workspace.\n\nFavorites • History • Downloads • Connections", modifier = Modifier.padding(top = 12.dp)); Spacer(Modifier.height(15.dp)); Button(onClick = {}) { Text("Home") } } }

@Composable private fun Terminal() { var input by remember { mutableStateOf("") }; var output by remember { mutableStateOf("HP 3000 XL CE Terminal\nType HELP for commands.\n> ") }; Column { Text(output, Modifier.weight(1f).fillMaxWidth().background(Color.Black).padding(10.dp), color = Color(0xFF7CFF7C), fontFamily = FontFamily.Monospace); Row(Modifier.padding(top = 6.dp)) { BasicTextField(input, { input = it }, Modifier.weight(1f).background(Color.Black).padding(8.dp), textStyle = LocalTextStyle.current.copy(color = Color.White, fontFamily = FontFamily.Monospace)); Button(onClick = { output += "\n> $input\n${terminalCommand(input)}"; input = "" }) { Text("Run") } } } }
private fun terminalCommand(c: String) = when (c.trim().uppercase()) { "HELP" -> "DIR  CD  DATE  TIME  VER  MEM  CLS  ABOUT"; "DIR" -> "My Documents  Program Files  Windows  Storage Card"; "VER" -> "HP 3000 XL Mobile CE Shell 2.0"; "MEM" -> "System memory available: Android managed"; "DATE" -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()); "TIME" -> SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()); "CLS" -> ""; "ABOUT" -> "Retro handheld desktop simulator"; "" -> ""; else -> "Command not found. Type HELP." }

@Composable private fun SettingsPanel(dark: Boolean, onDark: () -> Unit, volume: Boolean, onVolume: () -> Unit) { Column(Modifier.verticalScroll(rememberScrollState())) { Text("Control Panel", fontWeight = FontWeight.Bold, fontSize = 20.sp); Setting("Display", "Theme, brightness, orientation"); Setting("Power", "Battery, suspend, auto-off"); Setting("Memory", "Storage and memory information"); Setting("Network", "Wi-Fi, Bluetooth, proxy"); Setting("Regional Settings", "Language, date and time"); Setting("Owner Information", "Name, contact and device ID"); Setting("Security", "PIN, permissions and privacy"); Setting("Stylus / Touch", "Calibration and pointer speed"); Row(Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) { Text("Dark theme", Modifier.weight(1f)); Switch(dark, { onDark() }) }; Row(Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) { Text("Sound", Modifier.weight(1f)); Switch(volume, { onVolume() }) } } }
@Composable private fun Setting(a: String, b: String) { Row(Modifier.fillMaxWidth().clickable {}.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Text("⚙", fontSize = 20.sp); Spacer(Modifier.width(12.dp)); Column { Text(a, fontWeight = FontWeight.Bold); Text(b, fontSize = 11.sp, color = Color.Gray) } } }

@Composable private fun TaskManager() { Column { Text("Running Programs", fontWeight = FontWeight.Bold, fontSize = 20.sp); listOf("Explorer.exe", "Shell.exe", "DeviceManager", "InputManager", "Clock").forEach { Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text(it); Text("Running", color = Green) } }; HorizontalDivider(); Text("CPU: Android managed\nMemory: Android managed\nBattery: system managed", modifier = Modifier.padding(12.dp)) } }
@Composable private fun HelpView() { Column(Modifier.verticalScroll(rememberScrollState())) { Text("HP 3000 XL Help", fontWeight = FontWeight.Bold, fontSize = 20.sp); listOf("Getting Started", "Using the Start menu", "Managing files", "Pocket Word", "Pocket Excel", "Internet and networking", "Connecting peripherals", "Power management", "Troubleshooting").forEach { MenuItem("❔  $it") {} } } }
@Composable private fun GameView(title: String, board: String) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(title, fontWeight = FontWeight.Bold, fontSize = 22.sp); Spacer(Modifier.height(20.dp)); Text(board, fontSize = 30.sp, fontFamily = FontFamily.Monospace, textAlign = TextAlign.Center); Spacer(Modifier.height(20.dp)); Button(onClick = {}) { Text("New Game") } } }
@Composable private fun AboutView() { Column(Modifier.verticalScroll(rememberScrollState())) { Text("HP 3000 XL", fontWeight = FontWeight.Bold, fontSize = 24.sp); Text("Color Mobile Edition 2.0", fontSize = 17.sp); Spacer(Modifier.height(12.dp)); Text("A modern Android handheld desktop inspired by the form, interaction model and productivity workflow of late-1990s/early-2000s Windows CE PDAs.\n\nPlatform: Android 10+\nTarget SDK: 35\nArchitecture: Kotlin + Jetpack Compose\n\nThis implementation is an independent compatibility-style recreation and does not include proprietary Microsoft or HP firmware, ROMs, binaries or trademarks.") } }

@Composable private fun MenuPage(menu: CeMenu, onBack: () -> Unit, onOpen: (CeApp) -> Unit) { Column(Modifier.fillMaxSize().padding(10.dp, 10.dp, 10.dp, 50.dp)) { Text(menu.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp); Spacer(Modifier.height(10.dp)); val apps = when (menu) { CeMenu.Programs -> CeApp.entries.toList(); CeMenu.Settings -> listOf(CeApp.ControlPanel, CeApp.TaskManager, CeApp.About); CeMenu.Documents -> listOf(CeApp.FileExplorer, CeApp.PocketWord, CeApp.PocketExcel); CeMenu.Favorites -> listOf(CeApp.Internet, CeApp.Calendar, CeApp.FileExplorer); CeMenu.Start -> emptyList() }; LazyColumn { items(apps) { MenuItem("${it.icon}  ${it.title}") { onOpen(it) } } }; Button(onClick = onBack) { Text("Back") } } }
