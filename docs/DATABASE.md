# Database and Persistence

## Purpose

The persistent data layer stores user-owned PIM information, application metadata and configuration without attempting to reproduce a proprietary Windows CE registry implementation.

## Storage

The initial target is Android SQLite. Room can be placed above SQLite for type-safe entities and migrations.

## Logical tables

```text
settings
contacts
calendar_events
tasks
notes
documents
favorites
browser_history
installed_apps
update_history
```

## settings

```text
key TEXT PRIMARY KEY
value TEXT NOT NULL
updated_at INTEGER NOT NULL
```

## contacts

```text
id INTEGER PRIMARY KEY
name TEXT NOT NULL
phone TEXT
email TEXT
organization TEXT
notes TEXT
created_at INTEGER NOT NULL
updated_at INTEGER NOT NULL
```

## calendar_events

```text
id INTEGER PRIMARY KEY
title TEXT NOT NULL
start_at INTEGER NOT NULL
end_at INTEGER
location TEXT
notes TEXT
all_day INTEGER NOT NULL DEFAULT 0
```

## installed_apps

```text
id TEXT PRIMARY KEY
name TEXT NOT NULL
version TEXT NOT NULL
package_uri TEXT
enabled INTEGER NOT NULL DEFAULT 1
source TEXT NOT NULL
installed_at INTEGER NOT NULL
```

## update_history

```text
id INTEGER PRIMARY KEY
version TEXT NOT NULL
package_name TEXT NOT NULL
sha256 TEXT
status TEXT NOT NULL
installed_at INTEGER NOT NULL
```

## Migration policy

Database schemas must use explicit versioned migrations. Destructive migration must not occur automatically for user PIM data.

## Backup

Backups should export user data into a user-selected Android document URI. The backup format should be documented and versioned.
