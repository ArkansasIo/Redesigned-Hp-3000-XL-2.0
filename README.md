# Redesigned HP 3000 XL 2.0 — Color Mobile Edition

A full-color Android handheld desktop recreation inspired by the HP 300LX/Windows CE era. The application provides a Windows CE-style shell, Start menu, taskbar, desktop icons, window chrome, productivity applications, system utilities and handheld-oriented menus.

> This is an independent Android recreation. It does **not** contain Microsoft Windows CE or HP proprietary firmware, ROM images, binaries, source code, or copyrighted system assets.

## Android compatibility

- Android 10 (API 29) and newer
- Minimum SDK: 26
- Target SDK: 35
- Kotlin + Jetpack Compose
- Phone, tablet and handheld-PC layouts
- Physical keyboard/touch friendly controls

## Desktop and shell

- Full-color desktop
- Windows CE-style Start button and taskbar
- Start menu with Programs, Settings, Documents and Favorites submenus
- Desktop application shortcuts
- Window title bars, close controls and menu-bar model
- Back-button navigation
- Clock, sound indicator and desktop/home control
- Light/dark display theme

## Built-in applications

### Productivity

- Pocket Word-style document editor
- Pocket Excel-style spreadsheet workspace
- Inbox and Contacts
- Calendar
- File Explorer

### Internet and communications

- Internet Explorer-style browser workspace
- Network/settings categories
- Terminal with an allow-listed simulated command interpreter

### System tools

- Control Panel
- Task Manager
- Help system
- About/System Information
- Calculator
- Power/display/security/network settings model

### Games

- Solitaire-style game shell
- Minesweeper-style game shell

## Menu hierarchy

```text
START
├── Programs
│   ├── Pocket Word
│   ├── Pocket Excel
│   ├── Inbox / Contacts
│   ├── Calendar
│   ├── Calculator
│   ├── Internet Explorer
│   ├── Terminal
│   ├── File Explorer
│   ├── Solitaire
│   └── Minesweeper
├── Settings
│   ├── Control Panel
│   ├── Task Manager
│   └── About
├── Documents
│   ├── File Explorer
│   ├── Pocket Word
│   └── Pocket Excel
├── Favorites
│   ├── Internet Explorer
│   ├── Calendar
│   └── File Explorer
├── Help
└── Suspend / Power
```

## Architecture

The app is intentionally an Android-hosted compatibility shell rather than a replacement Android kernel. Android provides lifecycle, graphics, input, storage and device services while the application supplies the handheld desktop experience.

See [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md).

## Build

```bash
./gradlew assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

GitHub Actions also builds a debug APK artifact on pushes and pull requests.

## Roadmap toward a deeper handheld OS recreation

1. Persistent SQLite/Room PIM database.
2. Real Android Storage Access Framework file browser.
3. Hardware keyboard and stylus shortcut mapping.
4. Bluetooth, USB and Wi-Fi device-management UI.
5. Notification center and background-task manager.
6. CAB-like application package format for this recreation.
7. Sandboxed Lua/BASIC scripting runtime.
8. Pocket Office document formats and import/export.
9. Optional virtual machine layer for legally obtained legacy software.
10. Signed release APK/AAB and release update channel.

## License

MIT for the original project code. See `LICENSE`.
