package com.bigggfish.littley.fragment;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.bigggfish.littley.dao.BillType;
import com.bigggfish.littley.model.DBManager;
import com.bigggfish.littley.util.AnimatorTools;
import com.bigggfish.littley.util.Constant;

import java.util.List;

public class AddSpendFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

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

    private DBManager dbManager;
    private List<BillType> billTypeList;
    public AddSpendFragment() {
        // Required empty public constructor
    }

    public static AddSpendFragment newInstance(String param1, String param2) {
        AddSpendFragment fragment = new AddSpendFragment();
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

        gvSpendType.setAdapter(new BillTypeAdapter(this.getActivity(), getData(), true));
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

    private List<BillType> getData(){
        dbManager = new DBManager(this.getActivity());
        billTypeList = dbManager.querySpendBillType();
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
                if(dbManager ==null){
                    dbManager = new DBManager(getActivity());
                }
                long rowId = dbManager.insertBill(Integer.valueOf(etSpendMoney.getText().toString())
                        , currentTime, billTime, true, nowBillTypeId, remark);
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
