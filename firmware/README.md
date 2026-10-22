# HP3000XL Firmware Layer

This directory contains an independent firmware/boot model for the recreated handheld platform. It is source-only and does not contain HP proprietary ROM, boot ROM, Windows CE binaries, or extracted device firmware.

## Layers
- `boot/` — boot state machine and diagnostics
- `kernel/` — small platform abstraction intended to boot a Linux-based image or Android kernel
- `drivers/` — keyboard, mouse, touchscreen, display, audio, power and storage interfaces
- `board/` — hardware profile and memory map
- `tools/` — image/header generation utilities

The Android application remains the primary runtime on modern devices. This firmware tree is a hardware-oriented expansion path for an emulator or custom board.
