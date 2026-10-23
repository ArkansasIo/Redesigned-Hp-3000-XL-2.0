# OS Runtime

The Android application hosts an independent handheld operating-environment model above Android/Linux. It does not replace or redistribute the underlying Android/Linux kernel.

## Runtime layers

```text
Android / Linux kernel
        |
        +-- Android framework
        |
        +-- HP3000XL Android application
              |
              +-- OsRuntime
              +-- OsKernel
              +-- OsBootManager
              +-- OsServiceRegistry
              +-- OsEventBus
              +-- OsApplicationHost
              +-- OsProcessManager
              +-- OsPermissionManager
              +-- CE shell and applications
```

## Boot sequence

1. Power state is initialized.
2. Memory/runtime state is prepared.
3. Display state is prepared.
4. Touch, keyboard and pointer input become available.
5. Storage services are started.
6. Network services are initialized.
7. Audio state is initialized.
8. Application services are started.
9. The desktop shell is presented.
10. The runtime emits `BootCompleted`.

## Process model

Applications receive a lightweight process record containing a PID, application ID, name, state and foreground flag. Actual CPU scheduling remains the responsibility of Android/Linux.

## Permissions

The recreation maintains application-level permissions for file access, network, Bluetooth, notifications, clipboard, power and system settings. Android permissions remain authoritative underneath this layer.

## Application lifecycle

`OsApplicationHost` validates built-in application IDs, creates a process record, updates the active application and emits lifecycle events. Closing an application stops its process record and clears the active application when appropriate.

## Legal boundary

This runtime is original project code. It is not Windows CE, does not contain HP ROM images, and does not emulate proprietary firmware by redistributing it.
