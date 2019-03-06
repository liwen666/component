#include "com_temp_common_jni_MyJniMethod.h"
//#define LOG_TAG "Android-T"
//#undef LOG
//#include <android/log.h>
//#define LOGD(a) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,a)
//#define LOGD2(a,b) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,a,b)
// #define DBUG

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

JNIEXPORT void JNICALL Java_com_lin_myjin_MyJniMethod_add
(JNIEnv * env, jobject obj, jint count)
{

}
/*
 * Class: com_lin_myjin_MyJniMethod
 * Method: print
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_lin_myjin_MyJniMethod_print
(JNIEnv * env, jobject obj, jint count)
{

}
/*
 * Class: com_lin_myjin_MyJniMethod
 * Method: getadd
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_temp_common_jni_MyJniMethod_getadd
(JNIEnv * env, jobject obj, jint count)
{
return count+8;

}