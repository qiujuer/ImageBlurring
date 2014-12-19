package net.qiujuer.imageblurring.jni;

import android.graphics.Bitmap;

/**
 * Created by Qiujuer
 * on 2014/4/19.
 */
public class ImageBlur {
    public static native void blurIntArray(int[] pImg, int w, int h, int r);

    public static native void blurBitMap(Bitmap bitmap, int r);

    static {
        System.loadLibrary("ImageBlur");
    }
}
