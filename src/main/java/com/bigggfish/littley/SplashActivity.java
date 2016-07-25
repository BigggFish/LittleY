package com.bigggfish.littley;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static final int TAG_GOTO_MAIN = 1;
    private static final int TOTAL_TIME_IN_SPLASH = 3000;//ms

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TAG_GOTO_MAIN:
                    gotoMainActivity();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    private void initView(){
        handler.sendEmptyMessageDelayed(TAG_GOTO_MAIN, TOTAL_TIME_IN_SPLASH);
    }
}
