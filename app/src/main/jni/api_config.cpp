#include <jni.h>
#include <string>


std::string SERVER_URL = "http://demo.redtvlive.com/oxoo/v13/rest-api/";
std::string API_KEY = "spagreen";
std::string PURCHASE_CODE = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
std::string YOUTUBE_API_KEY = "xxxxxxxxxxxxxxxxxxxx";

extern "C" JNIEXPORT jstring JNICALL
// Change "com_oxoo_spagreen" with your package name. // I.e "com_package_name" // DO NOT CHANGE OTHER THINGS
Java_com_oxoo_spagreen_AppConfig_getApiServerUrl(
        JNIEnv* env,
        jclass clazz) {
    return env->NewStringUTF(SERVER_URL.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
// Change "com_oxoo_spagreen" with your package name. // I.e "com_package_name" // DO NOT CHANGE OTHER THINGS

Java_com_oxoo_spagreen_AppConfig_getApiKey(
        JNIEnv* env,
jclass clazz) {
return env->NewStringUTF(API_KEY.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
// Change "com_oxoo_spagreen" with your package name. // I.e "com_package_name" // DO NOT CHANGE OTHER THINGS

Java_com_oxoo_spagreen_AppConfig_getPurchaseCode(
        JNIEnv* env,
        jclass clazz) {
    return env->NewStringUTF(PURCHASE_CODE.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
// Change "com_oxoo_spagreen" with your package name. // I.e "com_package_name" // DO NOT CHANGE OTHER THINGS

Java_com_oxoo_spagreen_AppConfig_getYouTubeApiKey(
        JNIEnv* env,
        jclass clazz) {
    return env->NewStringUTF(YOUTUBE_API_KEY.c_str());
}
