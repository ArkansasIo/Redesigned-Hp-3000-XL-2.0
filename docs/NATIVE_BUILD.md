# Native Android build configuration

The Android Gradle module now invokes CMake for the optional SIMH bridge scaffold.

## Toolchain
- Android Gradle Plugin: 8.9.2
- Android NDK: 27.2.12479018
- CMake: 3.22.1
- ABIs configured: arm64-v8a and x86_64

## What this build proves
A successful build proves that the JNI bridge scaffold compiles and is packaged for the configured Android ABIs. It does not prove that the SIMH core is linked, that the HP 3000 machine runs, or that a guest operating system boots.

## Local build
Install the configured SDK platform, NDK and CMake packages, then run:

    gradle assembleDebug
    gradle testDebugUnitTest

The GitHub Actions workflow installs the same SDK/NDK/CMake versions before building the APKs.

## Required before emulator release
The native SIMH core and HP3000 machine sources must be integrated, host dependencies adapted, JNI lifecycle/console functions implemented, and an authorized guest boot tested on each supported ABI.
