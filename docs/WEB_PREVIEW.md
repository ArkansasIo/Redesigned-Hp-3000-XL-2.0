# Browser Desktop Preview

The public GitHub Pages site is an interactive, static demonstration of the HP 3000 XL desktop concept.

## Applications in the preview

- **File Explorer:** virtual sample folders and links to the preview applications.
- **Pocket Word:** a plain-text note editor with local browser persistence.
- **Pocket Excel:** an editable sample grid with CSV export.
- **Calculator:** basic arithmetic with expression validation.
- **Calendar:** add and remove reminders stored in the current browser.
- **Contacts:** add and remove local demo contacts.
- **Terminal:** allow-listed simulated commands only (`help`, `ver`, `date`, `dir`, `cls`, `echo`, `about`).
- **Settings:** change the accent color and clear local demo data.
- **About:** project and platform information.

## Run locally

Open `index.html` in a modern browser, or serve the repository root using any static HTTP server. No Node dependencies or build step are required.

## Storage and privacy

The preview stores notes, events, contacts and the accent preference in the browser's `localStorage`. Data is local to the browser profile and is not synchronized with Android or a server. Clearing browser storage may remove it. Do not store sensitive information in this demo.

## Limitations

This page is not an Android emulator. It cannot access arbitrary device files, run native applications, execute shell commands, provide a backend account, or implement Android system services. Those capabilities belong in the native Android app and require platform permissions and code.

## Deployment

The `.github/workflows/pages.yml` workflow validates `index.html`, packages the static site and deploys it to GitHub Pages after changes to the entry page or Pages workflow.
