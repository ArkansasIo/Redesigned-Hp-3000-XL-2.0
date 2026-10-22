#ifndef HP3000XL_KEYBOARD_H
#define HP3000XL_KEYBOARD_H

#include <stdint.h>
void hp3000xl_keyboard_init(void);
void hp3000xl_keyboard_key(uint32_t keycode, uint8_t pressed);

#endif
