#include "simh_android_bridge.h"

#include <jni.h>

namespace {
constexpr const char* kBridgeVersion = "0.1.0-scaffold";
constexpr const char* kEngineNotLinked =
    "SIMH engine is not linked; no guest machine can be started.";
}

extern "C" const char* simh_android_bridge_version(void) {
    return kBridgeVersion;
}

extern "C" SimhBridgeStatus simh_android_engine_status(void) {
    return SIMH_BRIDGE_ENGINE_NOT_LINKED;
}

extern "C" JNIEXPORT jstring JNICALL
Java_io_arkansasio_hp3000xl_emulator_SimhNativeBridge_nativeBridgeVersion(
    JNIEnv* env, jclass /* clazz */) {
    return env->NewStringUTF(simh_android_bridge_version());
}

extern "C" JNIEXPORT jint JNICALL
Java_io_arkansasio_hp3000xl_emulator_SimhNativeBridge_nativeEngineStatus(
    JNIEnv* /* env */, jclass /* clazz */) {
    return static_cast<jint>(simh_android_engine_status());
}

extern "C" JNIEXPORT jstring JNICALL
Java_io_arkansasio_hp3000xl_emulator_SimhNativeBridge_nativeEngineStatusMessage(
    JNIEnv* env, jclass /* clazz */) {
    return env->NewStringUTF(kEngineNotLinked);
}
