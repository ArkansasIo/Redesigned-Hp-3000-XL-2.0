# HP3000XL Application Package Format

Application packages are represented by a manifest plus application resources.

## Manifest fields

- `id` — lowercase application identifier
- `name` — display name
- `version` — application version
- `entryPoint` — logical application entry point
- `minApi` — minimum HP3000XL runtime API
- `permissions` — requested platform capabilities
- `publisher` — package publisher

## Validation

The runtime validates identifiers, required metadata and minimum API values before an application is registered.

## Security

Package manifests do not grant Android permissions. Android permission enforcement remains authoritative, and the HP3000XL permission layer provides an additional application-level policy boundary.
