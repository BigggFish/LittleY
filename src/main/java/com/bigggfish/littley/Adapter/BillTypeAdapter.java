package com.bigggfish.littley.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigggfish.littley.R;
import com.bigggfish.littley.dao.BillType;

import java.util.List;

/**
 * Created by android on 2016/7/21.
 */
public class BillTypeAdapter extends BaseAdapter {

    private Context context;
    private List<BillType> billTypeList;
    public BillTypeAdapter(Context context, List<BillType> billTypeList){
        this.context = context;
        this.billTypeList = billTypeList;
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
