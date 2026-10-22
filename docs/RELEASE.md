# Release Process

## Versioning

Use semantic versioning:

```text
MAJOR.MINOR.PATCH
```

Examples:

```text
2.0.0  major platform release
2.1.0  feature release
2.1.1  bug-fix release
```

## Release contents

A stable release should contain:

- Signed APK/AAB
- SHA-256 checksums
- Release manifest
- Changelog
- Installation instructions
- Compatibility information
- Known issues

## Git tag

Use tags matching the version:

```text
v2.0.0
v2.1.0
v2.1.1
```

## GitHub Release

The release should publish the production artifact and documentation. Debug workflow artifacts are development outputs and should not be presented as production releases.

## Release checklist

- [ ] Build succeeds
- [ ] Unit/UI tests pass
- [ ] Android 10+ installation tested
- [ ] APK/AAB signed
- [ ] SHA-256 recorded
- [ ] Release manifest generated
- [ ] Changelog updated
- [ ] Documentation updated
- [ ] Backup/recovery tested
- [ ] Git tag created
- [ ] GitHub Release published
- [ ] Update channel metadata points to the release

## Rollback

If a release is defective, stop advertising it through the stable update channel and publish a corrected patch release. Do not rely on an unsafe forced downgrade.
