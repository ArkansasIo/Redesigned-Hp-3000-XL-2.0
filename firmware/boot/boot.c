#include "boot.h"

void hp3000xl_boot_step(struct hp3000xl_boot_context *ctx) {
    if (!ctx) return;
    switch (ctx->state) {
        case BOOT_RESET: ctx->state = BOOT_HARDWARE_INIT; break;
        case BOOT_HARDWARE_INIT: ctx->state = BOOT_MEMORY_TEST; break;
        case BOOT_MEMORY_TEST: ctx->state = BOOT_STORAGE_INIT; break;
        case BOOT_STORAGE_INIT: ctx->state = BOOT_KERNEL_LOAD; break;
        case BOOT_KERNEL_LOAD: ctx->state = BOOT_HANDOFF; break;
        case BOOT_HANDOFF: break;
        default: ctx->state = BOOT_RECOVERY; break;
    }
}
