# Complete File Catalog

The repository is organized as an Android-hosted handheld operating environment.

## Application runtime

- `os/` — kernel facade, state, events, services, boot, processes, permissions and application hosting.
- `platform/` — package, storage, settings and input abstractions.
- `services/` — Android-facing device, power, network, storage, clipboard, notifications and filesystem interfaces.
- `network/` — Wi-Fi and Bluetooth facades.
- `storage/` — preference and document abstractions.
- `apps/` — application contracts and built-in application catalogs.
- `apps/office/` — Pocket Word and Pocket Excel engines.
- `apps/pim/` — contacts, calendar and mail data models.
- `apps/calculator/` — expression calculator engine.
- `apps/games/` — Solitaire and Minesweeper engines.
- `apps/development/` — development language/tool catalog.
- `development/` — code editor model and workspace support.
- `languages/` — language definitions and code workspace.
- `terminal/` — terminal session/profile implementation.
- `core/` — central runtime and event abstractions.
- `api/` — application-facing API surface.
- `input/` — Android input translation.

## Firmware model

`firmware/` contains original architecture documentation and hardware abstraction models. It is not a dump of HP firmware and does not contain Windows CE binaries.

## User interface

`MainActivity.kt` hosts the Compose desktop, taskbar, Start menu, window chrome and application views. GUI reference assets are maintained under `GUI/PNG/` where available.

## Documentation

`docs/` contains architecture, API, application, database, build, update, package, security, testing, release, roadmap, OS layer, input, menu, folder and version documentation.

## Build

Gradle builds the Android application with Kotlin/Compose. GitHub Actions builds debug and pre-alpha release APKs and packages checksums and metadata.

## Compatibility

The primary target is Android 10/API 29 and newer, with Huawei P20/P20 Pro-class hardware as a compatibility reference. The actual Android/Linux kernel remains the host kernel.
