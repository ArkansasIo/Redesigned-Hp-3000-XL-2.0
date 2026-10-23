# OS and Application File Inventory

This inventory defines the software layers for the Color Mobile Edition.

## OS runtime

- `os/OsVersion.kt` — product and API identity.
- `os/OsState.kt` — runtime power, network, audio and session state.
- `os/OsEvent.kt` — typed operating-system events.
- `os/OsEventBus.kt` — service/application event dispatch.
- `os/OsServices.kt` — service contract and registry.
- `os/OsKernel.kt` — Android-hosted kernel facade and boot lifecycle.
- `os/PowerManager.kt` — power/sleep state abstraction.
- `os/NetworkManager.kt` — Wi-Fi/Bluetooth/network state abstraction.
- `os/StorageManager.kt` — storage accounting abstraction.
- `os/AudioManager.kt` — original system-tone/audio abstraction.
- `os/ClipboardManager.kt` — OS clipboard abstraction.
- `os/TaskManager.kt` — running application registry.

## Applications

- `apps/OsApp.kt` — application descriptor.
- `apps/OsAppCatalog.kt` — built-in application catalog.
- `apps/FileExplorerApp.kt` — file navigation model.
- `apps/OfficeApps.kt` — Pocket Word and Pocket Excel cores.
- `apps/PimApps.kt` — Contacts and Calendar cores.
- `apps/CalculatorApp.kt` — calculator operations.
- `apps/InternetApp.kt` — browser/navigation state.
- `apps/TerminalApp.kt` — safe built-in terminal commands.
- `apps/DevelopmentApps.kt` — code editor and language catalog.
- `apps/SystemApps.kt` — Control Panel, Task Manager, Update Manager and Help.
- `apps/Games.kt` — Solitaire and Minesweeper cores.

## Package assets

- `assets/os/os-manifest.json` — OS manifest.
- `assets/apps/builtin/*.hpapp.json` — application package manifests.

## Compatibility boundary

The project is an independent software recreation. The OS layer hosts on Android/Linux and does not distribute Windows CE binaries, HP proprietary ROMs, or extracted proprietary firmware.
