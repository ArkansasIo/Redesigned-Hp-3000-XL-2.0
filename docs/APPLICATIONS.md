# Applications and Menu System

## Desktop

The desktop is the primary handheld shell. It provides shortcuts, taskbar access, Start, clock, sound state and the current application window.

## Programs

### File Explorer

Responsibilities:

- Browse user-selected storage locations
- Open documents
- Create folders where Android permissions allow
- Display files and metadata
- Access Storage Card-style locations through SAF

### Pocket Word

A lightweight document editor with:

- New/open/save
- Text editing
- Recent documents
- Basic formatting model
- Import/export adapters planned

### Pocket Excel

A lightweight spreadsheet application with:

- Cells
- Rows and columns
- Basic formulas
- Workbook persistence
- Import/export adapters planned

### Pocket Outlook

Provides the handheld PIM hub:

- Inbox
- Contacts
- Calendar
- Tasks
- Notes

### Calculator

Provides arithmetic operations and is designed to expand into scientific functions.

### Internet

A browser workspace that delegates actual web navigation to Android-safe browser components or intents. It does not ship a proprietary legacy browser binary.

### Terminal

An application-level command console. Built-in commands are allow-listed and sandboxed; arbitrary shell execution is not part of the security model.

### Control Panel

Settings categories:

```text
Display
Power
Memory / Storage
Network
Regional Settings
Owner Information
Security
Stylus / Input
Sound
System Update
```

### Task Manager

Displays the HP3000XL application/task model and runtime state. It should not claim control over unrelated Android processes.

### Help

Context-sensitive documentation and troubleshooting.

### Games

Solitaire and Minesweeper are independent implementations with no proprietary game binaries.

## Start menu

```text
START
├── Programs
├── Settings
├── Documents
├── Favorites
├── Help
└── Suspend / Power
```

## Application lifecycle

Every managed application follows the common initialize/start/suspend/resume/saveState/close model.
