# ROM Image Model — 1.2.0

`ROM_ASSET` in `BuildInfo` identifies the planned ROM-model artifact for the Color Mobile Edition. It is **not** an original HP ROM and is not a flashable hardware image.

## Purpose

The Android application emulates the device experience entirely as an APK. The ROM model documents the firmware-style layers that the application exposes:

- boot state machine
- board/display/input descriptors
- storage abstraction
- audio event abstraction
- power states
- kernel/HAL integration boundary
- application/runtime handoff

## Compatibility

- Android minimum: API 29 / Android 10
- Primary test class: Huawei P20 / P20 Pro class devices
- Package: `io.arkansasio.hp3000xl`

No Microsoft Windows CE binary, HP proprietary firmware, HP ROM dump, or extracted copyrighted system image is included.
