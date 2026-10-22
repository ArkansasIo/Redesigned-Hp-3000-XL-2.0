# Update Manifest Example

Example metadata for the first pre-alpha update channel.

```json
{
  "product": "Redesigned HP 3000 XL 2.0",
  "codename": "Color Mobile Edition",
  "version": "0.1.0-pre-alpha",
  "buildId": "HP3000XL-PA-0001",
  "channel": "pre-alpha",
  "android": {
    "minApi": 26,
    "targetApi": 35,
    "applicationId": "io.arkansasio.hp3000xl"
  },
  "release": {
    "tag": "v0.1.0-pre-alpha",
    "packageType": "apk"
  }
}
```

## Verification

A production update manifest should additionally contain the HTTPS download URL, file size, SHA-256 digest, publisher signature, release notes, and compatibility constraints. The application must verify those fields before installing an update.

APK installation must be delegated to Android's package installer; the application must not silently replace its own installed APK.
