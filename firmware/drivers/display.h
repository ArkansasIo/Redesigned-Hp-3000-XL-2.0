#ifndef HP3000XL_DISPLAY_H
#define HP3000XL_DISPLAY_H

#include <stdint.h>
void hp3000xl_display_init(uint32_t width, uint32_t height);
void hp3000xl_display_clear(uint32_t rgb);
void hp3000xl_display_present(void);

#endif
