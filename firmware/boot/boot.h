#ifndef HP3000XL_BOOT_H
#define HP3000XL_BOOT_H

enum hp3000xl_boot_state {
    BOOT_RESET,
    BOOT_HARDWARE_INIT,
    BOOT_MEMORY_TEST,
    BOOT_STORAGE_INIT,
    BOOT_KERNEL_LOAD,
    BOOT_HANDOFF,
    BOOT_RECOVERY,
    BOOT_ERROR
};

struct hp3000xl_boot_context {
    enum hp3000xl_boot_state state;
    unsigned error_code;
};

void hp3000xl_boot_step(struct hp3000xl_boot_context *ctx);

#endif
