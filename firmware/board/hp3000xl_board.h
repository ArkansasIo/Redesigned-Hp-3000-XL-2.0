#ifndef HP3000XL_BOARD_H
#define HP3000XL_BOARD_H

#include <stdint.h>

#define HP3000XL_BOOT_MAGIC 0x48503358u
#define HP3000XL_DISPLAY_WIDTH 640u
#define HP3000XL_DISPLAY_HEIGHT 480u
#define HP3000XL_TOUCH_MAX_X 4095u
#define HP3000XL_TOUCH_MAX_Y 4095u

struct hp3000xl_board_info {
    uint32_t ram_bytes;
    uint32_t storage_bytes;
    uint32_t display_width;
    uint32_t display_height;
};

#endif
