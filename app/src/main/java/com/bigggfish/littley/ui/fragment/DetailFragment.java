package com.bigggfish.littley.ui.fragment;

import android.animation.Animator;
import android.os.Bundle;
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

import com.bigggfish.littley.ui.adapter.ExpandableDetailAdapter;
import com.bigggfish.littley.R;
import com.bigggfish.littley.model.BillRepository;
import com.bigggfish.littley.model.dao.BillItem;
import com.bigggfish.littley.model.dao.TimeItem;
import com.bigggfish.littley.ui.base.BaseFragment;
import com.bigggfish.littley.util.AnimatorTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class DetailFragment extends BaseFragment implements View.OnClickListener {

    private ImageView expandDeleteImageView = null;
    private ImageView expandEditImageView = null;

    @BindView(R.id.lv_detail)
    ExpandableListView elvDetail;

    private ExpandableDetailAdapter expandableDetailAdapter;

    private List<List<BillItem>> billItemLists = new ArrayList<>();
    private List<TimeItem> timeItemList = new ArrayList<>();

    private BillRepository mBillRepository;

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBillRepository = BillRepository.getInstance(getActivity());
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initView(View rootView) {
        elvDetail.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_detail_head, null));
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

        initData();
    }

    public void updateData() {
        initData();
        expandableDetailAdapter.notifyDataSetChanged();
        for (int i = 0; i < expandableDetailAdapter.getGroupCount(); i++) {
            elvDetail.expandGroup(i);
        }
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


    private void initData() {
        timeItemList.clear();
        billItemLists.clear();
        List<BillItem> billItemList = mBillRepository.queryAllBill();
        Log.e("---->OUT", "billItemListSize" + billItemList.size());
        if (billItemList.size() > 0) {
            int dayAmount = 0;
            int billTime = billItemList.get(0).getBillTime();
            List<BillItem> billItemChildList = new ArrayList<>();
            for (int i = 0; i < billItemList.size(); i++) {
                BillItem billItem = billItemList.get(i);
                if (billTime == billItem.getBillTime()) {
                    billItemChildList.add(billItem);
                    if (billItem.isSpend()) {
                        dayAmount = dayAmount + billItem.getAmount();
                    } else {
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
                    if (billItem.isSpend()) {
                        dayAmount = dayAmount + billItem.getAmount();
                    } else {
                        dayAmount = dayAmount - billItem.getAmount();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
