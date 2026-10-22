# Repository Folder Structure

```text
Redesigned-Hp-3000-XL-2.0/
├── .github/workflows/android.yml
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/io/arkansasio/hp3000xl/
│       │   ├── api/
│       │   ├── apps/
│       │   ├── core/
│       │   ├── input/
│       │   ├── network/
│       │   ├── packages/
│       │   ├── services/
│       │   ├── storage/
│       │   ├── update/
│       │   └── shell/application sources
│       └── res/values/
├── docs/
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
└── README.md
```

The source packages separate the CE-style shell, application framework, Android-backed services, persistence, networking, package/update APIs, and public compatibility contracts.
