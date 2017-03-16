package com.bigggfish.littley.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigggfish.littley.R;
import com.bigggfish.littley.util.Constant;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {

    private static final int TAG_GOTO_MAIN = 1;
    private static final int TOTAL_TIME_IN_SPLASH = 2000;//ms

    static class MyHandler extends Handler{

        private WeakReference<Activity> refActivity;
        public MyHandler(Activity activity){
            refActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = refActivity.get();
            if(activity != null){
                gotoMainActivity(activity);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NormalTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView(){
        new MyHandler(this).sendEmptyMessageDelayed(TAG_GOTO_MAIN, TOTAL_TIME_IN_SPLASH);
    }


    ///////////////////////////static///////////////////////////////////////


    private static void gotoMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
