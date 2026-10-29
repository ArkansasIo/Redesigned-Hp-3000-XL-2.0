# Testing Guide

## Build validation

Every change should pass:

```bash
./gradlew assembleDebug
```

## Unit testing targets

- Application registry
- Version comparison
- Manifest parsing
- SHA-256 verification
- Patch base-version validation
- Database migrations
- Calculator operations
- Menu navigation
- Settings persistence

## UI testing targets

- Desktop loads
- Start menu opens/closes
- Programs submenu works
- Settings submenu works
- Window close/back navigation works
- Keyboard navigation works where supported
- Portrait layout
- Landscape layout
- Tablet layout

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

The stated compatibility target is Android 10/API 29 and newer. The Gradle minimum SDK remains 26 for build compatibility, so runtime checks should enforce features that require newer APIs.

## Physical-device testing

Test on at least one Android 10-era device and one current Android device. Verify touch, keyboard, display scaling, storage permissions, notification behavior, battery behavior and APK installation.

## Release gate

A release should not be published until the debug/release build succeeds, critical UI flows work, update verification tests pass, and the artifact checksum has been recorded.


## GitHub Pages preview smoke test

- [ ] Open the published page on desktop and a narrow mobile viewport.
- [ ] Launch each app from Start and desktop icons; close and reopen via taskbar.
- [ ] Save a note, refresh, and verify the note remains in the same browser profile.
- [ ] Edit spreadsheet cells and export CSV.
- [ ] Add/remove calendar reminders and contacts; refresh to verify persistence.
- [ ] Try terminal commands `help`, `ver`, `dir`, and an unknown command; confirm they are simulation-only.
- [ ] Change the accent color and clear demo data from Settings.

The Pages Actions run validates the entry page and confirms deployment. These manual interactions should be recorded separately from Android build/device tests.
