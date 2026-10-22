#include "kernel_api.h"

static uint64_t ticks;
static hp3000xl_irq_handler irq_handlers[32];

void hp3000xl_kernel_init(void) { ticks = 0; }
void hp3000xl_register_irq(uint32_t irq, hp3000xl_irq_handler handler) {
    if (irq < 32) irq_handlers[irq] = handler;
}
void hp3000xl_yield(void) { ++ticks; }
uint64_t hp3000xl_ticks(void) { return ticks; }
