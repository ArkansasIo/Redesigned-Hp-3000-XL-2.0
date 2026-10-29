# SIMH integration for Redesigned HP 3000 XL 2.0

## Goal
Connect the independent Android shell to the SIMH source tree, starting with the HP 3000 simulator. The shell-side UI lives in this repository; SIMH source remains separately maintained in [ArkansasIo/simh](https://github.com/ArkansasIo/simh).

## Added in this integration slice
- Kotlin configuration model for SIMH machine profiles.
- Runtime capability detection for the current Android ABI.
- Private per-machine data workspace below the app's files directory.
- Compose UI for selecting HP 3000, PDP-11 or VAX and inspecting runtime status.
- App catalog entry for the new emulator tool.

## Important limitation
The SIMH C emulator is **not yet compiled or executable on Android** by these files. The runtime reports `NOT_INSTALLED` unless a matching ABI-specific `libsimh_<machine>.so` is packaged. A native library alone is not enough: a tested machine-specific JNI launch and console bridge is also required.

## Native port plan
1. Review SIMH licensing notices and retain required notices with derived redistributions.
2. Build a CMake/NDK target for the HP 3000 simulator and required SIMH framework modules. The target needs multiple `HP3000/` C files plus core SIMH sources; compiling only `hp3000_sys.c` is insufficient.
3. Port host-specific console, filesystem, timing, signal and terminal integration as needed. Disable host-shell execution and network features unless implemented and reviewed.
4. Expose a minimal JNI API for create/start/stop, console input/output and orderly shutdown.
5. Package and test each intended ABI (for example `arm64-v8a` and `x86_64`) using Gradle external native build.
6. Import disk and tape images through Android Storage Access Framework, validating size and copying to the app-owned workspace.
7. Add native unit and device smoke tests for lifecycle, input/output, interruption, media mounting and clean shutdown.
8. Do not bundle proprietary HP firmware, ROM images, operating-system media, disk packs or tapes. Users provide media they are authorized to use.

## Runtime contract
- `SimhRuntime.status(machine)` tests only for the expected shared library; it does not prove a guest boots.
- `SimhEmulatorApp.launchPrerequisites(machine)` supplies machine ID, ABI, status and private data directory to the UI.
- `SimhEmulatorScreen` never pretends a missing runtime has started.
- Future user media import belongs in private `files/simh/<machine>/` storage after explicit user selection.

## Source references
- SIMH repository: https://github.com/ArkansasIo/simh
- HP 3000 sources: https://github.com/ArkansasIo/simh/tree/master/HP3000
- Simulator system interface: https://github.com/ArkansasIo/simh/blob/master/HP3000/hp3000_sys.c

## Validation status
Run the Android Gradle workflow to validate the Kotlin UI additions. Native emulation is not implemented until the NDK/JNI bridge and guest-system tests are completed.
