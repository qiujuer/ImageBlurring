package net.qiujuer.sample.blur.frags;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.qiujuer.genius.blur.StackBlur;
import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.sample.blur.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimFragment extends BaseFragment {
    private int mLeft;
    private int mWidth;
    private AnimatorSet mSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blur_anim, container, false);
        mText = (TextView) root.findViewById(R.id.text);
        root.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (mSet != null && mSet.isRunning())
            return;
        animView();
    }

    @Override
    protected void blur() {
        if (mBitmap == null || mWidth == 0 || mText == null)
            return;

        Bitmap bitmap = cropTextBitmap(mBitmap);
        bitmap = getScaleBitmap(bitmap);
        long startTime = System.currentTimeMillis();
        final Bitmap ret = blur(bitmap, 2);
        show(ret, System.currentTimeMillis() - startTime);
    }

    @Override
    protected Bitmap blur(Bitmap bitmap, int radius) {
        return StackBlur.blurNatively(bitmap, radius, true);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void show(final Bitmap bitmap, final long time) {
        if (mText != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    if (mText != null)
                        mText.setBackground(new BitmapDrawable(getResources(), bitmap));
                }
            });
        }
    }

    public Bitmap cropTextBitmap(Bitmap bitmap) {
        int h = bitmap.getHeight();
        return Bitmap.createBitmap(bitmap, mLeft, 0, mWidth, h, null, false);
    }

    @Override
    public void onDestroyView() {
        // try cancel it
        if (mSet != null)
            mSet.cancel();
        super.onDestroyView();
    }

    public void animView() {
        if (mSet == null) {
            View root = getView();
            if (root == null)
                return;

            mWidth = mText.getWidth();
            final ValueAnimator topAnim = ObjectAnimator.ofInt(mText, "left", 0, root.getRight() - mWidth);
            topAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mLeft = (int) animation.getAnimatedValue();
                    blur();
                }
            });
            topAnim.setRepeatMode(ValueAnimator.REVERSE);
            topAnim.setRepeatCount(9);

            final ValueAnimator bottomAnim = ObjectAnimator.ofInt(mText, "right", mWidth, root.getRight());
            bottomAnim.setRepeatMode(ValueAnimator.REVERSE);
            bottomAnim.setRepeatCount(9);

            AnimatorSet set = new AnimatorSet();
            set.play(topAnim).with(bottomAnim);
            set.setDuration(2800);
            set.start();
            mSet = set;
        } else {
            mSet.start();
        }

    }
}
