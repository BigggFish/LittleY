package com.bigggfish.littley.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigggfish.littley.R;
import com.bigggfish.littley.ui.base.BaseActivity;
import com.bigggfish.littley.util.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final String USER_NAME = "test";
    private static final String PASSWORD = "123456";

    @BindView(R.id.et_user_name)
    EditText etUserName;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.bt_login)
    Button btLogin;

    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.tv_forget_pw)
    TextView tvForgetPw;

    @BindView(R.id.iv_close)
    ImageView ivClose;

    private Handler handler = new Handler();
    private int requestCode;

    @Override
    protected void initView() {
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.bt_login)
    public void onLogin() {
        final String strUserName = etUserName.getText().toString();
        String strPassWord = etPassword.getText().toString();
        if (!TextUtils.isEmpty(strUserName) && !TextUtils.isEmpty(strPassWord)) {
            if (USER_NAME.equals(strUserName) && PASSWORD.equals(strPassWord)) {
                SPUtils.put(LoginActivity.this, "username", strUserName);
                SPUtils.put(LoginActivity.this, "password", strPassWord);
                //模拟登录延迟
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setResult(202);
                        finish();
                    }
                }, 2000);
            } else {
                Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.iv_close)
    public void onClose(){
        this.finish();
    }

    @OnClick(R.id.tv_forget_pw)
    public void onForgetPw(){
        Toast.makeText(this, "忘记密码？密码是" + PASSWORD, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_register)
    public void onRegister(){
        Toast.makeText(this, "不用注册，帐号是" + USER_NAME, Toast.LENGTH_SHORT).show();
    }
}
