# HP 3000 XL 2.0 — Full System File Map

This document is the implementation map for the native Android application and static browser preview. Existing files are retained; new modules should be added incrementally and wired into the existing app rather than duplicating entry points.

## Native Android module

Base package: `io.arkansasio.hp3000xl`

### Application and runtime
- `app/src/main/java/io/arkansasio/hp3000xl/`: application entry point, lifecycle, runtime state, navigation and dependency wiring.
- `core/model/`: shared app, window, device and settings models.
- `core/data/`: repositories and persistence adapters.
- `core/platform/`: Android battery, storage, clipboard, notification, connectivity and device information adapters.
- `core/security/`: permission checks, input validation and safe defaults.

### GUI and user interface
- `ui/theme/`: color palette, typography, spacing, light/dark modes and dynamic accent.
- `ui/shell/`: desktop, Start menu, taskbar, status bar, desktop shortcuts and window manager.
- `ui/components/`: reusable buttons, dialogs, menus, forms, toolbars, status indicators and empty/error/loading states.
- `ui/settings/`: display, sound, input, regional, owner, privacy/security, network and power settings.
- `ui/controlpanel/`: system categories and settings screens.
- `ui/taskmanager/`: app registry, running-workspace view and close actions.
- `ui/help/` and `ui/about/`: help topics, version, diagnostics and legal notices.

### Built-in applications
- `apps/explorer/`: SAF-based file access, locations, recent items and document actions.
- `apps/word/`: editable documents, local drafts and import/export.
- `apps/excel/`: grid model, formulas roadmap and CSV import/export.
- `apps/outlook/`: local inbox demonstration and message model.
- `apps/contacts/`, `apps/calendar/`, `apps/tasks/`, `apps/notes/`: PIM interfaces and repository contracts.
- `apps/calculator/`: validated arithmetic expressions.
- `apps/browser/`: browser workspace and safe navigation controls.
- `apps/terminal/`: allow-listed simulated commands only; never expose arbitrary shell execution.
- `apps/games/`: Solitaire and Minesweeper game screens.

### API and services
- `api/app/`: application manifest, registry, launch/close and lifecycle contracts.
- `api/files/`: document/file repository abstractions.
- `api/pim/`: contacts, calendar, tasks and notes repository contracts.
- `api/settings/`: typed settings keys and persistence.
- `api/system/`: device information, power, storage, clipboard and notifications contracts.
- `api/network/`: connectivity state and network preferences.
- `api/packages/`: validated `.hpapp` manifest format; packages are metadata/content, not arbitrary executable code.
- `services/`: Android-backed implementations and lifecycle-aware observers.

### Persistence and resources
- Room/SQLite entities, DAOs, migrations and repositories for settings and PIM data.
- `app/src/main/res/drawable/`: original icons and artwork.
- `app/src/main/res/mipmap-*/`: launcher icons.
- `app/src/main/res/xml/`: backup and file-sharing policies, if enabled.
- `app/src/main/assets/`: bundled non-proprietary help and sample content.

## Browser preview

- `index.html`: standalone responsive browser desktop and its client-side app behaviors.
- `.github/workflows/pages.yml`: validates and deploys the static preview.
- `docs/WEB_PREVIEW.md`: browser capabilities and limitations.
- `docs/PAGES_DEPLOYMENT.md`: deployment and 404 troubleshooting.

The browser preview must remain static-host compatible. Use relative/local assets only or absolute URLs for deliberately external resources. It must not pretend to provide native Android capabilities or remote account synchronization.

## Implementation and validation rules

1. Inspect current source before adding or replacing files; do not create duplicate application entry points.
2. Keep the existing package ID and minimum supported Android version unless a migration is explicitly planned.
3. Prefer Kotlin, Jetpack Compose, typed state, repositories and Android platform APIs.
4. Ask for runtime permissions only when a user invokes the related feature; degrade gracefully when denied.
5. Use Android Storage Access Framework for user-selected files. Do not claim broad filesystem access.
6. Bluetooth, USB, notifications and networking require real Android implementations and permission handling; UI-only mockups must be labeled.
7. Never execute terminal text as a system shell command.
8. Do not include proprietary HP/Microsoft firmware, ROMs, logos or binaries. This is an independent recreation.
9. Verify each increment with Gradle compilation/tests and the GitHub Actions workflows. Mark any untested subsystem as pending rather than complete.
10. Treat the browser preview and Android application as separate runtimes; browser localStorage does not synchronize with native SQLite.
