package com.bigggfish.littley.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigggfish.littley.R;
import com.bigggfish.littley.model.dao.BillType;
import com.bigggfish.littley.util.Constant;

import java.util.List;

/**
 * Created by android on 2016/7/21.
 * 条目类别适配器
 */
public class BillTypeAdapter extends BaseAdapter {

    private Context context;
    private List<BillType> billTypeList;
    private boolean isSpend;
    public BillTypeAdapter(Context context, List<BillType> billTypeList, boolean isSpend){
        this.context = context;
        this.billTypeList = billTypeList;
        this.isSpend = isSpend;
    }
    @Override
    public int getCount() {
        return billTypeList.size();
    }

    @Override
    public Object getItem(int i) {
        return billTypeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill_type, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BillType billType = billTypeList.get(position);
        viewHolder.ivBillType.setImageResource(Constant.TYPE_IMAGES_ID[billType.getImageId()]);
        viewHolder.tvBillType.setText(billType.getTitle());
        return convertView;
    }

    static class ViewHolder{
        private ImageView ivBillType;
        private TextView tvBillType;

        public ViewHolder(View convertView){
            this.ivBillType = (ImageView) convertView.findViewById(R.id.iv_bill_type);
            this.tvBillType = (TextView) convertView.findViewById(R.id.tv_bill_type);
        }
    }
}
