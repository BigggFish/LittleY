package com.bigggfish.littley.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.bigggfish.littley.Adapter.BillTypeAdapter;
import com.bigggfish.littley.R;
import com.bigggfish.littley.dao.BillType;
import com.bigggfish.littley.util.AnimatorTools;

import java.util.ArrayList;
import java.util.List;

public class AddIncomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PAGE_NUM = 2;

    private String mParam1;
    private String mParam2;
    private float toY;
    private float toX;

    private EditText etIncomeMoney;
    private GridView gvIncomeType;
    private EditText etIncomeRemarks;
    private ImageView ivSelectedBillType;
    private ImageView ivAnmimation;

    private View parent;

    public AddIncomeFragment() {
        // Required empty public constructor
    }

    public static AddIncomeFragment newInstance(String param1, String param2) {
        AddIncomeFragment fragment = new AddIncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        parent = inflater.inflate(R.layout.fragment_add_income, container, false);
        initView();
        return parent;
    }

    private void initView() {
        etIncomeMoney = (EditText) parent.findViewById(R.id.et_income_money);
        etIncomeRemarks = (EditText) parent.findViewById(R.id.et_income_remarks);
        gvIncomeType = (GridView) parent.findViewById(R.id.gv_income_type);
        ivSelectedBillType = (ImageView) parent.findViewById(R.id.iv_selected_bill_type);
        ivAnmimation = (ImageView) parent.findViewById(R.id.iv_animation);
        gvIncomeType.setAdapter(new BillTypeAdapter(this.getActivity(), getData()));
        gvIncomeType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_bill_type);
                toX = ivSelectedBillType.getLeft() + ivSelectedBillType.getPaddingLeft();
                toY = ivSelectedBillType.getTop();
                float fromY = gvIncomeType.getTop() + gvIncomeType.getPaddingTop()
                        + view.getHeight() * (i / 6) + imageView.getTop();
                float fromX = view.getWidth() * (i % 6) + imageView.getLeft();
                ivAnmimation.setVisibility(View.VISIBLE);
                ivAnmimation.setImageResource(R.drawable.ic_launcher);
                AnimatorTools.startTranslationAnimator(ivAnmimation, fromX, fromY, toX
                        , toY, 300, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ivSelectedBillType.setImageResource(R.drawable.ic_launcher);
                        ivAnmimation.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }
    private List<BillType> getData() {
        List<BillType> billTypeList = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            billTypeList.add(new BillType());
        }
        return billTypeList;
    }
}

