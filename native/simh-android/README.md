# SIMH Android native port scaffold

This directory is the source-controlled starting point for the Android NDK port. It is **not a working emulator** yet.

## Current contents
- CMakeLists.txt: optional standalone Android NDK target for the bridge scaffold.
- src/simh_android_bridge.cpp: JNI entry points for capability reporting and lifecycle contract checks.
- include/simh_android_bridge.h: native bridge contract.

The scaffold intentionally returns ENGINE_NOT_LINKED. It must not be mistaken for a built SIMH HP 3000 engine. No machine code from the SIMH source tree is linked by this target yet.

## Next integration steps
1. Vendor or fetch a pinned, reviewed SIMH source revision at build time; preserve its license and notices.
2. Build the required SIMH core and HP3000 source files as a separate target, adapting POSIX/terminal dependencies to Android.
3. Replace the ENGINE_NOT_LINKED response only after the real simulator lifecycle is connected.
4. Add a bounded console event queue and deterministic stop/join behavior; do not expose a host shell.
5. Add Storage Access Framework media import and machine boot smoke tests.
6. Build and test arm64-v8a and x86_64 on real devices/emulators.

Do not bundle proprietary HP firmware, ROMs, OS disks, or tape images. The user must provide media they are authorized to use.
