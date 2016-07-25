package com.bigggfish.littley.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigggfish.littley.Adapter.DetailAdapter;
import com.bigggfish.littley.R;
import com.bigggfish.littley.dao.BillItem;

import java.util.ArrayList;
import java.util.List;


public class DetailFragment extends Fragment {

    private View parentView;
    private ListView lvDetail;

    public DetailFragment() {
        // Required empty public constructor
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
        parentView = inflater.inflate(R.layout.fragment_detail, container, false);
        initView();
        return parentView;
    }

    private void initView(){
        lvDetail = (ListView) parentView.findViewById(R.id.lv_detail);
        lvDetail.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_detail_head, null));
        lvDetail.setAdapter(new DetailAdapter(this.getActivity(), getData()));
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private List<BillItem> getData(){
        List<BillItem> billItemList = new ArrayList<>();
        for(int i=0; i<20; i++){
            if(i % 4 == 0){
                billItemList.add(new BillItem(false, false));
            }else if(i % 3 == 0){
                billItemList.add(new BillItem(true, false));
            }else{
                billItemList.add(new BillItem(true, true));
            }
        }
        return billItemList;
    }
}
