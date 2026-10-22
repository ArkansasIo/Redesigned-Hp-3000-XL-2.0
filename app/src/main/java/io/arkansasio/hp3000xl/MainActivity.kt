package io.arkansasio.hp3000xl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Ink = Color(0xFF172A25)
private val Screen = Color(0xFFC7D1B4)
private val Panel = Color(0xFFE1E8D1)
private val Line = Color(0xFF74826A)
private val Accent = Color(0xFF3C5747)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HP3000XLApp() }
    }
}

private enum class DeskApp(val title: String, val glyph: String) {
    NOTES("Pocket Notes", "✎"), CALCULATOR("Calculator", "±"), TERMINAL("Terminal", ">_"),
    FILES("File Manager", "▤"), CALENDAR("Calendar", "▦"), CONTACTS("Contacts", "♙"),
    SETTINGS("Settings", "⚙"), ABOUT("System Info", "ⓘ")
}

@Composable
private fun HP3000XLApp() {
    var current by remember { mutableStateOf<DeskApp?>(null) }
    var showMenu by remember { mutableStateOf(false) }
    BackHandler(enabled = current != null) { current = null }
    MaterialTheme(colorScheme = lightColorScheme(primary = Accent, onPrimary = Screen, background = Screen,
        onBackground = Ink, surface = Panel, onSurface = Ink)) {
        Column(Modifier.fillMaxSize().background(Screen).padding(8.dp).border(2.dp, Ink).padding(6.dp)) {
            TopBar()
            HorizontalDivider(color = Ink, thickness = 2.dp)
            if (current == null) Desktop(onOpen = { current = it }, showMenu = showMenu, onMenu = { showMenu = !showMenu })
            else AppPanel(app = current!!, onBack = { current = null })
            Spacer(Modifier.height(4.dp))
            HorizontalDivider(color = Ink, thickness = 2.dp)
            BottomBar(onStart = { showMenu = !showMenu }, current = current)
        }
    }
}

