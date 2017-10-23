package com.bigggfish.littley.ui.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigggfish.bottomnavigationbar.BottomNavigationBar;
import com.bigggfish.bottomnavigationbar.NavigationBarItem;
import com.bigggfish.littley.R;

public class Main3Activity extends AppCompatActivity {


    private int[] mCheckBgColors = new int[]{
             R.color.indigo_500
            , R.color.blue_500
            , R.color.pink_500
            , R.color.green_500
            , R.color.light_green_500
            , R.color.orange_500
            , R.color.deep_orange_500};

    private BottomNavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mNavigationBar = (BottomNavigationBar) findViewById(R.id.navigation_bar);

        for (int i = 0; i < 3; i++) {
            NavigationBarItem item = new NavigationBarItem(this);
            if (i > mCheckBgColors.length - 1) {
                item.setBgColor(getResources().getColor(mCheckBgColors[mCheckBgColors.length - 1]));
            } else {
                item.setBgColor(getResources().getColor(mCheckBgColors[i]));
            }
            if (i == 0) {
                item.setText("流水");
                item.setCheckedDrawable(getResources().getDrawable(R.drawable.ic_detail_white));
                //item.setUnCheckedDrawable(getResources().getDrawable(R.drawable.detail_normal));
            } else if (i == 1) {
                item.setText("添加");
                item.setCheckedDrawable(getResources().getDrawable(R.drawable.ic_add_white));
                //item.setUnCheckedDrawable(getResources().getDrawable(R.drawable.add_normal));
            } else if (i == 2) {
                item.setText("我的");
                item.setCheckedDrawable(getResources().getDrawable(R.drawable.ic_mine_white));
                //item.setUnCheckedDrawable(getResources().getDrawable(R.drawable.mine_normal));
            }else {
                item.setText("我的");
                item.setCheckedDrawable(getResources().getDrawable(R.drawable.ic_mine_white));
            }
            mNavigationBar.addBottomNavigation(item);
        }
        mNavigationBar.notifyDataChange();
    }
}
