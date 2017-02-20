package com.bigggfish.littley.fragment;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigggfish.littley.adapter.BillTypeAdapter;
import com.bigggfish.littley.R;
import com.bigggfish.littley.model.BillRepository;
import com.bigggfish.littley.model.BillTypeRepository;
import com.bigggfish.littley.model.dao.BillItem;
import com.bigggfish.littley.model.dao.BillType;
import com.bigggfish.littley.util.AnimatorTools;
import com.bigggfish.littley.util.Constant;

import java.util.List;

public class AddSpendFragment extends Fragment {

    private float toY;
    private float toX;
    private int nowBillTypeId = 1;//现在的类别ID

    private View parentView;
    private EditText etSpendMoney;
    private GridView gvSpendType;
    private EditText etSpendRemarks;
    private ImageView ivAnmimation;
    private ImageView ivSelectedBillType;
    private TextView tvSelectedBillType;

    private List<BillType> billTypeList;

    private BillTypeRepository mBillTypeRepository;
    private BillRepository mBillRepository;
    public AddSpendFragment() {
        // Required empty public constructor
    }

    public static AddSpendFragment newInstance() {
        AddSpendFragment fragment = new AddSpendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBillTypeRepository = BillTypeRepository.getInstance(getActivity());
        mBillRepository = BillRepository.getInstance(getActivity());
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    @Override
    public void onAttach(Context context) {
        ((AddFragment)getParentFragment()).setOnSpendFabtClickListener(new OnFabtClickListener());
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_add_spend, container, false);
        initView();
        return parentView;
    }

    private void initView(){
        etSpendMoney = (EditText) parentView.findViewById(R.id.et_spend_money);
        etSpendRemarks = (EditText) parentView.findViewById(R.id.et_spend_remarks);
        ivSelectedBillType = (ImageView) parentView.findViewById(R.id.iv_selected_bill_type);
        ivAnmimation = (ImageView) parentView.findViewById(R.id.iv_animation);
        gvSpendType = (GridView) parentView.findViewById(R.id.gv_spend_type);
        tvSelectedBillType = (TextView) parentView.findViewById(R.id.tv_selected_bill_type);

        gvSpendType.setAdapter(new BillTypeAdapter(this.getActivity(), getSpendBillTypeList(), true));
        gvSpendType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int j = i;
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_bill_type);
                toX = ivSelectedBillType.getLeft() + ivSelectedBillType.getPaddingLeft();
                toY = ivSelectedBillType.getTop();
                float fromY = gvSpendType.getTop() + gvSpendType.getPaddingTop()
                        + view.getHeight() * (i / 5) + imageView.getTop();
                float fromX = view.getWidth() * (i % 5) + imageView.getLeft();
                ivAnmimation.setVisibility(View.VISIBLE);
                ivAnmimation.setImageResource(Constant.TYPE_IMAGES_ID[j]);
                nowBillTypeId = i + 1;
                AnimatorTools.startTranslationAnimator(ivAnmimation, fromX, fromY, toX
                        , toY, 300, new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                ivSelectedBillType.setImageResource(Constant.TYPE_IMAGES_ID[j]);
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

    private List<BillType> getSpendBillTypeList(){
        billTypeList = mBillTypeRepository.getSpendBillTypeList();
        return billTypeList;
    }
    class OnFabtClickListener implements AddFragment.OnSpendFabtClickListener {

        @Override
        public void onFabtClick(int year, int month, int day) {
            int billTime = day + month * 100 + year * 10000;
            Toast.makeText(getActivity(), "income_fabt_click" + billTime, Toast.LENGTH_SHORT).show();
            if(!TextUtils.isEmpty(etSpendMoney.getText().toString())){
                long currentTime = System.currentTimeMillis();//暂时只使用现在的时间
                String remark = etSpendRemarks.getText().toString();
                int amount = Integer.valueOf(etSpendMoney.getText().toString());//输入金额
                BillItem billItem = new BillItem();
                billItem.setAmount(amount);
                billItem.setTimeStamp(currentTime);
                billItem.setBillTime(billTime);
                billItem.setSpend(false);
                billItem.setBillTypeId(nowBillTypeId);
                billItem.setBillRemark(remark);
                if(!mBillRepository.insertBill(billItem)){
                    Toast.makeText(getActivity(), R.string.insert_failed, Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(), R.string.money_amount_cant_zero, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
