package com.bigggfish.littley.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bigggfish.littley.Adapter.BillTypeAdapter;
import com.bigggfish.littley.R;
import com.bigggfish.littley.dao.BillType;
import com.bigggfish.littley.model.DBManager;
import com.bigggfish.littley.util.AnimatorTools;
import com.bigggfish.littley.util.Constant;

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
    private int nowBillTypeId = 1;

    private EditText etIncomeMoney;
    private GridView gvIncomeType;
    private EditText etIncomeRemarks;
    private ImageView ivSelectedBillType;
    private ImageView ivAnmimation;
    private TextView tvSelectedBillType;
    private View parent;

    private DBManager dbManager;
    private List<BillType> billTypeList;

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

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        ((AddFragment)getParentFragment()).setOnIncomeFabtClickListener(new OnFabtClickListener());
        super.onAttach(context);
    }

    private void initView() {
        etIncomeMoney = (EditText) parent.findViewById(R.id.et_income_money);
        etIncomeRemarks = (EditText) parent.findViewById(R.id.et_income_remarks);
        gvIncomeType = (GridView) parent.findViewById(R.id.gv_income_type);
        ivSelectedBillType = (ImageView) parent.findViewById(R.id.iv_selected_bill_type);
        ivAnmimation = (ImageView) parent.findViewById(R.id.iv_animation);
        tvSelectedBillType = (TextView) parent.findViewById(R.id.tv_selected_bill_type);

        gvIncomeType.setAdapter(new BillTypeAdapter(this.getActivity(), getData(), false));
        gvIncomeType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int j = i;
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_bill_type);
                toX = ivSelectedBillType.getLeft() + ivSelectedBillType.getPaddingLeft();
                toY = ivSelectedBillType.getTop();
                float fromY = gvIncomeType.getTop() + gvIncomeType.getPaddingTop()
                        + view.getHeight() * (i / 5) + imageView.getTop();
                float fromX = view.getWidth() * (i % 5) + imageView.getLeft();
                nowBillTypeId = i + 1;
                ivAnmimation.setVisibility(View.VISIBLE);
                ivAnmimation.setImageResource(Constant.TYPE_IMAGES_ID[ 14 + j]);
                AnimatorTools.startTranslationAnimator(ivAnmimation, fromX, fromY, toX
                        , toY, 300, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ivSelectedBillType.setImageResource(Constant.TYPE_IMAGES_ID[ 14 + j]);
                        ivAnmimation.setVisibility(View.GONE);
                        tvSelectedBillType.setText(billTypeList.get(j).getTitle());
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
        dbManager = new DBManager(getActivity());
        billTypeList = dbManager.queryIncomeBillType();
        return billTypeList;
    }
    class OnFabtClickListener implements AddFragment.OnIncomeFabtClickListener {

        @Override
        public void onFabtClick(int year, int month, int day) {

            int billTime = day + month * 100 + year * 10000;
            Toast.makeText(getActivity(), "income_fabt_click" + billTime, Toast.LENGTH_SHORT).show();
            if(!TextUtils.isEmpty(etIncomeMoney.getText().toString())){
                long currentTime = System.currentTimeMillis();//暂时只使用现在的时间
                String remark = etIncomeRemarks.getText().toString();
                if(dbManager ==null){
                    dbManager = new DBManager(getActivity());
                }
                long rowId = dbManager.insertBill(Integer.valueOf(etIncomeMoney.getText().toString())
                        , currentTime, billTime, false, nowBillTypeId, remark);
                if(rowId == -1){
                    Toast.makeText(getActivity(), "插入数据失败", Toast.LENGTH_SHORT).show();
                }
                Log.e("----OUT", "插入数据行号" + rowId);
            }else {
                Toast.makeText(getActivity(), "金额不能为0", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

