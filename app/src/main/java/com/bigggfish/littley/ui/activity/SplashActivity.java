package com.bigggfish.littley.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.bigggfish.littley.R;
import com.bigggfish.littley.ui.base.BaseActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends BaseActivity {

    private static final int TAG_GOTO_MAIN = 1;
    private static final int TOTAL_TIME_IN_SPLASH = 2000;//ms

    private static class MyHandler extends Handler{

        private WeakReference<Activity> refActivity;

        private MyHandler(Activity activity){
            refActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = refActivity.get();
            if(activity != null){
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        setTheme(R.style.NormalTheme);
    }

    @Override
    protected void initView(){
        new MyHandler(this).sendEmptyMessageDelayed(TAG_GOTO_MAIN, TOTAL_TIME_IN_SPLASH);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }
}
