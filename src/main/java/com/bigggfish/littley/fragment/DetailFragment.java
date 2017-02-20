package com.bigggfish.littley.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigggfish.littley.adapter.ExpandableDetailAdapter;
import com.bigggfish.littley.R;
import com.bigggfish.littley.dao.BillItem;
import com.bigggfish.littley.dao.TimeItem;
import com.bigggfish.littley.model.DBManager;
import com.bigggfish.littley.util.AnimatorTools;

import java.util.ArrayList;
import java.util.List;


public class DetailFragment extends Fragment implements View.OnClickListener{

    private ImageView expandDeleteImageView = null;
    private ImageView expandEditImageView = null;
    private View parentView;
    private ExpandableListView elvDetail;
    private TextView tvDetailAction;
    private ExpandableDetailAdapter expandableDetailAdapter;

    private DBManager dbManager;
    private List<List<BillItem>> billItemLists = new ArrayList<>();
    private List<TimeItem> timeItemList = new ArrayList<>();

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("---->OUT", "onCreateView");
        parentView = inflater.inflate(R.layout.fragment_detail, container, false);
        initView();
        return parentView;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.e("---->OUT", "onAttach1");
        super.onAttach(activity);
    }
    @Override
    public void onAttach(Context context) {
        Log.e("---->OUT", "onAttachonAttach");
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.e("---->OUT", "onAttachFragment");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("---->OUT", "onHiddenChanged");
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e("---->OUT", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        elvDetail = (ExpandableListView) parentView.findViewById(R.id.lv_detail);
        elvDetail.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_detail_head, null));
        tvDetailAction = (TextView) parentView.findViewById(R.id.tv_detail_action);

        initData();

        tvDetailAction.setOnClickListener(this);
        expandableDetailAdapter = new ExpandableDetailAdapter(this.getActivity(), timeItemList, billItemLists);
        elvDetail.setAdapter(expandableDetailAdapter);
        elvDetail.setGroupIndicator(null);
        for (int i = 0; i < expandableDetailAdapter.getGroupCount(); i++) {
            elvDetail.expandGroup(i);
        }
        expandableDetailAdapter.setOnBillItemTypeClickListener(new ExpandableDetailAdapter.OnBillItemTypeClickListener() {
            @Override
            public void onClick(View convertView, final int groupPosition, final int childPosition) {

                if (expandDeleteImageView != null && expandEditImageView != null) {
                    AnimatorTools.startTranslationAndRotationAnimator(expandEditImageView, expandEditImageView.getX()
                            , expandEditImageView.getY(), expandEditImageView.getX() - 200f, expandEditImageView.getY(), 300, new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    if (expandEditImageView != null) {
                                        expandEditImageView.setVisibility(View.GONE);
                                        expandEditImageView = null;
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                    AnimatorTools.startTranslationAndRotationAnimator(expandDeleteImageView, expandDeleteImageView.getX()
                            , expandDeleteImageView.getY(), expandDeleteImageView.getX() + 200f, expandDeleteImageView.getY(), 300, new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    if (expandDeleteImageView != null) {
                                        expandDeleteImageView.setVisibility(View.GONE);
                                        expandDeleteImageView = null;
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                } else {
                    ImageView ivBillType = (ImageView) convertView.findViewById(R.id.iv_detail_type);
                    LinearLayout llBillType = (LinearLayout) convertView.findViewById(R.id.ll_detail_type);
                    final ImageView ivBillEdit = (ImageView) convertView.findViewById(R.id.iv_detail_edit);
                    final ImageView ivBillDelete = (ImageView) convertView.findViewById(R.id.iv_detail_delete);
                    ivBillDelete.setVisibility(View.VISIBLE);
                    ivBillEdit.setVisibility(View.VISIBLE);
                    float fromX = llBillType.getX();
                    float fromY = ivBillType.getY();
                    AnimatorTools.startTranslationAndRotationAnimator(ivBillEdit, fromX, fromY
                            , fromX + 200f, fromY, 300, new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    expandEditImageView = ivBillEdit;
                                    ivBillEdit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(DetailFragment.this.getActivity(), "editgroup:" + groupPosition + "editchild:" + childPosition, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                    AnimatorTools.startTranslationAndRotationAnimator(ivBillDelete, fromX, fromY
                            , fromX - 200f, fromY, 300, new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    expandDeleteImageView = ivBillDelete;
                                    ivBillDelete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(DetailFragment.this.getActivity(), "deletegroup:"
                                                    + groupPosition + "deletechild:" + childPosition, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

                                }
                            });
                }
            }
        });

        expandableDetailAdapter.setOnBillItemDeleteClickListener(new ExpandableDetailAdapter.OnBillItemDeleteClickListener() {
            @Override
            public void onClick(int groupPosition, int childPosition) {
                Toast.makeText(DetailFragment.this.getActivity(), "group:" + groupPosition + "child:" + childPosition, Toast.LENGTH_SHORT).show();
            }
        });

        expandableDetailAdapter.setOnBillItemEditClickListener(new ExpandableDetailAdapter.OnBillItemEditClickListener() {
            @Override
            public void onClick(int groupPosition, int childPosition) {
                Toast.makeText(DetailFragment.this.getActivity(), "group:" + groupPosition + "child:" + childPosition, Toast.LENGTH_SHORT).show();
            }
        });
        elvDetail.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(final ExpandableListView expandableListView, View view, int i, long l) {
                Toast.makeText(DetailFragment.this.getActivity(), "group:" + i, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        elvDetail.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(DetailFragment.this.getActivity(), "group:" + i + "child:" + i1, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        elvDetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                colseExpandBillMenu();
                return false;
            }
        });
    }

    public void updateData(){
        initData();
        expandableDetailAdapter.notifyDataSetChanged();
    }

    private void colseExpandBillMenu() {
        if (expandDeleteImageView != null && expandEditImageView != null) {
            AnimatorTools.startTranslationAndRotationAnimator(expandEditImageView, expandEditImageView.getX()
                    , expandEditImageView.getY(), expandEditImageView.getX() - 200f, expandEditImageView.getY(), 300, new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if (expandEditImageView != null) {
                                expandEditImageView.setVisibility(View.GONE);
                                expandEditImageView = null;
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
            AnimatorTools.startTranslationAndRotationAnimator(expandDeleteImageView, expandDeleteImageView.getX()
                    , expandDeleteImageView.getY(), expandDeleteImageView.getX() + 200f, expandDeleteImageView.getY(), 300, new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if (expandDeleteImageView != null) {
                                expandDeleteImageView.setVisibility(View.GONE);
                                expandDeleteImageView = null;
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
        }
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void  initData() {
        timeItemList.clear();
        billItemLists.clear();
        dbManager = new DBManager(getActivity());
        List<BillItem> billItemList = dbManager.queryAllBill();
        if (billItemList.size() > 0) {
            int dayAmount = 0;
            int billTime = billItemList.get(0).getBillTime();
            List<BillItem> billItemChildList = new ArrayList<>();
            for (int i = 0; i < billItemList.size(); i++) {
                BillItem billItem = billItemList.get(i);
                if (billTime == billItem.getBillTime()) {
                    billItemChildList.add(billItem);
                    if(billItem.isSpend()){
                        dayAmount = dayAmount + billItem.getAmount();
                    }else{
                        dayAmount = dayAmount - billItem.getAmount();
                    }
                    if (i == billItemList.size() - 1) {
                        billItemLists.add(billItemChildList);
                        timeItemList.add(new TimeItem(dayAmount, billTime));
                    }
                } else {
                    billItemLists.add(billItemChildList);
                    timeItemList.add(new TimeItem(dayAmount, billTime));
                    billTime = billItem.getBillTime();
                    billItemChildList = new ArrayList<>();
                    dayAmount = 0;
                    billItemChildList.add(billItem);
                    if(billItem.isSpend()){
                        dayAmount = dayAmount + billItem.getAmount();
                    }else{
                        dayAmount = dayAmount - billItem.getAmount();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_detail_action:

                break;
        }
    }
}
