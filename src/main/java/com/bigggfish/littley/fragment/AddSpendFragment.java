package com.bigggfish.littley.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddSpendFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private float toY;
    private float toX;

    private View parentView;
    private EditText etSpendMoney;
    private GridView gvSpendType;
    private EditText etSpendRemarks;
    private ImageView ivAnmimation;
    private ImageView ivSelectedBillType;

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
        gvSpendType.setAdapter(new BillTypeAdapter(this.getActivity(), getData()));
        gvSpendType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_bill_type);
                toX = ivSelectedBillType.getLeft() + ivSelectedBillType.getPaddingLeft();
                toY = ivSelectedBillType.getTop();
                float fromY = gvSpendType.getTop() + gvSpendType.getPaddingTop()
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


    private List<BillType> getData(){
        List<BillType> billTypeList = new ArrayList<>();
        for(int i=0; i<27; i++){
            billTypeList.add(new BillType());
        }
        return billTypeList;
    }
}
