package net.qiujuer.imageblurring;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import net.qiujuer.imageblurring.fragments.AnimatorBlurFragment;
import net.qiujuer.imageblurring.fragments.FastBlurFragment;
import net.qiujuer.imageblurring.fragments.JniBlurArrayFragment;
import net.qiujuer.imageblurring.fragments.JniBlurBitMapFragment;
import net.qiujuer.imageblurring.util.ZoomOutPageTransformer;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

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
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            getActionBar().addTab(
                    getActionBar().newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(tabListener)
            );
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

