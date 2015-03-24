package net.qiujuer.imageblurring.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import net.qiujuer.imageblurring.R;
import net.qiujuer.imageblurring.util.FastBlur;


/**
 * Created by QIUJUER
 * on 2015/3/24.
 */
public class AnimatorBlurFragment extends Fragment {
    private ImageView image;
    private TextView text;
    private Bitmap bitmap = null;
    private int animP = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        image = (ImageView) view.findViewById(R.id.picture);
        text = (TextView) view.findViewById(R.id.text);
        image.setImageResource(R.drawable.picture);
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                image.buildDrawingCache();
                bitmap = image.getDrawingCache();
                blur(bitmap);
                return true;
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animP = -animP;
                animView(animP);
            }
        });

        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void blur(Bitmap bkg) {
        float scaleFactor = 6;
        float radius = 3;

        Bitmap overlay = Bitmap.createBitmap((int) (text.getMeasuredWidth() / scaleFactor),
                (int) (text.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-text.getLeft() / scaleFactor, -text.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlurJniArray(overlay, (int) radius, true);

        text.setBackground(new BitmapDrawable(getResources(), overlay));
    }

    public void animView(final int p) {
        final ValueAnimator topAnim = ObjectAnimator.ofInt(text, "top", text.getTop(), text.getTop() + p);
        topAnim.setDuration(2000);
        topAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (bitmap != null)
                    blur(bitmap);
            }
        });


        final ValueAnimator bottomAnim = ObjectAnimator.ofInt(text, "bottom", text.getBottom(), text.getBottom() + p);
        bottomAnim.setDuration(2000);

        topAnim.start();
        bottomAnim.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bitmap = null;
        image = null;
        text = null;
    }

    @Override
    public String toString() {
        return "Animator";
    }
}
