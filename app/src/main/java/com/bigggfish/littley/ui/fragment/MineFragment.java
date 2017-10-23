package com.bigggfish.littley.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigggfish.littley.ui.activity.LoginActivity;
import com.bigggfish.littley.R;
import com.bigggfish.littley.ui.base.BaseFragment;
import com.bigggfish.littley.util.SPUtils;

import butterknife.BindView;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private boolean isLogin = false;
    private boolean needPassword = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_clear_data)
    TextView tvClearData;
    @BindView(R.id.tv_spend_limit)
    TextView tvSpendLimit;
    @BindView(R.id.rl_spend_limit)
    RelativeLayout rlSpendLimit;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_check_update)
    TextView tvCheckUpdate;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.switch_need_pw)
    SwitchCompat switchCompatNeedPw;

    public MineFragment() {
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View rootView) {

        String userName = (String) SPUtils.get(getActivity(), "username", "");
        if (TextUtils.isEmpty(userName)) {
            isLogin = false;
            tvLogin.setOnClickListener(this);
        } else {
            tvLogin.setText(userName);
            isLogin = true;
        }
        int spendLimit = (int) SPUtils.get(getActivity(), "spendlimit", 3000);
        tvSpendLimit.setText(String.valueOf(spendLimit));
        rlSpendLimit.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvCheckUpdate.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvClearData.setOnClickListener(this);
        switchCompatNeedPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                needPassword = checked;
                if (checked) {
                    if ("".equals(SPUtils.get(MineFragment.this.getActivity(), "password", ""))) {
                        showPasswordDialog();
                    }
                } else {
                    if (!"".equals(SPUtils.get(MineFragment.this.getActivity(), "password", ""))) {
                        showTestPasswordDialog();
                    }
                }
            }
        });

        collapsingToolbarLayout.setTitleEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivityForResult(intent, 201);
                break;
            case R.id.rl_spend_limit:
                showLimitDialog();
                break;
            case R.id.tv_feedback:
                showDialog("发邮件吧");
                break;
            case R.id.tv_check_update:
                showDialog("到github吧");
                break;
            case R.id.tv_about_us:
                showDialog("email:1063631103@qq.com" +
                        "\ngithub:https://github.com/BigggFish/LittleY");
                break;
            case R.id.tv_clear_data:
                showClearDataDialog();
                break;

        }
    }

    private void showLimitDialog() {
        final EditText etLimit = new EditText(this.getActivity());
        int spendLimit = (int) SPUtils.get(MineFragment.this.getActivity(), "spendlimit", 0);
        etLimit.setText(String.valueOf(spendLimit == 0 ? 3000 : spendLimit));
        etLimit.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this.getActivity())
                .setTitle("请输入")
                .setView(etLimit)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (etLimit.getText() == null || "".equals(etLimit.getText().toString())) {
                            Toast.makeText(MineFragment.this.getActivity(), "输入限制金额不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            int spendLimit = Integer.valueOf(etLimit.getText().toString());
                            tvSpendLimit.setText("" + spendLimit);
                            SPUtils.put(MineFragment.this.getActivity(), "spendlimit", spendLimit);
                        }
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void showDialog(String message) {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    //后期需要对是否加密做判断
    private void showClearDataDialog() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示")
                .setMessage("您确定要删除所有数据吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MineFragment.this.getActivity(), "删除", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MineFragment.this.getActivity(), "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void showPasswordDialog() {
        final EditText etPassword = new EditText(this.getActivity());
        etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this.getActivity())
                .setTitle("请输入新密码")
                .setView(etPassword)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!"".equals(etPassword.getText().toString())) {
                            SPUtils.put(MineFragment.this.getActivity(), "password", etPassword.getText().toString());
                            dialogInterface.dismiss();
                            switchCompatNeedPw.setChecked(true);
                        } else {
                            switchCompatNeedPw.setChecked(false);
                            Toast.makeText(MineFragment.this.getActivity(), "您输入的密码不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        switchCompatNeedPw.setChecked(false);
                    }
                })
                .show();
    }

    /**
     * 验证密码
     */
    private void showTestPasswordDialog() {
        final EditText etPassword = new EditText(this.getActivity());
        etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this.getActivity())
                .setTitle("请输入旧密码")
                .setView(etPassword)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!"".equals(etPassword.getText().toString())) {
                            if (etPassword.getText().toString().equals(SPUtils.get(MineFragment.this.getActivity(), "password", ""))) {
                                SPUtils.put(MineFragment.this.getActivity(), "password", "");
                                dialogInterface.dismiss();
                                showPasswordDialog();
                            } else {
                                switchCompatNeedPw.setChecked(true);
                                Toast.makeText(MineFragment.this.getActivity(), "您输入的密码不正确", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            switchCompatNeedPw.setChecked(true);
                            Toast.makeText(MineFragment.this.getActivity(), "您输入的密码不能为空", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        switchCompatNeedPw.setChecked(true);
                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (getActivity() == null) {
            Log.e("---->OUT", "getactivity == null");
        } else {
            if (resultCode == 202) {
                tvLogin.setText((String) SPUtils.get(getActivity(), "username", ""));
                isLogin = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
