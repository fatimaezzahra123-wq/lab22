#include <jni.h>
#include <string>
#include <algorithm>
#include <climits>
#include <android/log.h>

#define LOG_TAG "JNI_DEMO"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// 🔹 Hello
extern "C"
JNIEXPORT jstring JNICALL
Java_projet_ensa_ma_app_lab22_1devel_MainActivity_helloFromJNI(
        JNIEnv* env,
        jobject) {

    return env->NewStringUTF("Hello from C++ via JNI !");
}

// 🔹 Factorial
extern "C"
JNIEXPORT jint JNICALL
Java_projet_ensa_ma_app_lab22_1devel_MainActivity_factorial(
        JNIEnv* env,
        jobject,
        jint n) {

    if (n < 0) return -1;

    long long fact = 1;
    for (int i = 1; i <= n; i++) {
        fact *= i;
        if (fact > INT_MAX) return -2;
    }

    return static_cast<jint>(fact);
}

// 🔹 Reverse String
extern "C"
JNIEXPORT jstring JNICALL
Java_projet_ensa_ma_app_lab22_1devel_MainActivity_reverseString(
        JNIEnv* env,
        jobject,
        jstring javaString) {

    if (javaString == nullptr)
        return env->NewStringUTF("");

    const char* chars = env->GetStringUTFChars(javaString, nullptr);
    std::string s(chars);
    env->ReleaseStringUTFChars(javaString, chars);

    std::reverse(s.begin(), s.end());

    return env->NewStringUTF(s.c_str());
}

// 🔹 Sum Array
extern "C"
JNIEXPORT jint JNICALL
Java_projet_ensa_ma_app_lab22_1devel_MainActivity_sumArray(
        JNIEnv* env,
        jobject,
        jintArray array) {

    if (array == nullptr) return 0;

    jsize len = env->GetArrayLength(array);
    jint* elements = env->GetIntArrayElements(array, nullptr);

    long long sum = 0;
    for (jsize i = 0; i < len; i++) {
        sum += elements[i];
    }

    env->ReleaseIntArrayElements(array, elements, 0);

    if (sum > INT_MAX) return -3;

    return static_cast<jint>(sum);
}

// 🔥 Benchmark C++
extern "C"
JNIEXPORT jlong JNICALL
Java_projet_ensa_ma_app_lab22_1devel_MainActivity_nativeSum(
        JNIEnv* env,
        jobject,
        jint n) {

    long long sum = 0;
    for (int i = 0; i < n; i++) {
        sum += i;
    }

    return sum;
}