# HP 3000 XL Color Mobile Edition Architecture

## Design target

The project recreates the interaction model of a classic Windows CE handheld on modern Android 10+ without copying proprietary firmware or Microsoft/HP binaries. It is an application-level handheld shell, not a replacement Android kernel.

## Shell

```text
Android Activity
└── Compose desktop shell
    ├── Desktop
    ├── Taskbar
    ├── Start menu
    │   ├── Programs
    │   ├── Settings
    │   ├── Documents
    │   └── Favorites
    ├── Window manager model
    └── Application panels
```

## Application layer

- File Explorer
- Pocket Word
- Pocket Excel
- Inbox / Contacts
- Calendar
- Calculator
- Internet Explorer-style browser workspace
- Simulated terminal
- Control Panel
- Task Manager
- Help
- Solitaire and Minesweeper shells
- System information

## System-settings model

The Control Panel exposes the major handheld configuration domains:

- Display/theme
- Power/battery/auto-off
- Memory/storage
- Network/Wi-Fi/Bluetooth
- Regional settings
- Owner information
- Security/PIN/permissions
- Stylus/touch calibration
- Sound

## Android 10+ boundary

Android remains responsible for:

- Process isolation
- Permissions
- Networking
- Bluetooth and USB APIs
- Battery state
- Display and orientation
- Filesystem access
- Notifications

The app does not request root access or attempt to replace Android system services.

## Data persistence roadmap

The current shell keeps UI state in Compose. Future persistence should use Room/SQLite for:

- Contacts
- Calendar appointments
- Notes/documents
- Browser favorites/history
- Device settings
- Application metadata

Files should use Android Storage Access Framework rather than unrestricted filesystem paths.

## Legacy software compatibility

A future compatibility layer can expose a sandboxed virtual CPU/runtime for software the user is legally entitled to run. Proprietary Windows CE ROMs and copyrighted system binaries are intentionally not included.


## Browser preview architecture (added for GitHub Pages)

The root `index.html` also contains a standalone, responsive browser desktop preview. Its UI apps include a virtual File Explorer, notes editor, editable sample spreadsheet with CSV export, calculator, calendar reminders, contacts, settings and an allow-listed simulated terminal. The preview uses localStorage for demo preferences and personal demo data. It is not the native Android runtime and has no backend or synchronization.

See [WEB_PREVIEW.md](WEB_PREVIEW.md) for the exact feature boundaries and manual smoke tests.
