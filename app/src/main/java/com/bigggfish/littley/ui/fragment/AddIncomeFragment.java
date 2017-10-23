package com.bigggfish.littley.ui.fragment;


import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigggfish.littley.ui.adapter.BillTypeAdapter;
import com.bigggfish.littley.R;
import com.bigggfish.littley.model.BillRepository;
import com.bigggfish.littley.model.BillTypeRepository;
import com.bigggfish.littley.model.dao.BillItem;
import com.bigggfish.littley.model.dao.BillType;
import com.bigggfish.littley.ui.base.BaseFragment;
import com.bigggfish.littley.util.AnimatorTools;
import com.bigggfish.littley.util.Constant;

import java.util.List;

import butterknife.BindView;


public class AddIncomeFragment extends BaseFragment {

    private float toY;
    private float toX;
    private int nowBillTypeId = 1;

    @BindView(R.id.et_income_money)
    EditText etIncomeMoney;
    @BindView(R.id.gv_income_type)
    GridView gvIncomeType;
    @BindView(R.id.et_income_remarks)
    EditText etIncomeRemarks;
    @BindView(R.id.iv_selected_bill_type)
    ImageView ivSelectedBillType;
    @BindView(R.id.iv_animation)
    ImageView ivAnimation;
    @BindView(R.id.tv_selected_bill_type)
    TextView tvSelectedBillType;

    private List<BillType> billTypeList;

    private BillTypeRepository mBillTypeRepository;
    private BillRepository mBillRepository;

    public AddIncomeFragment() {
        // Required empty public constructor
    }

    public static AddIncomeFragment newInstance() {
        AddIncomeFragment fragment = new AddIncomeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_add_income;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBillTypeRepository = BillTypeRepository.getInstance(getActivity());
        mBillRepository = BillRepository.getInstance(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        ((AddFragment) getParentFragment()).setOnIncomeFabtClickListener(new OnFabtClickListener());
        super.onAttach(context);
    }

    @Override
    protected void initView(View view) {
        gvIncomeType.setAdapter(new BillTypeAdapter(this.getActivity(), getBillTypeData(), false));
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
                nowBillTypeId = i + 14;
                ivAnimation.setVisibility(View.VISIBLE);
                ivAnimation.setImageResource(Constant.TYPE_IMAGES_ID[14 + j]);
                AnimatorTools.startTranslationAnimator(ivAnimation, fromX, fromY, toX
                        , toY, 300, new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                ivSelectedBillType.setImageResource(Constant.TYPE_IMAGES_ID[14 + j]);
                                ivAnimation.setVisibility(View.GONE);
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

    private List<BillType> getBillTypeData() {
        billTypeList = mBillTypeRepository.getIncomeBillTypeList();
        return billTypeList;
    }

    private class OnFabtClickListener implements AddFragment.OnIncomeFabtClickListener {

        @Override
        public void onFabtClick(int year, int month, int day) {

            int billTime = day + month * 100 + year * 10000;
            Toast.makeText(getActivity(), "income_fabt_click" + billTime, Toast.LENGTH_SHORT).show();
            if (!TextUtils.isEmpty(etIncomeMoney.getText().toString())) {
                long currentTime = System.currentTimeMillis();//暂时只使用现在的时间
                String remark = etIncomeRemarks.getText().toString();//说明
                int amount = Integer.valueOf(etIncomeMoney.getText().toString());//输入金额
                BillItem billItem = new BillItem();
                billItem.setAmount(amount);
                billItem.setTimeStamp(currentTime);
                billItem.setBillTime(billTime);
                billItem.setSpend(false);
                billItem.setBillTypeId(nowBillTypeId);
                billItem.setBillRemark(remark);
                if (!mBillRepository.insertBill(billItem)) {
                    Toast.makeText(getActivity(), R.string.insert_failed, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), R.string.money_amount_cant_zero, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

