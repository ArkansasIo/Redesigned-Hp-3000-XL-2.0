# Redesigned HP 3000 XL 2.0

An Android application that recreates the feel of the classic HP 300LX handheld as a modern, retro-inspired handheld OS shell.

> This is an Android launcher/simulator app, not a replacement kernel or a port of Microsoft Windows CE.

## Features

- HP 300LX-inspired monochrome/industrial handheld desktop
- Launchable Notes, Calculator, Terminal, Files, Calendar and Settings panels
- Responsive layout for phones, tablets and handheld PCs
- Android-native Kotlin + Jetpack Compose
- GitHub Actions workflow to build a debug APK
- No external service or database required for the starter build

## Build

Open this repository in Android Studio (recent stable version), allow Gradle sync, then run the app configuration.

Or use the included GitHub Actions workflow. Push to main, open Actions, run Build Android APK, and download the hp3000xl-debug-apk artifact after the workflow succeeds.

Local command (requires JDK 17 and Android SDK):
```bash
./gradlew assembleDebug
```

APK output:
`app/build/outputs/apk/debug/app-debug.apk`

## Architecture

- `app/`: Android UI and application entry point
- `docs/`: design notes and roadmap
- `.github/workflows/android.yml`: reproducible CI build

## Roadmap

1. Add persistent notes and file storage.
2. Add a safer sandboxed terminal command interpreter.
3. Embed Lua and BASIC runtimes.
4. Add windowing, keyboard-shortcut support, and user-selectable themes.
5. Add optional emulator integration for legacy software.

## Compatibility and safety

The starter terminal is a simulated command console. It does not provide a root shell, execute arbitrary native commands, or access private Android system interfaces. This project does not include HP or Microsoft proprietary firmware or operating-system binaries.

## License

MIT. See `LICENSE`.
