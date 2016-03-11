package net.qiujuer.sample.blur.frags;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RSFragment extends BaseFragment {

    @Override
    protected void show(long time) {
        super.show(time);
        mText.setText("RenderScript");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected Bitmap blur(Bitmap bitmap, int radius) {
        RenderScript rs = RenderScript.create(getActivity());
        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);

        overlayAlloc.copyTo(bitmap);
        rs.destroy();
        return bitmap;
    }
}
