# SIMH integration for Redesigned HP 3000 XL 2.0

## Goal

Connect the independent Android shell to a reviewed SIMH source tree, starting with the HP 3000 simulator. The shell-side UI lives in this repository; simulator source is maintained separately.

## Implemented integration slice

- Kotlin configuration model for SIMH machine profiles.
- JNI bridge capability detection with explicit `ENGINE_NOT_LINKED` and `LIBRARY_UNAVAILABLE` states.
- Private per-machine data workspace below the app's files directory.
- Compose UI for selecting HP 3000, PDP-11 or VAX and inspecting runtime status.
- App catalog entry for the emulator tool.
- Runtime status now comes from the JNI bridge capability check rather than guessing readiness from the presence of a machine-named `.so` file.

## Current limitation

The native C++ target is a bridge scaffold only. It reports `ENGINE_NOT_LINKED`; the SIMH CPU/device execution loop, HP 3000 device models, machine launch/stop lifecycle, console streaming and guest boot path are not integrated. Therefore the application must not advertise a runnable HP 3000 guest yet.

`READY` from a future bridge should mean the native engine and machine lifecycle are available, not that a guest operating system has booted. A documented boot smoke test is a separate release gate.

## Source and licensing review

Before selecting or copying an upstream source revision:

1. Identify the exact source distribution/revision and record its immutable commit or release version.
2. Review the license and notices shipped with that exact source package; retain all required notices in derived distributions.
3. Verify that the chosen HP 3000 implementation matches the intended classic machine family and its documented device support.
4. Do not mix source trees or assume that every repository named SIMH has identical provenance, maintenance status or authorization.
5. Do not bundle proprietary HP firmware, ROM images, operating-system media, disk packs or tapes. Users must supply media they are authorized to use.

Useful references for review:
- [HP 21xx/1000 and HP 3000 simulator distribution and documentation](https://simh.trailing-edge.com/hp/)
- [HP 3000 Simulator User's Guide](https://simh.trailing-edge.com/hp/docs/hp3000_guide.pdf)
- [SIMH source repository](https://github.com/simh/simh)

The source and license must be reviewed before vendoring or compiling it into this app.

## Native port plan

1. Pin and document the reviewed SIMH source revision and its required notices.
2. Build the HP 3000 simulator plus required SIMH framework modules as a CMake/NDK target. The target requires multiple HP 3000 source files and framework modules; compiling a single machine file is insufficient.
3. Port host-specific console, filesystem, timing, signal and terminal integration as needed. Disable host-shell execution and network features unless implemented and reviewed.
4. Expose a minimal JNI API for create/start/stop, bounded console input/output and orderly shutdown.
5. Package and test each intended ABI (initially `arm64-v8a` and `x86_64`) using Gradle external native build.
6. Import disk and tape images through Android Storage Access Framework, validating input and copying selected media into app-owned workspace.
7. Add native tests for lifecycle, invalid media, input/output, interruption and clean shutdown.
8. Run a documented guest boot smoke test with authorized user-provided media before claiming emulation works.

## Runtime contract

- `SimhRuntime.status(machine)` reports the native bridge capability. It does not prove a guest operating system boots.
- `ENGINE_NOT_LINKED` means the bridge is present but the simulator engine is not integrated.
- `LIBRARY_UNAVAILABLE` means the bridge library cannot be loaded for the current device.
- `SimhEmulatorApp.launchPrerequisites(machine)` supplies machine ID, ABI, status and private data directory to the UI.
- The emulator screen must never pretend a missing or unlinked runtime has started.
- Future media import belongs in private `files/simh/<machine>/` storage after explicit user selection.

## Validation status

The Android workflow should run unit tests before assembling APKs. Native emulation remains incomplete until the NDK/JNI engine, lifecycle and guest-system tests are implemented and pass.
