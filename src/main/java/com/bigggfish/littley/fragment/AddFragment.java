package com.bigggfish.littley.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigggfish.littley.R;


public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PAGE_NUM = 2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View parent;
    private ViewPager vpAdd;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
    /*    Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_add, container, false);
        vpAdd = (ViewPager) parent.findViewById(R.id.vp_add);
        toolbar = (Toolbar) parent.findViewById(R.id.toolbar);
        tabLayout = (TabLayout) parent.findViewById(R.id.tl_add);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        vpAdd.setAdapter(new AddPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpAdd);
        toolbar = (Toolbar) parent.findViewById(R.id.toolbar);
        return parent;
    }
    class AddPagerAdapter extends FragmentPagerAdapter {

        public AddPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return AddIncomeFragment.newInstance("", "");
                case 1:
                    return AddSpendFragment.newInstance("","");

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "添加收入";
                case 1:
                    return "添加支出";
            }
            return null;
        }
    }
}
