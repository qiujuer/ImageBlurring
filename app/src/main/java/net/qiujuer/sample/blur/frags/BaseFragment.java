package net.qiujuer.sample.blur.frags;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.sample.blur.R;

/**
 * Created by qiujuer
 * on 16/3/10.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected TextView mText;
    protected TextView mTime;
    protected boolean isScale = true;
    protected Bitmap mBitmap;

    public void setBitmap(Bitmap bitmap) {
        View view = getView();
        if (view != null) {
            mBitmap = Bitmap.createBitmap(view.getWidth(),
                    view.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mBitmap);
            canvas.translate(-view.getLeft(), -view.getTop());
            canvas.scale(1, 1);
            Paint paint = new Paint();
            paint.setFlags(Paint.FILTER_BITMAP_FLAG);
            canvas.drawBitmap(bitmap, 0, 0, paint);

            // Call Blur
            blur();
        }
    }

    protected Bitmap getScaleBitmap(Bitmap bitmap) {
        float scaleFactor = 9;
        float scale = 1f / scaleFactor;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap ret = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return ret;
    }

    protected Bitmap getNewBitmap() {
        return mBitmap.copy(mBitmap.getConfig(), true);
    }

    protected void blur() {
        if (mBitmap == null || mText == null)
            return;
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getNewBitmap();
                int radius = 20;
                if (isScale) {
                    radius = 2;
                    bitmap = getScaleBitmap(bitmap);
                }

                long startTime = System.currentTimeMillis();
                final Bitmap ret = blur(bitmap, radius);
                show(ret, System.currentTimeMillis() - startTime);
            }
        };
        thread.start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void show(final Bitmap bitmap, final long time) {
        final View view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.setBackground(new BitmapDrawable(getResources(), bitmap));
                    show(time);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blur, container, false);
        mText = (TextView) root.findViewById(R.id.text);
        mTime = (TextView) root.findViewById(R.id.time);
        root.setOnClickListener(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mText = null;
        mBitmap.recycle();
        mBitmap = null;
    }

    @Override
    public void onClick(View v) {
        blur();
        isScale = !isScale;
    }

    protected void show(long time) {
        if (mTime != null)
            mTime.setText(time + "ms");
    }

    protected abstract Bitmap blur(Bitmap bitmap, int radius);
}
