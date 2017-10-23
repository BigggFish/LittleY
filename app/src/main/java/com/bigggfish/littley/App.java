package com.bigggfish.littley;

import android.app.Application;
import android.provider.Settings;

import com.bigggfish.littley.util.Global;

/**
 * Created by yuxianglong on 2017/10/17.
 *
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Global.setGlobalContext(this);
    }
}
