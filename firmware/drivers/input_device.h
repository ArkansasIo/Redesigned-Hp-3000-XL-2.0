#ifndef HP3000XL_INPUT_DEVICE_H
#define HP3000XL_INPUT_DEVICE_H

#include <stdint.h>

enum hp3000xl_input_type { INPUT_TOUCH, INPUT_KEYBOARD, INPUT_MOUSE };
struct hp3000xl_input_event {
    enum hp3000xl_input_type type;
    int32_t x, y;
    int32_t dx, dy;
    uint32_t buttons;
    uint32_t keycode;
};

void hp3000xl_input_submit(const struct hp3000xl_input_event *event);

#endif
