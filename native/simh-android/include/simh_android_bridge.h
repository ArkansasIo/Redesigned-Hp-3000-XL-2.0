#pragma once

#ifdef __cplusplus
extern "C" {
#endif

typedef enum SimhBridgeStatus {
    SIMH_BRIDGE_OK = 0,
    SIMH_BRIDGE_ENGINE_NOT_LINKED = 1,
    SIMH_BRIDGE_INVALID_ARGUMENT = 2,
    SIMH_BRIDGE_NOT_RUNNING = 3,
    SIMH_BRIDGE_INTERNAL_ERROR = 4
} SimhBridgeStatus;

/* The current scaffold is intentionally unavailable until the actual
 * upstream SIMH core and HP3000 machine implementation are linked. */
const char* simh_android_bridge_version(void);
SimhBridgeStatus simh_android_engine_status(void);

#ifdef __cplusplus
}
#endif
