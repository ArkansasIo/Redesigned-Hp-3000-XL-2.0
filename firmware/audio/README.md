# Device Audio

The original HP device's recordings are not redistributed. This layer defines replacement audio events that can be implemented as synthesized tones or newly authored sound effects.

Events:
- `boot_start`
- `boot_ready`
- `button_press`
- `menu_open`
- `notification`
- `warning`
- `power_low`
- `shutdown`

Android implementations should use generated/local assets rather than proprietary recordings.
