#ifndef HP3000XL_KERNEL_API_H
#define HP3000XL_KERNEL_API_H

#include <stdint.h>

typedef void (*hp3000xl_irq_handler)(uint32_t irq);

void hp3000xl_kernel_init(void);
void hp3000xl_register_irq(uint32_t irq, hp3000xl_irq_handler handler);
void hp3000xl_yield(void);
uint64_t hp3000xl_ticks(void);

#endif
