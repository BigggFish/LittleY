package com.bigggfish.littley.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigggfish.littley.R;
import com.bigggfish.littley.ui.base.BaseFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFragment extends BaseFragment{

    private static final int PAGE_NUM = 2;
    private int year = 0;
    private int month = 0;
    private int day = 0;

    @BindView(R.id.vp_add)
    ViewPager vpAdd;
    @BindView(R.id.tl_add)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fabt_submit)
    FloatingActionButton fabtSubmit;
    @BindView(R.id.tv_calendar)
    TextView tvCalendar;

    private Calendar calendar;

    private OnIncomeFabtClickListener onIncomeFabtClickListener;
    private OnSpendFabtClickListener onSpendFabtClickListener;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_add;
    }

    @Override
    protected void initView(View rootView) {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        tvCalendar.setText(year % 100 + "-" +
                (month > 9 ? month : "0" + month) + "-" +
                (day > 9 ? day : "0" + day));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        vpAdd.setAdapter(new AddPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(vpAdd);
    }


    @OnClick(R.id.fabt_submit)
    protected void submit(){
        if (vpAdd.getCurrentItem() == 0) {
            if (onSpendFabtClickListener != null) {
                onSpendFabtClickListener.onFabtClick(year, month, day);
            }
        } else {
            if (onIncomeFabtClickListener != null) {
                onIncomeFabtClickListener.onFabtClick(year, month, day);
            }
        }
    }


/*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabt_submit:
                if (vpAdd.getCurrentItem() == 0) {
                    if (onSpendFabtClickListener != null) {
                        onSpendFabtClickListener.onFabtClick(year, month, day);
                    }
                } else {
                    if (onIncomeFabtClickListener != null) {
                        onIncomeFabtClickListener.onFabtClick(year, month, day);
                    }
                }
                break;
        }
    }
*/

    public void setOnIncomeFabtClickListener(OnIncomeFabtClickListener onIncomeFabtClickListener) {
        this.onIncomeFabtClickListener = onIncomeFabtClickListener;
    }

    public void setOnSpendFabtClickListener(OnSpendFabtClickListener onSpendFabtClickListener) {
        this.onSpendFabtClickListener = onSpendFabtClickListener;
    }

    public interface OnIncomeFabtClickListener {
        void onFabtClick(int year, int month, int day);//留出选择时间的接口
    }

    public interface OnSpendFabtClickListener {
        void onFabtClick(int year, int month, int day);//留出选择时间的接口
    }

    class AddPagerAdapter extends FragmentPagerAdapter {

        public AddPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AddSpendFragment.newInstance();
                case 1:
                    return AddIncomeFragment.newInstance();

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "添加支出";
                case 1:
                    return "添加收入";
            }
            return null;
        }
    }
}
