# Testing Guide

## Build validation

Run the same checks used by Android CI:

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew assembleRelease
```

The CI workflow runs unit tests before packaging APK artifacts. A successful source commit is not proof that a build passed; use the linked Actions run to confirm its conclusion and artifact uploads.

## Current unit test coverage

- Calculator expression parsing: precedence, parentheses, unary signs, decimals, malformed expressions, division by zero and expression length limits.
- SIMH bridge capability: unavailable library and unlinked engine must not be considered launchable.
- SIMH configuration: default machine, invalid console port and workspace path validation.
- SIMH media copy: successful copy, exact size limit, oversize rejection, empty input and invalid limits.
- Pocket Excel cell model: cell storage, clearing, bounds checking, positive dimensions and deterministic row-major snapshots.
- Package metadata validation: ID syntax, required fields, permission strings and minimum Android API.
- Core runtime and OS runtime smoke tests.
- SIMH emulator catalog registration.

## Recommended additional unit testing

- Application registry and lifecycle.
- Version comparison and manifest parsing.
- SHA-256 verification and update base-version validation.
- Database persistence and migrations.
- Menu navigation and settings persistence.
- Media importer failure cleanup and SAF integration using Android instrumentation tests.

## UI testing targets

- Desktop loads.
- Start menu opens/closes.
- Programs submenu works.
- Settings submenu works.
- Window close/back navigation works.
- Keyboard navigation works where supported.
- Portrait, landscape and tablet layouts.
- SIMH machine selection and capability status.
- Media picker cancellation and import failure messaging.
- Emulator launch remains disabled while the native engine reports `ENGINE_NOT_LINKED`.

## Update testing

Use test releases to validate:

1. No update available.
2. Normal update available.
3. Patch update available.
4. Invalid checksum.
5. Invalid signature.
6. Unsupported Android version.
7. Unsupported runtime version.
8. Interrupted download.
9. Insufficient storage.
10. Failed installation.
11. Rollback/recovery.

## Android versions

The current Gradle minimum SDK is API 29 (Android 10). This is the configured build minimum; actual device compatibility still requires physical-device or emulator testing.

## Physical-device testing

Test on at least one Android 10-era device and one current Android device. Verify touch, keyboard, display scaling, storage permissions, notification behavior, battery behavior and APK installation. Validate both `arm64-v8a` and `x86_64` build outputs where supported.

## Release gate

A release should not be described as verified until the debug and release builds succeed, critical UI flows work, update verification tests pass, and artifact checksums have been recorded. Native HP 3000 emulation has a separate gate: the real SIMH engine must be linked, its lifecycle and console bridge tested, and a guest boot smoke test completed with authorized media.

## GitHub Pages preview smoke test

- [ ] Open the published page on desktop and a narrow mobile viewport.
- [ ] Launch each app from Start and desktop icons; close and reopen via taskbar.
- [ ] Save a note, refresh, and verify the note remains in the same browser profile.
- [ ] Edit spreadsheet cells and export CSV.
- [ ] Add/remove calendar reminders and contacts; refresh to verify persistence.
- [ ] Try terminal commands `help`, `ver`, `dir`, and an unknown command; confirm they are simulation-only.
- [ ] Change the accent color and clear demo data from Settings.

The Pages Actions run validates the entry page and confirms deployment. These manual interactions should be recorded separately from Android build/device tests.
