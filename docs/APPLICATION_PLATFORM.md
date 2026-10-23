# Application Platform

## Package model

Applications are represented by a package ID, semantic version, entry point and requested permissions. Package installation is modeled separately from Android APK installation.

## Lifecycle

```text
registered -> installed -> enabled -> running -> stopped
                         \-> disabled
```

The operating environment tracks lightweight process records. Android/Linux remains responsible for real process scheduling and memory management.

## Security boundary

Package metadata and application permissions are validated by the recreation layer. Android application sandboxing, filesystem permissions and platform security remain authoritative.

## Updates

The pre-alpha update channel accepts HTTPS update metadata with a 64-character SHA-256 value. Release signing and Android package verification must be performed by the Android platform before installation.

## Development tools

The development catalog exposes editor/language identities for BASIC, Lua, Python, JavaScript, TypeScript, Shell, C, C++, Rust, Java, Kotlin and Assembly. The catalog does not imply that compilers or interpreters are bundled.
