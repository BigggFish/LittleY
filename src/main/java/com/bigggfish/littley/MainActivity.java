package com.bigggfish.littley;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bigggfish.littley.fragment.AddFragment;
import com.bigggfish.littley.fragment.DetailFragment;
import com.bigggfish.littley.fragment.MineFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgMain;
    private ViewPager vpMain;
    private MineFragment mineFragment;
    private DetailFragment detailFragment;
    private AddFragment addFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        rgMain = (RadioGroup) findViewById(R.id.rg_main);
        vpMain = (ViewPager) findViewById(R.id.vp_main);
        ((RadioButton)rgMain.getChildAt(1)).setChecked(true);
        vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        vpMain.setCurrentItem(1);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_detail:
                        vpMain.setCurrentItem(0);
                        break;
                    case R.id.rb_add:
                        vpMain.setCurrentItem(1);
                        break;
                    case R.id.rb_mine:
                        vpMain.setCurrentItem(2);
                        break;

                }
            }
        });

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton)rgMain.getChildAt(position)).setChecked(true);
                switch (position){
                    case 0:
                        Log.e("---->OUT", "detailfragment");
                        if(detailFragment != null){
                            Log.e("---->OUT", "detailfragment != null");
                            detailFragment.updateData();
                        }else{
                            Log.e("---->OUT", "detailfragment = null");
                        }
                        break;
                    case 1:
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                        break;
                    case 2:
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //用于解决ViewPager 和 CoordinatorLayout之间存在的fitSystemWindows属性无效bug。
        ViewCompat.setOnApplyWindowInsetsListener(vpMain,
                new OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View v,
                                                                  WindowInsetsCompat insets) {
                        insets = ViewCompat.onApplyWindowInsets(v, insets);
                        if (insets.isConsumed()) {
                            return insets;
                        }
                        boolean consumed = false;
                        for (int i = 0, count = vpMain.getChildCount(); i < count; i++) {
                            ViewCompat.dispatchApplyWindowInsets(vpMain.getChildAt(i), insets);
                            if (insets.isConsumed()) {
                                consumed = true;
                            }
                        }
                        return consumed ? insets.consumeSystemWindowInsets() : insets;
                    }
                });

    }
    class MainPagerAdapter extends FragmentPagerAdapter{

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    detailFragment = DetailFragment.newInstance();
                    return detailFragment;
                case 1:
                    addFragment = AddFragment.newInstance();
                    return addFragment;
                case 2:
                    mineFragment = MineFragment.newInstance("", "");
                    return mineFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 202 && mineFragment != null){
            mineFragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
