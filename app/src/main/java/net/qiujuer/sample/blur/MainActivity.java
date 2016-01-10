package net.qiujuer.sample.blur;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import net.qiujuer.sample.blur.fragments.AnimatorBlurFragment;
import net.qiujuer.sample.blur.fragments.FastBlurFragment;
import net.qiujuer.sample.blur.fragments.JniBlurArrayFragment;
import net.qiujuer.sample.blur.fragments.JniBlurBitMapFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CustomPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter =
                new CustomPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
    }

    public class CustomPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
            // 使用这种方式只能在Android 4.4 及其以后版本才能使用
            // This should sdk >= 4.4
            // fragments.add(Fragment.instantiate(MainActivity.this, RSBlurFragment.class.getName()));
            fragments.add(Fragment.instantiate(MainActivity.this, FastBlurFragment.class.getName()));
            fragments.add(Fragment.instantiate(MainActivity.this, JniBlurArrayFragment.class.getName()));
            fragments.add(Fragment.instantiate(MainActivity.this, JniBlurBitMapFragment.class.getName()));
            fragments.add(Fragment.instantiate(MainActivity.this, AnimatorBlurFragment.class.getName()));
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).toString();
        }
    }
}
