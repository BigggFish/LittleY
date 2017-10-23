package com.bigggfish.littley.ui.base.listui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by bigggfish on 2017/10/18.
 *
 */

public abstract class BaseListAdapter<T, E extends BaseListAdapter.ViewHolder>
        extends BaseAdapter implements ICommonListAdapter<T>{

    protected List<T> mData;
    LayoutInflater mInflater;

    protected BaseListAdapter(Context context){
        mData = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        E viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(initItemLayout(), parent, false);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (E) convertView.getTag();
        }
        bindData(viewHolder, position);
        return convertView;
    }

    protected abstract int initItemLayout();

    protected abstract E getViewHolder(View convertView);

    protected abstract void bindData(E viewHolder, int position);

    public static abstract class ViewHolder{

        protected ViewHolder(View itemView){
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void replaceAllData(List<T> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void addAllData(List<T> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
