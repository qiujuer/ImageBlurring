package com.accumulation.imageblurring.app.jni;

import android.graphics.Bitmap;

/**
 * Created by QIUJUER on 2014/4/19.
 */
public class ImageBlur {
    public static native int[] blurIntArray(int[] pImg, int w, int h, int r);
    public static native int[] blurBitMap(Bitmap bitmap,int r);

    static {
        System.loadLibrary("JNI_ImageBlur");
    }
}
