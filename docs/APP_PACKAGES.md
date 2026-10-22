# HPAPP Package Specification

## Purpose

`.hpapp` is a project-defined application package format for installing applications inside the HP 3000 XL runtime. It is not a Windows CE CAB file and does not contain proprietary Microsoft or HP software.

## Package layout

```text
example.hpapp
├── manifest.json
├── icon.png
├── app/
│   ├── metadata.json
│   └── resources/
├── code/
└── checksums.sha256
```

The exact executable/runtime representation depends on the sandbox selected for the application. Native Android code must remain an Android package and cannot be treated as trusted `.hpapp` content.

## Manifest

```json
{
  "format": 1,
  "id": "com.example.notes",
  "name": "Example Notes",
  "version": "1.0.0",
  "minRuntime": "2.0.0",
  "permissions": ["documents"],
  "entry": "app/metadata.json"
}
```

## Permissions

Possible runtime permissions include:

```text
documents
contacts
calendar
notifications
network
bluetooth
clipboard
```

Permissions must be explicit and enforced by the runtime. A package cannot request Android root/system privileges through the `.hpapp` manifest.

## Installation

```text
Select package
 -> validate ZIP structure
 -> parse manifest
 -> validate ID/version
 -> check runtime compatibility
 -> verify checksums
 -> verify signature
 -> request declared permissions
 -> install into application storage
 -> register with CeApplicationManager
```

## Upgrade

An upgrade must preserve the application ID and must pass version/compatibility checks.

## Uninstall

Uninstall removes the application package and registry entry. User data should be retained or explicitly offered for deletion.

## Signing

Production packages should use a detached signature and a trusted public key distributed with the runtime. Hashes provide integrity but do not establish publisher identity by themselves.

## Sandboxing

Application code must execute in a constrained runtime. The package format is not permission to execute arbitrary downloaded native code.
