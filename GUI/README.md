# HP 3000 XL Color Mobile GUI Assets

This directory defines the visual asset contract for the Redesigned HP 3000 XL 2.0 Color Mobile Edition 1.2.0 pre-alpha.

## PNG screens

The generated GUI pack contains individual PNG screens for:

- Boot screen
- Desktop
- Start menu
- File Explorer
- Word Processor
- Spreadsheet
- Calendar
- Terminal
- Code Editor
- Calculator
- System Settings

These images are custom artwork for the recreation. They are not extracted HP ROM graphics, Windows CE binaries, firmware, or original proprietary device assets.

## Android integration

Recommended Android placement:

```text
app/src/main/res/drawable-nodpi/hp3000xl_boot_screen.png
app/src/main/res/drawable-nodpi/hp3000xl_desktop.png
app/src/main/res/drawable-nodpi/hp3000xl_start_menu.png
app/src/main/res/drawable-nodpi/hp3000xl_file_explorer.png
app/src/main/res/drawable-nodpi/hp3000xl_word_processor.png
app/src/main/res/drawable-nodpi/hp3000xl_spreadsheet.png
app/src/main/res/drawable-nodpi/hp3000xl_calendar.png
app/src/main/res/drawable-nodpi/hp3000xl_terminal.png
app/src/main/res/drawable-nodpi/hp3000xl_code_editor.png
app/src/main/res/drawable-nodpi/hp3000xl_calculator.png
app/src/main/res/drawable-nodpi/hp3000xl_system_settings.png
```

The PNGs are visual backgrounds/reference surfaces; interactive controls remain native Compose UI so the APK stays functional on Android 10 and Huawei P20/P20 Pro-class devices.

## Asset package

The generated ZIP asset pack is versioned separately from source and has SHA-256:

`21af533410859b00e4c613f52e7131f09475972b254753b1c4d22e67a8cbcd94`
