# Hardware Abstraction

The project models the original handheld-era device experience while using Android APIs underneath.

## Display

`DisplayModel` describes resolution, density and color mode. The current product is a full-color Android recreation.

## Input

The input model supports touchscreen, keyboard, mouse and stylus concepts. Android's actual input subsystem remains authoritative.

## Audio

`AudioModel` tracks enabled state and normalized volume. Actual audio output is delegated to Android.

## Storage

`StorageModel` represents logical volumes. Android application storage and Storage Access Framework APIs remain responsible for physical storage access.

## Compatibility

Hardware abstractions are intentionally non-destructive and do not attempt to flash, replace or control proprietary device firmware.
