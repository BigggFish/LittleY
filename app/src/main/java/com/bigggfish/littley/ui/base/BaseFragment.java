package com.bigggfish.littley.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigggfish.littley.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(initLayout(), null);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected abstract int initLayout();

    protected abstract void initView(View rootView);

    protected void loadData(){}

    protected void resumeData(){}

    protected void showMsg(Object obj) {
        try {
            if (obj != null) {
                ToastUtils.showToast((String)obj);
            }
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    protected <T extends View> T bindView(int id) {
        return (T) getView().findViewById(id);
    }

    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
