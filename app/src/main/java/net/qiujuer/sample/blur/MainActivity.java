package net.qiujuer.sample.blur;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import net.qiujuer.sample.blur.frags.BaseFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BaseFragment mJniPixel;
    BaseFragment mJniBitmap;
    BaseFragment mJava;
    BaseFragment mRS;
    BaseFragment mAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findFragments();

        findViewById(R.id.btn_opt).setOnClickListener(this);
    }

    private void findFragments() {
        FragmentManager manager = getSupportFragmentManager();
        mJniPixel = (BaseFragment) manager.findFragmentById(R.id.frag_jni_pixel);
        mJniBitmap = (BaseFragment) manager.findFragmentById(R.id.frag_jni_bitmap);
        mJava = (BaseFragment) manager.findFragmentById(R.id.frag_java);
        mRS = (BaseFragment) manager.findFragmentById(R.id.frag_rs);
        mAnim = (BaseFragment) manager.findFragmentById(R.id.frag_anim);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.frag_rs).setVisibility(View.GONE);
        }
    }

    private void start() {
        final View root = findViewById(R.id.root);
        root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                root.getViewTreeObserver().removeOnPreDrawListener(this);
                root.buildDrawingCache();
                Bitmap bmp = root.getDrawingCache();
                setFragmentsBitmap(bmp);
                return true;
            }
        });
    }

    private void setFragmentsBitmap(final Bitmap bmp) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                mJniPixel.setBitmap(bmp);
                mJniBitmap.setBitmap(bmp);
                mJava.setBitmap(bmp);
                mAnim.setBitmap(bmp);

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    mRS.setBitmap(bmp);
            }
        };
        thread.start();
    }

    @Override
    public void onClick(View v) {
        start();
        v.setVisibility(View.GONE);
    }
}
