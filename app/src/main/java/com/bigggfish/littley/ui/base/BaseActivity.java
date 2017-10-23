package com.bigggfish.littley.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bigggfish.littley.util.Global;
import com.bigggfish.littley.util.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by yuxianglong on 2017/10/16.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        setContentView(initLayout());

        ButterKnife.bind(this);

        initView();

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Global.setGlobalContext(this);
        resumeData();
    }

    protected void init(){}

    protected abstract void initView();

    protected abstract int initLayout();

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
        return (T) super.findViewById(id);
    }

    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
