# Touchscreen, Keyboard and Mouse Support

The Android shell treats all three input classes as first-class sources.

## Touch
- Tap maps to primary click/select.
- Long press maps to context menu/right click.
- Drag maps to pointer movement.
- Two-finger gesture may be reserved for scrolling.

## Keyboard
- USB/Bluetooth HID keyboards are supported through Android key events.
- Function keys, arrows, Enter, Escape, Tab, Backspace and common shortcuts are normalized into `CeInputAction`.

## Mouse
- USB/Bluetooth HID mice are supported through Android pointer events.
- Primary/secondary buttons map to select/context menu.
- Wheel maps to vertical scrolling.

## Hybrid mode
Touch, mouse and keyboard events enter the same compatibility input router so applications do not need separate UI implementations.
