# SIMH native port status

## Implemented in source control
- Android app-side SIMH profile model and runtime capability check.
- Emulator selection/status screen and catalog registration.
- Native bridge contract and an optional NDK CMake scaffold.
- JNI capability reporting that explicitly states the engine is not linked.

## Not implemented
- The SIMH CPU/device execution loop.
- HP 3000 system devices, disk/tape controllers, or boot path compiled for Android.
- Console input/output streaming or lifecycle start/stop.
- User-selected disk/tape image import and mounting.
- A booted HP 3000 guest on Android hardware.

## Release gates
A release must not advertise HP 3000 emulation as operational until all are complete:
- [ ] Pin and document upstream SIMH revision and license notices.
- [ ] Compile required SIMH core + HP3000 sources using Android NDK.
- [ ] Implement Android-safe console, timing, filesystem, and signal adapters.
- [ ] Connect JNI lifecycle and bounded console I/O.
- [ ] Add user-mediated SAF media import; validate and copy selected files safely.
- [ ] Add native unit tests for create/start/stop, invalid media, interruption, and shutdown.
- [ ] Run arm64-v8a and x86_64 builds.
- [ ] Demonstrate a documented, authorized HP 3000 guest image boot on a device/emulator.
- [ ] Verify no host shell, unsafe host command execution, or bundled proprietary guest media.

The native scaffold is intentionally fail-closed: ENGINE_NOT_LINKED means no machine can start.
