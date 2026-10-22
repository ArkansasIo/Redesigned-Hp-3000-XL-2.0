#ifndef HP3000XL_POWER_H
#define HP3000XL_POWER_H

enum hp3000xl_power_state { POWER_ACTIVE, POWER_IDLE, POWER_SUSPEND, POWER_CHARGING };
void hp3000xl_power_init(void);
enum hp3000xl_power_state hp3000xl_power_state(void);
void hp3000xl_power_suspend(void);

#endif
