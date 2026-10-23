# Output folder

This folder holds the built distributable artifacts of the project.

| File | Description |
|---|---|
| `HP3000XL-2.0-debug.apk` | Debug build of the HP 3000 XL 2.0 Color Mobile Edition Android app. Installable on any Android device ("install unknown apps" required). |
| `HP3000XL-2.0.exe` | Windows launcher (compiled C#, .NET Framework 4). One click: finds adb, boots an emulator if none is attached, builds/installs the APK and launches the app with a live log window. |

## Rebuilding the APK

```bash
./gradlew assembleDebug
copy app\build\outputs\apk\debug\app-debug.apk output\HP3000XL-2.0-debug.apk
```

## Notes

- `.gitignore` keeps this folder tracked but excludes anything except `README.md` —
  add a `!output/<filename>` rule for each artifact you want committed.
- GitHub blocks files larger than 100 MB. Use GitHub Releases for larger binaries.
- There is currently no native Windows build of the app itself: this project is an
  Android app (Kotlin/Gradle). `HP3000XL-2.0.exe` is only a helper launcher that
  drives adb; its source is `launcher/HP3000XLLauncher.cs`, rebuilt with:

```bash
csc /target:winexe /out:output\HP3000XL-2.0.exe launcher\HP3000XLLauncher.cs
```
