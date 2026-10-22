#ifndef HP3000XL_TOUCHSCREEN_H
#define HP3000XL_TOUCHSCREEN_H

#include <stdint.h>
struct hp3000xl_touch_point { uint16_t x, y, pressure; uint8_t contact; };
void hp3000xl_touch_init(void);
void hp3000xl_touch_report(struct hp3000xl_touch_point point);

#endif
