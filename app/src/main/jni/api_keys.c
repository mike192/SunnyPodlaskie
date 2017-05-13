#include <jni.h>

JNIEXPORT jstring JNICALL
Java_pl_mosenko_sunnypodlaskie_util_APIKeyProvider_getEncodedAPIKey(JNIEnv *env, jobject instance) {
    return (*env) -> NewStringUTF(env, "ODYzODNlZDEzOWRjNGRjZTRhMWViODIxZmNjNWE0YmE=");
}

