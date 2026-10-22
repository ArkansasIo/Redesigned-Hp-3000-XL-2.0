# Visual Design System — 1.2.0

The Color Mobile Edition uses an original visual system inspired by late-1990s/early-2000s handheld desktop interaction: compact window chrome, beveled controls, blue title bars, silver panels, dense information layouts, and full-color LCD presentation.

## Palette

| Token | Hex | Use |
|---|---|---|
| LCD Background | `#B8C9D4` | desktop/display background |
| Panel Silver | `#D4D9DD` | panels and taskbar |
| Panel Dark | `#7B8790` | bevel/shadow edges |
| Command Blue | `#164A78` | title bars and primary controls |
| Deep Blue | `#092B4A` | system chrome |
| Selection Blue | `#2E78B7` | selected rows and focus |
| Highlight | `#E8F2F8` | raised highlights |
| Text | `#10202C` | primary text |
| Warning | `#C58A20` | warnings |
| Error | `#A83B3B` | errors |
| Success | `#357A50` | ready/OK state |

## Screen structure

1. Boot diagnostics screen
2. Color desktop
3. Start menu
4. Taskbar/status area
5. Application window chrome
6. Menu bar and command rows
7. Recessed document/work areas
8. Dialogs and system notifications

## Interaction model

- Touch is the primary input on Android 10+.
- USB/Bluetooth HID keyboard and mouse events share the compatibility input router.
- Controls remain native Compose controls; PNGs are reference artwork and visual assets, not screenshots used as non-interactive UI.
- Touch targets are enlarged where necessary for Huawei P20/P20 Pro-class displays.

## Asset policy

The GUI artwork is newly created recreation material. The project does not package HP ROM images, Microsoft Windows CE binaries, extracted proprietary firmware, or original copyrighted device audio.
