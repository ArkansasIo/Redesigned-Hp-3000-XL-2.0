#ifndef HP3000XL_MOUSE_H
#define HP3000XL_MOUSE_H

#include <stdint.h>
void hp3000xl_mouse_init(void);
void hp3000xl_mouse_move(int32_t dx, int32_t dy);
void hp3000xl_mouse_button(uint8_t button, uint8_t pressed);

#endif