@Composable
private fun TopBar() {
    var time by remember { mutableStateOf(now()) }
    LaunchedEffect(Unit) { while (true) { kotlinx.coroutines.delay(30_000); time = now() } }
    Row(Modifier.fillMaxWidth().background(Ink).padding(horizontal = 8.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("HP", color = Screen, fontWeight = FontWeight.Black, fontSize = 17.sp)
        Spacer(Modifier.width(8.dp))
        Text("3000 XL  •  HANDHELD OS", color = Screen, fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        Text(time, color = Screen, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
    }
}

@Composable
private fun Desktop(onOpen: (DeskApp) -> Unit, showMenu: Boolean, onMenu: () -> Unit) {
    Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(8.dp)) {
        Text("Welcome back.", color = Ink, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Text("Personal Information Manager", color = Accent, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        Spacer(Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Status("MEMORY", "READY"); Status("STORAGE", "ONLINE"); Status("POWER", "OK")
        }
        Spacer(Modifier.height(12.dp))
        Text("APPLICATIONS", color = Ink, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
        Spacer(Modifier.height(6.dp))
        DeskApp.entries.chunked(2).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { item -> AppTile(item, Modifier.weight(1f)) { onOpen(item) } }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
        }
        if (showMenu) {
            Column(Modifier.fillMaxWidth().border(2.dp, Ink).background(Panel).padding(8.dp)) {
                Text("START MENU", fontWeight = FontWeight.Black)
                DeskApp.entries.forEach { item ->
                    Text("${item.glyph}   ${item.title}", Modifier.fillMaxWidth().clickable { onOpen(item) }.padding(vertical = 7.dp), color = Ink)
                }
                Text("Close menu", Modifier.clickable { onMenu() }.padding(vertical = 5.dp), color = Accent, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("HP3000XL OS 1.0.0  |  CE-INSPIRED SHELL", color = Accent, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
    }
}

@Composable
private fun Status(label: String, value: String) {
    Column(Modifier.border(1.dp, Line).background(Panel).padding(6.dp)) {
        Text(label, fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Accent)
        Text(value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Ink)
    }
}

@Composable
private fun AppTile(app: DeskApp, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier.heightIn(min = 78.dp).border(1.dp, Ink).background(Panel).clickable(onClick = onClick).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(app.glyph, fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Ink)
        Spacer(Modifier.height(3.dp))
        Text(app.title, fontSize = 11.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Ink)
    }
}

@Composable
private fun BottomBar(onStart: () -> Unit, current: DeskApp?) {
    Row(Modifier.fillMaxWidth().padding(top = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.background(Ink).clickable(onClick = onStart).padding(horizontal = 12.dp, vertical = 7.dp)) {
            Text("START", color = Screen, fontWeight = FontWeight.Black, fontSize = 12.sp)
        }
        Spacer(Modifier.width(8.dp))
        Text(current?.title ?: "Desktop", color = Ink, fontFamily = FontFamily.Monospace, fontSize = 11.sp, modifier = Modifier.weight(1f))
        Text("BAT 100%  ▰  I/O", color = Ink, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
    }
}

@Composable
private fun AppPanel(app: DeskApp, onBack: () -> Unit) {
    Column(Modifier.weight(1f).fillMaxWidth().padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(app.glyph, fontSize = 24.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.width(8.dp))
            Text(app.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Button(onClick = onBack, contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)) { Text("Close") }
        }
        HorizontalDivider(color = Ink, thickness = 1.dp); Spacer(Modifier.height(8.dp))
        when (app) {
            DeskApp.NOTES -> NotesApp(); DeskApp.CALCULATOR -> CalculatorApp(); DeskApp.TERMINAL -> TerminalApp()
            DeskApp.FILES -> FilesApp(); DeskApp.CALENDAR -> CalendarApp(); DeskApp.CONTACTS -> ContactsApp()
            DeskApp.SETTINGS -> SettingsApp(); DeskApp.ABOUT -> AboutApp()
        }
    }
}

@Composable
private fun NotesApp() {
    val context = LocalContext.current
    var text by remember { mutableStateOf(context.getSharedPreferences("hp3000xl", 0).getString("notes", "") ?: "") }
    Column(Modifier.fillMaxSize()) {
        Text("PERSONAL NOTEPAD  •  AUTO-SAVE", fontSize = 11.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(6.dp))
        BasicTextField(value = text, onValueChange = { text = it; context.getSharedPreferences("hp3000xl", 0).edit().putString("notes", it).apply() },
            modifier = Modifier.fillMaxWidth().weight(1f).border(1.dp, Line).background(Color.White).padding(8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Ink, fontSize = 15.sp, fontFamily = FontFamily.Monospace),
            decorationBox = { inner -> Box { if (text.isEmpty()) Text("Type your notes here...", color = Line); inner() } })
    }
}

@Composable
private fun CalculatorApp() {
    var expression by remember { mutableStateOf("") }; var result by remember { mutableStateOf("0") }
    val keys = listOf("7","8","9","÷","4","5","6","×","1","2","3","−","C","0",".","+","=")
    Column(Modifier.fillMaxSize()) {
        Text("DISPLAY", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text(result, Modifier.fillMaxWidth().background(Ink).padding(12.dp), color = Screen, fontFamily = FontFamily.Monospace, fontSize = 25.sp, textAlign = TextAlign.End)
        Spacer(Modifier.height(8.dp))
        keys.chunked(4).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                row.forEach { key -> Button(onClick = {
                    when (key) { "C" -> { expression = ""; result = "0" }; "=" -> { result = safeCalculate(expression) ?: "ERROR"; expression = result }; else -> { expression += key; result = expression } }
                }, modifier = Modifier.weight(1f), contentPadding = PaddingValues(vertical = 8.dp)) { Text(key, fontSize = 16.sp) } }
                repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

private fun safeCalculate(source: String): String? {
    val s = source.replace("×", "*").replace("÷", "/").replace("−", "-").replace(" ", "")
    if (s.isBlank() || !s.matches(Regex("[0-9.+*/-]+"))) return null
    return try {
        val tokens = Regex("(?<=[+*/-])|(?=[+*/-])").split(s).filter { it.isNotEmpty() }.toMutableList()
        if (tokens.isEmpty()) return null
        fun reduce(op: String) { var i = 0; while (i < tokens.size) {
            if (tokens[i] == op && i > 0 && i + 1 < tokens.size) {
                val a = tokens[i-1].toDouble(); val b = tokens[i+1].toDouble(); val v = if (op == "*") a*b else a/b
                if (!v.isFinite()) throw ArithmeticException()
                tokens[i-1] = v.toString(); tokens.removeAt(i); tokens.removeAt(i); i = 0
            } else i++
        } }
        reduce("*"); reduce("/"); var value = tokens[0].toDouble(); var i = 1
        while (i + 1 < tokens.size) { val n = tokens[i+1].toDouble(); value = when (tokens[i]) { "+" -> value+n; "-" -> value-n; else -> return null }; i += 2 }
        if (value.isFinite()) value.toString().removeSuffix(".0") else null
    } catch (_: Exception) { null }
}

@Composable
private fun TerminalApp() {
    var command by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("HP3000XL OS 1.0.0\nType 'help' to list simulated commands.\n") }
    Column(Modifier.fillMaxSize()) {
        Text(output, Modifier.weight(1f).fillMaxWidth().background(Ink).padding(8.dp).verticalScroll(rememberScrollState()), color = Screen, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        Row(Modifier.fillMaxWidth().padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(">", fontWeight = FontWeight.Black); Spacer(Modifier.width(6.dp))
            BasicTextField(command, { command = it }, Modifier.weight(1f).border(1.dp, Line).background(Panel).padding(7.dp), textStyle = androidx.compose.ui.text.TextStyle(color = Ink, fontFamily = FontFamily.Monospace))
            Button(onClick = { output += "\n> $command\n" + terminalResponse(command) + "\n"; command = "" }) { Text("Run") }
        }
    }
}

private fun terminalResponse(input: String): String = when (input.trim().lowercase()) {
    "help" -> "help, info, date, time, uptime, memory, dir, ls, clear, echo TEXT, version"
    "info" -> "HP 3000 XL handheld shell\nAndroid host / Compose UI"
    "date" -> SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    "time" -> SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
    "uptime" -> "Session active (simulated)"
    "memory" -> "Memory details are managed by Android."
    "dir", "ls" -> "Documents/\nDownloads/\nPictures/\nPrograms/"
    "version" -> "HP3000XL OS 1.0.0"
    "clear" -> ""
    else -> if (input.trim().startsWith("echo ", true)) input.trim().drop(5) else "Unknown command. Type 'help'."
}

@Composable
private fun FilesApp() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text("USER STORAGE", fontWeight = FontWeight.Bold)
        listOf("Documents", "Downloads", "Pictures", "Programs", "README.TXT").forEach {
            Row(Modifier.fillMaxWidth().border(1.dp, Line).padding(12.dp)) { Text(if (it.endsWith(".TXT")) "▤  $it" else "▣  $it", color = Ink, fontFamily = FontFamily.Monospace) }
            Spacer(Modifier.height(4.dp))
        }
        Text("Starter view: real file browsing will be added in a later version.", fontSize = 11.sp, color = Accent)
    }
}

@Composable
private fun CalendarApp() {
    val today = remember { SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(Date()) }
    Column(Modifier.fillMaxSize().padding(8.dp)) { Text("TODAY", fontSize = 11.sp, color = Accent, fontWeight = FontWeight.Bold); Text(today, fontSize = 19.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(10.dp)); Text("No appointments scheduled.", fontFamily = FontFamily.Monospace) }
}

@Composable
private fun ContactsApp() {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }; var detail by remember { mutableStateOf("") }
    var entries by remember { mutableStateOf(context.getSharedPreferences("hp3000xl", 0).getString("contacts", "") ?: "") }
    Column(Modifier.fillMaxSize()) {
        Text("CONTACT NAME"); BasicTextField(name, { name = it }, Modifier.fillMaxWidth().border(1.dp, Line).padding(8.dp)); Spacer(Modifier.height(6.dp))
        Text("PHONE / EMAIL"); BasicTextField(detail, { detail = it }, Modifier.fillMaxWidth().border(1.dp, Line).padding(8.dp))
        Button(onClick = { if (name.isNotBlank()) { entries = entries + (if (entries.isBlank()) "" else "\n") + "$name — $detail"; context.getSharedPreferences("hp3000xl", 0).edit().putString("contacts", entries).apply(); name = ""; detail = "" } }) { Text("Save Contact") }
        Text(entries, Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()), fontFamily = FontFamily.Monospace, color = Ink)
    }
}

@Composable
private fun SettingsApp() {
    var highContrast by remember { mutableStateOf(false) }; var showHints by remember { mutableStateOf(true) }
    Column(Modifier.fillMaxSize()) {
        Text("SYSTEM SETTINGS", fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) { Text("High-contrast mode", Modifier.weight(1f)); Switch(checked = highContrast, onCheckedChange = { highContrast = it }) }
        Row(verticalAlignment = Alignment.CenterVertically) { Text("Show desktop labels", Modifier.weight(1f)); Switch(checked = showHints, onCheckedChange = { showHints = it }) }
        Text("Display: automatic\nShell: HP3000XL Classic\nBuild: 1.0.0\nHost OS: Android", fontFamily = FontFamily.Monospace, fontSize = 12.sp, modifier = Modifier.padding(top = 12.dp))
        Text("Some settings are demonstration toggles in this starter release.", color = Accent, fontSize = 11.sp)
    }
}

@Composable
private fun AboutApp() {
    Column(Modifier.fillMaxSize().padding(8.dp)) {
        Text("HP 3000 XL", fontSize = 26.sp, fontWeight = FontWeight.Black); Text("HANDHELD OPERATING ENVIRONMENT", fontFamily = FontFamily.Monospace, fontSize = 11.sp); Spacer(Modifier.height(12.dp))
        Text("Version: 1.0.0"); Text("Platform: Android"); Text("Interface: HP 300LX-inspired"); Text("Runtime: Kotlin / Jetpack Compose"); Spacer(Modifier.height(12.dp))
        Text("This application simulates a handheld desktop. It is not a standalone kernel and does not contain Windows CE.", fontSize = 12.sp)
    }
}

private fun now(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
