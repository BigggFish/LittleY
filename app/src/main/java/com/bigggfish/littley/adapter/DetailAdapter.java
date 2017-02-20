package com.bigggfish.littley.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigggfish.littley.R;
import com.bigggfish.littley.model.dao.BillItem;

import java.util.List;

/**
 * Created by android on 2016/7/21.
 */
public class DetailAdapter extends BaseAdapter {
    private static final int TYPE_BILL_NORMAL = 0;
    private static final int TYPE_BILL_DATE = 1;

    private Context context;
    private List<BillItem> billItemList;

    public DetailAdapter(Context context, List<BillItem> billItemList) {
        this.context = context;
        this.billItemList = billItemList;
    }

    @Override
    public int getCount() {
        return billItemList == null ? 0 : billItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return billItemList == null ? null : billItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        /*if (billItemList.get(position).isNormal()) {
            return TYPE_BILL_NORMAL;
        } else {
            return TYPE_BILL_DATE;
        }*/
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        NormalViewHolder normalViewHolder;
        DateViewHolder dateViewHolder;
        switch (getItemViewType(position)) {
            case TYPE_BILL_DATE:
                if(convertView == null){
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_bill_date_amount, null);
                    dateViewHolder = new DateViewHolder(convertView);
                    convertView.setTag(dateViewHolder);
                }else{
                    dateViewHolder = (DateViewHolder) convertView.getTag();
                }
                break;
            case TYPE_BILL_NORMAL:
                if(convertView == null){
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_bill_normal, null);
                    normalViewHolder = new NormalViewHolder(convertView);
                    convertView.setTag(normalViewHolder);
                }else{
                    normalViewHolder = (NormalViewHolder) convertView.getTag();
                }

                if(billItemList.get(position).isSpend()){
                    normalViewHolder.tvIncomeAmount.setVisibility(View.INVISIBLE);
                    normalViewHolder.tvSpendAmount.setVisibility(View.VISIBLE);
                }else{
                    normalViewHolder.tvSpendAmount.setVisibility(View.INVISIBLE);
                    normalViewHolder.tvIncomeAmount.setVisibility(View.VISIBLE);
                }
                break;
        }
        return convertView;
    }

    static class NormalViewHolder {
        private ImageView ivDetailType;
        private TextView tvIncomeAmount;
        private TextView tvSpendAmount;
        public NormalViewHolder(View parentView){
            ivDetailType = (ImageView) parentView.findViewById(R.id.iv_detail_type);
            tvIncomeAmount = (TextView) parentView.findViewById(R.id.tv_income_amount);
            tvSpendAmount = (TextView) parentView.findViewById(R.id.tv_spend_amount);
        }
    }

    static class DateViewHolder {
        private ImageView ivGrayDot;
        private TextView tvBillDate;
        private TextView tvBillAmount;
        public DateViewHolder(View parentView){
            ivGrayDot = (ImageView) parentView.findViewById(R.id.iv_gray_dot);
            tvBillAmount = (TextView) parentView.findViewById(R.id.tv_bill_detail_amount);
            tvBillDate = (TextView) parentView.findViewById(R.id.tv_bill_detail_date);
        }
    }
}
