# Project Roadmap

## Phase 1 — Shell

- Full-color desktop
- Taskbar
- Start menu
- Window model
- Built-in application navigation

## Phase 2 — Runtime

- Application registry
- Persistent database
- System-service abstractions
- PIM persistence

## Phase 3 — Android integration

- Storage Access Framework
- Android browser integration
- Notifications
- Battery/storage information
- Keyboard/stylus handling
- Bluetooth/USB/Wi-Fi settings

## Phase 4 — Application platform

- `.hpapp` package format
- Manifest permissions
- Application installer
- Sandboxed scripting
- Lua/BASIC runtime
- Document import/export

## Phase 5 — Update platform

- GitHub release discovery
- Release manifests
- Download manager
- SHA-256 verification
- Publisher signature verification
- Patch packages
- Update history
- Recovery/rollback

## Phase 6 — Production

- Signed release APK/AAB
- Stable/beta/nightly channels
- Automated release workflow
- Device compatibility matrix
- Complete test suite
- User documentation

## Non-goals

The project will not distribute proprietary Windows CE/HP firmware, ROM images, Microsoft binaries or copyrighted legacy assets. Compatibility features will be implemented independently and used only with software the user is legally entitled to run.


## Browser preview and live site

- [x] Publish a responsive interactive desktop preview through GitHub Pages.
- [x] Add local demo notes, contacts and calendar reminders.
- [x] Add a sample spreadsheet and CSV export.
- [x] Add a simulated, allow-listed terminal (no OS command execution).
- [ ] Expand native app integration only with Android APIs, runtime permissions and device testing.

The web preview is not evidence that native Android features or device integrations are complete. See [WEB_PREVIEW.md](WEB_PREVIEW.md).
