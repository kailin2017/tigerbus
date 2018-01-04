#include <jni.h>
#include <string>

using namespace std;

extern "C" {
JNIEXPORT jstring JNICALL Java_com_tiger_util_NativeUtility_getS32Key(JNIEnv *env);
JNIEXPORT jstring JNICALL Java_com_tiger_util_NativeUtility_getS24Key(JNIEnv *env);
JNIEXPORT jstring JNICALL Java_com_tiger_util_NativeUtility_getS16Key(JNIEnv *env);
JNIEXPORT jstring JNICALL Java_com_tiger_util_NativeUtility_getS8Key(JNIEnv *env);
}

JNIEXPORT jstring JNICALL
Java_com_tiger_util_NativeUtility_getS32Key(JNIEnv *env) {
    std::string key = "12345678123456781234567812345678";
    return env->NewStringUTF(key.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_tiger_util_NativeUtility_getS24Key(JNIEnv *env) {
    std::string key = "123456781234567812345678";
    return env->NewStringUTF(key.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_tiger_util_NativeUtility_getS16Key(JNIEnv *env) {
    std::string key = "1234567812345678";
    return env->NewStringUTF(key.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_tiger_util_NativeUtility_getS8Key(JNIEnv *env) {
    std::string key = "12345678";
    return env->NewStringUTF(key.c_str());
}
