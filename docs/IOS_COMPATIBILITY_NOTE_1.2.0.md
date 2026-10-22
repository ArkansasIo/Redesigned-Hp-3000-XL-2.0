# iOS Compatibility Note — 1.2.0

An Android APK is the deliverable for this project. An iOS system image is not embedded in the APK and is not required for the Android recreation.

The `IOS_COMPATIBILITY_ASSET` metadata name is retained only as a future compatibility/documentation hook. It does not identify an Apple operating-system image or redistribute iOS firmware.

## Current target

- Android 10 / API 29 minimum
- Huawei P20 / P20 Pro class devices
- Full recreation delivered as `io.arkansasio.hp3000xl`
- Touchscreen, keyboard, and mouse input supported through the Android compatibility layer

A future native iOS build would be a separate application target with its own signing and packaging process.
