# Terminal and Programming Languages

The 1.2.0 pre-alpha development environment provides a CE-style terminal and language catalog.

## Terminal

The terminal uses an application-owned command environment rather than executing arbitrary Android shell commands. This keeps the default terminal sandboxed.

Built-ins include:

`help`, `clear`, `pwd`, `ls`, `cd`, `cat`, `echo`, `date`, `time`, `apps`, `info`, `settings`, `update`, `lang`, `run`, `history`, `exit`

## Language catalog

- Kotlin
- Java
- C
- C++
- Rust
- Python
- Lua
- JavaScript
- TypeScript
- POSIX Shell
- BASIC 2026
- Assembly

The catalog describes toolchains/runtimes; it does not pretend that every compiler is bundled in the Android APK. Native or external toolchains can be connected later through a controlled developer environment.
