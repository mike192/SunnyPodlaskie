#include <jni.h>

JNIEXPORT jstring JNICALL
Java_pl_mosenko_sunnypodlaskie_util_WeatherApiKeyProvider_getEncodedAPIKey(JNIEnv *env,
                                                                           __unused jobject instance) {
    return (*env)->NewStringUTF(env, "ODYzODNlZDEzOWRjNGRjZTRhMWViODIxZmNjNWE0YmE=");
}

