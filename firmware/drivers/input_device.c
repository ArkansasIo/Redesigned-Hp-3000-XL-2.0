#include "input_device.h"

static struct hp3000xl_input_event last_event;

void hp3000xl_input_submit(const struct hp3000xl_input_event *event) {
    if (event) last_event = *event;
}
