# Redesigned HP 3000 XL 2.0 — Color Mobile Edition

A full-color Android handheld desktop recreation inspired by the HP 300LX/Windows CE era. The project is being expanded from a visual shell into a modular handheld application platform with persistent PIM data, an application registry and Android-backed system services.

> This is an independent Android recreation. It does **not** contain Microsoft Windows CE or HP proprietary firmware, ROM images, binaries, source code, or copyrighted system assets.

## Android compatibility

- Android 10 (API 29) and newer
- Minimum SDK: 26
- Target SDK: 35
- Kotlin + Jetpack Compose
- Phone, tablet and handheld-PC layouts
- Physical keyboard/touch friendly controls

## Platform architecture

```text
Android 10+
    |
    +-- HP3000XL Runtime
          +-- CE-style Shell / Desktop
          +-- Application Manager
          +-- SQLite PIM / Settings Database
          +-- System Services
          |     +-- Power
          |     +-- Storage
          |     +-- Clipboard
          |     +-- Notifications
          |     +-- Device/System Information
          +-- Application APIs
                +-- File I/O
                +-- Windows / Menus / Dialogs
                +-- Documents
                +-- Networking
                +-- PIM
                +-- Scripting (future)
```

### New platform APIs

- `CeDatabase` — SQLite persistence for settings, contacts, calendar events, notes, favorites and installed applications.
- `CeApplicationManager` — built-in application registry with IDs, versions, categories and enable/disable state.
- `CeSystemServices` — device model, Android version, SDK, battery and storage information.
- `CeService` — common lifecycle contract for future clipboard, notification, power, network and device services.

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

### Productivity / PIM

- Pocket Word-style document editor
- Pocket Excel-style spreadsheet workspace
- Pocket Outlook-style Inbox
- Contacts
- Calendar
- Tasks/Notes database foundation
- File Explorer
- Calculator

### Internet and communications

- Internet-style browser workspace
- Network/settings categories
- Terminal with an allow-listed simulated command interpreter
- Application/service APIs prepared for real Android networking

### System tools

- Control Panel
- Task Manager
- Help system
- About/System Information
- Power/display/security/network settings model
- Battery and storage information API
- Persistent application registry

### Games

- Solitaire-style game shell
- Minesweeper-style game shell

## Menu hierarchy

```text
START
├── Programs
│   ├── Pocket Word
│   ├── Pocket Excel
│   ├── Pocket Outlook
│   ├── Contacts
│   ├── Calendar
│   ├── Calculator
│   ├── Internet
│   ├── Terminal
│   ├── File Explorer
│   ├── Solitaire
│   └── Minesweeper
├── Settings
│   ├── Control Panel
│   ├── Task Manager
│   ├── Display
│   ├── Power
│   ├── Memory / Storage
│   ├── Network
│   ├── Regional Settings
│   ├── Owner Information
│   ├── Security
│   ├── Stylus / Input
│   └── Sound
├── Documents
│   ├── File Explorer
│   ├── Pocket Word
│   └── Pocket Excel
├── Favorites
│   ├── Internet
│   ├── Calendar
│   └── File Explorer
├── Help
└── Suspend / Power
```

## Build

```bash
./gradlew assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

GitHub Actions builds a debug APK artifact on pushes and pull requests.

## Roadmap

1. Connect the visible PIM applications to `CeDatabase`.
2. Replace the simulated File Explorer with Android Storage Access Framework.
3. Add hardware keyboard, stylus and handheld shortcut mapping.
4. Add Bluetooth, USB and Wi-Fi device-management screens.
5. Add Android notification integration and a real process/task model.
6. Add a documented `.hpapp` package format for this recreation.
7. Add a sandboxed Lua/BASIC scripting runtime.
8. Add Pocket Office import/export formats.
9. Add optional VM support only for software the user is legally entitled to run.
10. Produce signed release APK/AAB builds and an update channel.

## License

MIT for the original project code. See `LICENSE`.


## Live browser preview

The interactive GitHub Pages preview is available at:

**https://arkansasio.github.io/Redesigned-Hp-3000-XL-2.0/**

It includes a responsive handheld desktop shell, Start menu, app windows, notes, an editable spreadsheet with CSV export, calculator, local calendar reminders, local contacts, display settings and a simulated terminal. Browser data stays in the current browser profile; this is not a backend sync service or Android emulator.

- [Browser preview documentation](docs/WEB_PREVIEW.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Roadmap](docs/ROADMAP.md)
- [Testing guide](docs/TESTING.md)
- [Security notes](docs/SECURITY.md)
- [GitHub Pages deployment workflow](.github/workflows/pages.yml)
