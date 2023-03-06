#include <jni.h>JNIEXPORT jstring JNICALL


JNIEXPORT jstring JNICALL
Java_com_mabrouk_core_network_KeysKt_getApiKey(JNIEnv *env, jclass clazz) {
 return (*env)-> NewStringUTF(env,"0d4c62d88469205dc1d674b39137a1db779a2153cbfc1f0d0d3d2a773946522e");
}

JNIEXPORT jstring JNICALL
Java_com_mabrouk_core_network_KeysKt_getBaseUrl(JNIEnv *env, jclass clazz) {
 return (*env)-> NewStringUTF(env,"https://apiv2.allsportsapi.com/");
}