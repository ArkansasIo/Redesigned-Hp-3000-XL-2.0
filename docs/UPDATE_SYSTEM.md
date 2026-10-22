# GitHub Update and Patch System

## Purpose

The System Update component allows the HP 3000 XL application to discover, download and validate releases and application packages published for this project.

The updater is an application feature. It does not replace Android's package manager or silently modify the Android operating system.

## Release channels

```text
stable
beta
nightly
```

A production build should default to `stable`.

## Release metadata

A release may contain:

```text
release.json
HP3000XL-x.y.z.apk
HP3000XL-x.y.z.hpapp
HP3000XL-x.y.z.patch.zip
SHA256SUMS.txt
SIGNATURE.sig
CHANGELOG.md
```

Example manifest:

```json
{
  "product": "HP3000XL",
  "version": "2.0.0",
  "channel": "stable",
  "minAndroid": 29,
  "targetAndroid": 35,
  "packages": [
    {
      "name": "HP3000XL-2.0.0.apk",
      "type": "apk",
      "sha256": "..."
    }
  ]
}
```

## Update sequence

```text
Check Releases
      |
      v
Compare installed version
      |
      v
Show release notes
      |
      v
Download package
      |
      v
Verify SHA-256
      |
      v
Verify publisher signature
      |
      v
Validate manifest
      |
      v
Backup application data
      |
      v
Install
      |
      v
Record update history
```

## APK updates

APK installation must be delegated to Android's package installer. The application must not attempt privileged self-replacement.

Users may need to approve installation from the appropriate source according to Android security policy.

## `.hpapp` updates

`.hpapp` is the project's own application package format. It can update applications managed by `CeApplicationManager` without replacing the main Android APK.

## Patch updates

Patch archives should contain only files belonging to the HP3000XL application/package namespace. A patch must declare:

- Base version
- Target version
- File list
- SHA-256 for each file
- Manifest version
- Signature information

The updater must reject a patch whose base version does not match the installed version.

## Rollback

Before an update, application-owned persistent data should be backed up. Failed `.hpapp` updates can restore the previous package version. Android APK rollback remains subject to Android package-management rules.

## GitHub integration

The release service should query the repository's public Releases endpoint and consume release metadata/assets. It should not use a GitHub personal access token embedded in the APK.

## Network behavior

- Use HTTPS.
- Apply connection/read timeouts.
- Do not download executable content from arbitrary URLs.
- Validate expected asset names/types.
- Limit package size.
- Use resumable downloads where practical.
- Verify hashes after download.

## Current repository state

The repository documentation defines the update architecture, but a GitHub Release must exist before the application can discover a published release. Releases should be created only after a signed production artifact is available.
