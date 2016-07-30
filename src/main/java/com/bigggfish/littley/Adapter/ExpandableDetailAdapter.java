package com.bigggfish.littley.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigggfish.littley.R;
import com.bigggfish.littley.dao.BillItem;
import com.bigggfish.littley.dao.TimeItem;
import com.bigggfish.littley.util.AnimatorTools;
import com.bigggfish.littley.util.Constant;

import java.util.List;

/**
 * Created by android on 2016/7/25.
 */
public class ExpandableDetailAdapter extends BaseExpandableListAdapter {

    private int expandGroupPosition = -1;//那个item编辑菜单展开
    private int expandChildPosition = -1;

    private Context context;
    private List<TimeItem> timeItemList;//group
    private List<List<BillItem>> billItemLists;//child
    private OnBillItemTypeClickListener onBillItemTypeClickListener;
    private OnBillItemDeleteClickListener onBillItemDeleteClickListener;
    private OnBillItemEditClickListener onBillItemEditClickListener;

    public ExpandableDetailAdapter (Context context, List<TimeItem> timeItemList, List<List<BillItem>> billItemLists){
        this.context = context;
        this.billItemLists = billItemLists;
        this.timeItemList = timeItemList;
    }

    @Override
    public int getGroupCount() {
        if(billItemLists != null){
            return timeItemList.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        if(billItemLists != null && billItemLists.size() > 0){
            if(billItemLists.get(i) != null){
                return billItemLists.get(i).size();
            }
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return timeItemList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return billItemLists.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill_date_amount, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        }else{
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        TimeItem timeItem = timeItemList.get(i);
        groupViewHolder.tvBillDate.setText((timeItem.getBillTime()%10000)/100 +"月" + timeItem.getBillTime()%100 + "日");
        groupViewHolder.tvBillAmount.setText("￥"+timeItem.getDayAmount());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildViewHolder childViewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill_normal, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        BillItem billItem = billItemLists.get(groupPosition).get(childPosition);

        if(billItem.isSpend()){
            childViewHolder.tvSpendAmount.setVisibility(View.VISIBLE);
            childViewHolder.tvSpendAmount.setText("￥" + billItem.getAmount());
            childViewHolder.tvIncomeAmount.setVisibility(View.INVISIBLE);
            childViewHolder.ivDetailType.setImageResource(Constant.TYPE_IMAGES_ID[billItem.getBillTypeId()-1]);
        }else{
            childViewHolder.tvIncomeAmount.setVisibility(View.VISIBLE);
            childViewHolder.tvIncomeAmount.setText("￥" + billItem.getAmount());
            childViewHolder.tvSpendAmount.setVisibility(View.INVISIBLE);
            childViewHolder.ivDetailType.setImageResource(Constant.TYPE_IMAGES_ID[billItem.getBillTypeId()-1 + 14]);
        }
        final View itemView = convertView;
        if(groupPosition != expandGroupPosition && childPosition != expandChildPosition){
            childViewHolder.ivBillDelete.setVisibility(View.GONE);
            childViewHolder.ivBillEdit.setVisibility(View.GONE);
        }
        childViewHolder.ivDetailType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onBillItemTypeClickListener != null){
                    onBillItemTypeClickListener.onClick(itemView, groupPosition, childPosition);
                    expandGroupPosition = groupPosition;
                    expandChildPosition = childPosition;
                }
              /*  float fromX = childViewHolder.llBillType.getLeft();
                float fromY = childViewHolder.ivDetailType.getTop();
                AnimatorTools.startTranslationAndRotationAnimator(childViewHolder.ivBillDelete, fromX, fromY
                        , fromX - 200f, fromY, 300, new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                                childViewHolder.ivBillEdit.setVisibility(View.VISIBLE);
                                childViewHolder.ivBillDelete.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                expandGroupPosition = groupPosition;
                                expandChildPosition = childPosition;
                                childViewHolder.ivBillDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(onBillItemDeleteClickListener != null){
                                            onBillItemDeleteClickListener.onClick(groupPosition, childPosition);
                                        }
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
                AnimatorTools.startTranslationAndRotationAnimator(childViewHolder.ivBillEdit, fromX, fromY
                        , fromX + 200f, fromY, 300, null);*/
            }
        });
        return convertView;
    }

/*

    class DeleteAnimationListener implements Animator.AnimatorListener{

        private int groupPosition;
        private int childPosition;
        public DeleteAnimationListener(int groupPostion, int childPosition){
            this.groupPosition = groupPostion;
            this.childPosition = childPosition;
        }
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            expandChildPosition = childPosition;
            expandGroupPosition = groupPosition;
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
*/

   /* class EditAnimationListener implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
*/
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    public void setOnBillItemDeleteClickListener(OnBillItemDeleteClickListener onBillItemDeleteClickListener) {
        this.onBillItemDeleteClickListener = onBillItemDeleteClickListener;
    }

    public void setOnBillItemEditClickListener(OnBillItemEditClickListener onBillItemEditClickListener) {
        this.onBillItemEditClickListener = onBillItemEditClickListener;
    }

    public void setOnBillItemTypeClickListener(OnBillItemTypeClickListener onBillItemTypeClickListener) {
        this.onBillItemTypeClickListener = onBillItemTypeClickListener;
    }

    public interface OnBillItemTypeClickListener{
        void onClick(View convertView , int groupPosition, int childPosition);
    }

    public interface OnBillItemDeleteClickListener{
        void onClick(int groupPosition, int childPosition);
    }

    public interface OnBillItemEditClickListener{
        void onClick(int groupPosition, int childPosition);
    }

    static class ChildViewHolder {
        private ImageView ivDetailType;
        private TextView tvIncomeAmount;
        private TextView tvSpendAmount;
        private ImageView ivBillDelete;
        private ImageView ivBillEdit;
        private LinearLayout llBillType;
        public ChildViewHolder(View parentView){
            ivDetailType = (ImageView) parentView.findViewById(R.id.iv_detail_type);
            tvIncomeAmount = (TextView) parentView.findViewById(R.id.tv_income_amount);
            tvSpendAmount = (TextView) parentView.findViewById(R.id.tv_spend_amount);
            ivBillDelete = (ImageView) parentView.findViewById(R.id.iv_detail_delete);
            ivBillEdit = (ImageView) parentView.findViewById(R.id.iv_detail_edit);
            llBillType = (LinearLayout) parentView.findViewById(R.id.ll_detail_type);
        }
    }

    static class GroupViewHolder {
        private ImageView ivGrayDot;
        private TextView tvBillDate;
        private TextView tvBillAmount;
        public GroupViewHolder(View parentView){
            ivGrayDot = (ImageView) parentView.findViewById(R.id.iv_gray_dot);
            tvBillAmount = (TextView) parentView.findViewById(R.id.tv_bill_detail_amount);
            tvBillDate = (TextView) parentView.findViewById(R.id.tv_bill_detail_date);
        }
    }
}
