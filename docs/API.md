# Application and System API

## Purpose

The HP 3000 XL API is an application-level compatibility API for the Android recreation. It provides stable contracts for built-in programs and future third-party `.hpapp` applications.

## Core contracts

```text
CeApplication
CeService
CeWindow
CeMenu
CeDocument
CeFile
CeNotification
CeTask
CeDevice
```

### CeApplication lifecycle

```text
initialize(context)
start()
suspend()
resume()
saveState()
close()
```

Applications must not assume unrestricted filesystem access, root privileges, or direct control over Android system processes.

## Application manager

`CeApplicationManager` maintains:

- Application ID
- Display name
- Version
- Category
- Enabled state
- Built-in/installed origin
- Package metadata

Suggested ID format:

```text
io.arkansasio.hp3000xl.fileexplorer
io.arkansasio.hp3000xl.pocketword
io.arkansasio.hp3000xl.calendar
```

## Database API

`CeDatabase` provides persistence for settings, contacts, calendar events, notes, favorites and installed applications.

All database access should be asynchronous from UI code and should use parameterized queries/Room entities when the persistence implementation is expanded.

## System services

### Device

Exposes model, Android release, SDK level, battery and storage information.

### Power

Exposes safe application-level power state and suspend preferences. It does not replace Android power management.

### Storage

Uses Android Storage Access Framework URIs for user-selected files.

### Clipboard

Provides text clipboard operations subject to Android platform restrictions.

### Notifications

Provides application notifications through Android notification channels.

### Network

Provides connectivity state and approved network operations. Applications must respect Android permissions and cleartext/network-security policy.

## Update API

Future `CeUpdateService` should expose:

```text
checkForUpdates()
listReleases()
downloadPackage()
verifyPackage()
installPackage()
recordUpdate()
rollback()
```

The updater must verify package metadata and cryptographic hashes before installation.

## Security model

Application packages run with Android application permissions and HP3000XL runtime restrictions. A package is not trusted merely because it exists in a Git repository.

## Compatibility principle

The API reproduces useful handheld application concepts without copying proprietary Windows CE API implementation or Microsoft binaries.
