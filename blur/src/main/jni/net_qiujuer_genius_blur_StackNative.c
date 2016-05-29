/*
 * Copyright (C) 2014-2016 Qiujuer <qiujuer@live.cn>
 * WebSite http://www.qiujuer.net
 * Created 04/28/2015
 * Changed 05/29/2016
 * Version 2.0.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <android/log.h>
#include <android/bitmap.h>
#include "stackblur.h"

#define TAG "net_qiujuer_genius_blur_StackNative"
#define LOG_D(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)

JNIEXPORT void JNICALL
Java_net_qiujuer_genius_blur_StackNative_blurPixels
        (JNIEnv
         *env,
         jclass obj, jintArray
         arrIn,
         jint w, jint
         h,
         jint r
        ) {
    jint *pixels;
    // cpp:
    // pix = (env)->GetIntArrayElements(arrIn, 0);
    pixels = (*env)->GetIntArrayElements(env, arrIn, 0);
    if (pixels == NULL) {
        LOG_D("Input pixels isn't null.");
        return;
    }

    // Start
    pixels = blur_ARGB_8888(pixels, w, h, r);
    // End

    // if return:
    // int size = w * h;
    // jintArray result = env->NewIntArray(size);
    // env->SetIntArrayRegion(result, 0, size, pix);
    // cpp:
    // (env)->ReleaseIntArrayElements(arrIn, pix, 0);
    (*env)->ReleaseIntArrayElements(env, arrIn, pixels, 0);
    // return result;
}

JNIEXPORT void JNICALL
Java_net_qiujuer_genius_blur_StackNative_blurBitmap
        (JNIEnv
         *env,
         jclass obj, jobject
         bitmapIn,
         jint r
        ) {
    AndroidBitmapInfo infoIn;
    void *pixels;

    // Get image info
    if (AndroidBitmap_getInfo(env, bitmapIn, &infoIn) != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOG_D("AndroidBitmap_getInfo failed!");
        return;
    }

    // Check image
    if (infoIn.format != ANDROID_BITMAP_FORMAT_RGBA_8888 &&
        infoIn.format != ANDROID_BITMAP_FORMAT_RGB_565) {
        LOG_D("Only support ANDROID_BITMAP_FORMAT_RGBA_8888 and ANDROID_BITMAP_FORMAT_RGB_565");
        return;
    }

    // Lock all images
    if (AndroidBitmap_lockPixels(env, bitmapIn, &pixels) != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOG_D("AndroidBitmap_lockPixels failed!");
        return;
    }
    // height width
    int h = infoIn.height;
    int w = infoIn.width;

    // Start
    if (infoIn.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        pixels = blur_ARGB_8888((int *) pixels, w, h, r);
    } else if (infoIn.format == ANDROID_BITMAP_FORMAT_RGB_565) {
        pixels = blur_RGB_565((short *) pixels, w, h, r);
    }

    // End

    // Unlocks everything
    AndroidBitmap_unlockPixels(env, bitmapIn);
}