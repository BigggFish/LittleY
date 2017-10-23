package com.bigggfish.littley.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigggfish.littley.R;
import com.bigggfish.littley.model.dao.BillType;
import com.bigggfish.littley.ui.base.listui.BaseListAdapter;
import com.bigggfish.littley.util.Constant;

import java.util.List;

import butterknife.BindView;

/**
 * Created by bigggfish on 2016/7/21.
 * 条目类别适配器
 */
public class BillTypeAdapter extends BaseListAdapter<BillType, BillTypeAdapter.ViewHolder> {

    private boolean isSpend;

    public BillTypeAdapter(Context context, List<BillType> billTypeList, boolean isSpend){
        super(context);
        this.isSpend = isSpend;
        replaceAllData(billTypeList);
    }

    @Override
    protected int initItemLayout() {
        return R.layout.item_bill_type;
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, int position) {
        BillType billType = mData.get(position);
        viewHolder.ivBillType.setImageResource(Constant.TYPE_IMAGES_ID[billType.getImageId()]);
        viewHolder.tvBillType.setText(billType.getTitle());
    }

    static class ViewHolder extends BaseListAdapter.ViewHolder{

        @BindView(R.id.iv_bill_type)
        ImageView ivBillType;
        @BindView(R.id.tv_bill_type)
        TextView tvBillType;

        protected ViewHolder(View convertView){
            super(convertView);
        }
    }
}
