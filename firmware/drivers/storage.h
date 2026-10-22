#ifndef HP3000XL_STORAGE_H
#define HP3000XL_STORAGE_H

#include <stdint.h>
int hp3000xl_storage_init(void);
int hp3000xl_storage_read(uint64_t offset, void *buffer, uint32_t length);
int hp3000xl_storage_write(uint64_t offset, const void *buffer, uint32_t length);

#endif
