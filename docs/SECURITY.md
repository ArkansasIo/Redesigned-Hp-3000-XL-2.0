# Security Model

## Principles

1. Treat downloaded content as untrusted.
2. Verify integrity before installation.
3. Verify publisher identity for production packages.
4. Use least-privilege Android permissions.
5. Keep application data separate from system data.
6. Never require root access.

## Update security

A release is acceptable only after:

```text
HTTPS transport
+ expected asset/type
+ size limits
+ SHA-256 verification
+ publisher signature verification
+ manifest validation
+ Android package verification
```

A failed verification must abort installation and leave the current version intact.

## Secrets

No GitHub token, signing private key, keystore password or other secret may be embedded in the APK or repository.

GitHub Actions release signing must use protected secrets or a dedicated secure signing system.

## Network security

The application should use HTTPS for update traffic and should reject unexpected redirects/hosts where practical. Arbitrary remote code loading is not part of the update design.

## Application packages

`.hpapp` packages run under the HP3000XL application sandbox. A manifest permission is an application-level capability and does not automatically grant an Android dangerous permission.

## Privacy

PIM data such as contacts, calendar entries and notes is local by default. Synchronization should be opt-in and documented separately.

## Threats

The security model considers:

- Malicious release asset
- Compromised download
- Wrong package/version
- Malicious `.hpapp`
- Manifest spoofing
- Downgrade attack
- Corrupted patch
- Excessive permissions
- Accidental data loss

## Recovery

Failed package verification must not overwrite an installed package. Updates should create recoverable application-data backups before destructive migrations.
