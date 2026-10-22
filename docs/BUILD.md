# Build Guide

## Requirements

- Android Studio or a compatible Gradle/JDK environment
- JDK 17
- Android SDK platform 35
- Android Build Tools 35.0.0
- Android 10+ device or emulator for runtime testing

## Local build

```bash
./gradlew assembleDebug
```

If the Gradle wrapper is unavailable in a checkout, use the installed Gradle version supported by the project.

Output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Install for development

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

The device must have developer options/USB debugging enabled for ADB installation.

## GitHub Actions

The Android workflow:

1. Checks out the repository.
2. Installs JDK 17.
3. Configures Android SDK.
4. Accepts SDK licenses.
5. Installs API 35/build tools.
6. Configures Gradle.
7. Builds the debug APK.
8. Uploads the APK as a workflow artifact.

The workflow currently builds a debug artifact. A production release workflow should add signing and release publication only after signing keys are securely configured.

## Release build

Release signing credentials must never be committed. Use GitHub Actions encrypted secrets or an external signing service.

## Troubleshooting

### Gradle dependency resolution

Run:

```bash
./gradlew --refresh-dependencies assembleDebug
```

### SDK errors

Confirm `ANDROID_HOME` and install the required platform/build-tools versions.

### Device installation errors

Check Android version, ABI, package signature, existing installation signature and available storage.
