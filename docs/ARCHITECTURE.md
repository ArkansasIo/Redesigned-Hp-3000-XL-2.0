# Architecture

## Scope

HP3000XL is an Android-hosted retro handheld desktop simulator. It is not a bootable operating system or a Windows CE binary-compatible environment.

## Layers

- **Android host:** lifecycle, filesystem sandbox and platform compatibility.
- **Desktop shell:** status bar, launcher tiles, Start menu and task bar.
- **Built-in apps:** notes, calculator, simulated terminal, file-list mockup, calendar, contacts and settings.
- **Persistence:** Android SharedPreferences for notes and contacts in the initial version.
- **Build:** Gradle Kotlin DSL and GitHub Actions debug APK artifact.

## Security boundary

The terminal implements an allow-listed simulated command set. It must not execute arbitrary shell commands. File access should use Android's Storage Access Framework when real file browsing is implemented.

## Planned engineering steps

1. Replace the file-list mockup with Storage Access Framework integration.
2. Move notes and contacts to Room/SQLite with import/export.
3. Implement a tested expression parser for the calculator.
4. Embed Lua/BASIC interpreters in sandboxed runtimes.
5. Add physical keyboard shortcuts and tablet/handheld landscape layouts.
6. Add CI tests and a signed release workflow with repository secrets.
