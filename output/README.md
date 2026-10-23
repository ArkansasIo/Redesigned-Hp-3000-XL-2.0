# Output folder

This folder holds the built distributable artifacts of the project.

| File | Description |
|---|---|
| `HP3000XL-2.0-debug.apk` | Debug build of the HP 3000 XL 2.0 Color Mobile Edition Android app. Installable on any Android device ("install unknown apps" required). |

## Rebuilding the APK

```bash
./gradlew assembleDebug
copy app\build\outputs\apk\debug\app-debug.apk output\HP3000XL-2.0-debug.apk
```

## Notes

- `.gitignore` keeps this folder tracked but excludes anything except `README.md` —
  add a `!output/<filename>` rule for each artifact you want committed.
- GitHub blocks files larger than 100 MB. Use GitHub Releases for larger binaries.
- There is currently no Windows `.exe` build: this project is an Android app
  (Kotlin/Gradle). If a desktop/launcher executable is added later, place it here.
