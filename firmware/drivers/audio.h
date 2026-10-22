#ifndef HP3000XL_AUDIO_H
#define HP3000XL_AUDIO_H

#include <stdint.h>
void hp3000xl_audio_init(uint32_t sample_rate);
void hp3000xl_audio_tone(uint16_t frequency_hz, uint16_t duration_ms);
void hp3000xl_audio_beep(uint8_t pattern);

#endif
