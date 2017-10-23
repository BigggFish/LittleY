package com.bigggfish.littley.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigggfish.littley.R;
import com.bigggfish.littley.model.dao.BillItem;
import com.bigggfish.littley.ui.base.listui.BaseListAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bigggfish on 2016/7/21.
 */
public class DetailAdapter extends BaseListAdapter<BillItem, DetailAdapter.NormalViewHolder> {

    private List<BillItem> billItemList;

    public DetailAdapter(Context context, List<BillItem> billItemList) {
        super(context);
        this.billItemList = billItemList;
    }

    @Override
    protected int initItemLayout() {
        return R.layout.item_bill_normal;
    }

    @Override
    protected NormalViewHolder getViewHolder(View convertView) {
        return new NormalViewHolder(convertView);
    }

    @Override
    protected void bindData(NormalViewHolder viewHolder, int position) {
        if (billItemList.get(position).isSpend()) {
            viewHolder.tvIncomeAmount.setVisibility(View.INVISIBLE);
            viewHolder.tvSpendAmount.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvSpendAmount.setVisibility(View.INVISIBLE);
            viewHolder.tvIncomeAmount.setVisibility(View.VISIBLE);
        }
    }

    static class NormalViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.iv_detail_type)
        ImageView ivDetailType;
        @BindView(R.id.tv_income_amount)
        TextView tvIncomeAmount;
        @BindView(R.id.tv_spend_amount)
        TextView tvSpendAmount;

        private NormalViewHolder(View parentView) {
            super(parentView);
        }
    }

}
