# GitHub Pages Deployment

This repository has two deliverables: the native Android/Kotlin application and a static browser preview served from the repository root.

## Published site

Expected project-site URL:

https://arkansasio.github.io/Redesigned-Hp-3000-XL-2.0/

The static preview is the root `index.html`. It is not the Android app, an Android emulator, or a backend service.

## Deployment workflow

The workflow in `.github/workflows/pages.yml` builds the Pages artifact from `index.html` and `.nojekyll`, validates the entry point, uploads the artifact, and deploys it using the official GitHub Pages Actions.

It runs on pushes to `main` that touch the browser entry page, the Pages marker, or the workflow, and can be started manually from the Actions tab.

## One-time GitHub setting

In the repository, open **Settings → Pages** and confirm **Build and deployment → Source** is set to **GitHub Actions**. The workflow's deploy job requires Pages write and OIDC token permissions; repository or organization policy must allow those permissions.

## Verify a deployment

1. Open the repository's **Actions** tab and select **Deploy GitHub Pages**.
2. Check that both `build` and `deploy` jobs succeed.
3. Open the published URL from the `github-pages` environment deployment.
4. Hard-refresh the page if the browser displays an older cached version.

If deployment fails with a Pages configuration error, check the one-time setting above and repository Actions permissions. If the workflow succeeds but the URL returns 404, verify the environment's published URL and that the Pages deployment is for this repository, not a similarly named project.

## Local preview checks

Open `index.html` directly or run a static server from the repository root. The browser preview uses local browser storage for demo data; it does not synchronize with the Android application's SQLite database.
