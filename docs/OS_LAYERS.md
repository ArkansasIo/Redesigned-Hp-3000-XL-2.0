# OS Layers

```text
Firmware / Boot ROM abstraction
        ↓
Bootloader + Hardware Diagnostics
        ↓
Linux kernel / Android kernel integration boundary
        ↓
Hardware abstraction layer
        ↓
CE compatibility services
        ↓
Desktop shell + Start menu
        ↓
Built-in applications
        ↓
User documents / settings / packages
```

The project does not distribute the Linux kernel source as a clone or copy. A future board build can consume a properly licensed upstream Linux/Android kernel tree and connect it to these platform interfaces.
