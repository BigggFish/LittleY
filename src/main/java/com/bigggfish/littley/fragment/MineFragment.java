package com.bigggfish.littley.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.MessagePattern;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigggfish.littley.LoginActivity;
import com.bigggfish.littley.R;
import com.bigggfish.littley.util.SPUtils;

public class MineFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private boolean isLogin = false;
<<<<<<< HEAD
=======
    private boolean needPassword = false;
>>>>>>> dev

    private View parent;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView tvLogin;
    private TextView tvClearData;
    private TextView tvSpendLimit;
    private RelativeLayout rlSpendLimit;
    private TextView tvFeedback;
    private TextView tvCheckUpdate;
    private TextView tvAboutUs;
    private SwitchCompat switchCompatNeedPw;

    public MineFragment() {
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return parent;
    }

    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) parent.findViewById(R.id.collapse_toolbar);
        toolbar = (Toolbar) parent.findViewById(R.id.toolbar);
        tvLogin = (TextView) parent.findViewById(R.id.tv_login);
        tvClearData = (TextView) parent.findViewById(R.id.tv_clear_data);
        tvSpendLimit = (TextView) parent.findViewById(R.id.tv_spend_limit);
        tvFeedback = (TextView) parent.findViewById(R.id.tv_feedback);
        tvCheckUpdate = (TextView) parent.findViewById(R.id.tv_check_update);
        tvAboutUs = (TextView) parent.findViewById(R.id.tv_about_us);
        rlSpendLimit = (RelativeLayout) parent.findViewById(R.id.rl_spend_limit);
        switchCompatNeedPw = (SwitchCompat) parent.findViewById(R.id.switch_need_pw);

        String userName = (String)SPUtils.get(getActivity(), "username", "");
        if(TextUtils.isEmpty(userName)){
            isLogin = false;
            tvLogin.setOnClickListener(this);
        }else{
            tvLogin.setText(userName);
            isLogin = true;
<<<<<<< HEAD
            int spendLimit = (int)SPUtils.get(getActivity(), "spendlimit", 3000);
            tvSpendLimit.setText("￥" + spendLimit);
        }

=======
        }
        int spendLimit = (int)SPUtils.get(getActivity(), "spendlimit", 3000);
        tvSpendLimit.setText("" + spendLimit);
>>>>>>> dev
        rlSpendLimit.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvCheckUpdate.setOnClickListener(this);
        tvAboutUs.setOnClickListener(this);
        tvClearData.setOnClickListener(this);
        switchCompatNeedPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
<<<<<<< HEAD
                if(checked){
                    SPUtils.put(MineFragment.this.getActivity(), "haspassword", true);
                }else{
                    SPUtils.put(MineFragment.this.getActivity(), "haspassword", false);
=======
                needPassword = checked;
                if(checked){
                    if("".equals(SPUtils.get(MineFragment.this.getActivity(), "password",""))){
                        showPasswordDialog();
                    }
                }else{
                    if(!"".equals(SPUtils.get(MineFragment.this.getActivity(), "password",""))){
                        showTestPasswordDialog();
                    }
>>>>>>> dev
                }
            }
        });

        collapsingToolbarLayout.setTitleEnabled(false);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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

    private void showLimitDialog(){
        final EditText etLimit = new EditText(this.getActivity());
<<<<<<< HEAD
=======
        int spendLimit = (int)SPUtils.get(MineFragment.this.getActivity(), "spendlimit", 0);
        etLimit.setText(""+ (spendLimit == 0 ? 3000 : spendLimit));
>>>>>>> dev
        etLimit.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this.getActivity())
                .setTitle("请输入")
                .setView(etLimit)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(etLimit.getText() == null || "".equals(etLimit.getText().toString())){
<<<<<<< HEAD
                            tvSpendLimit.setText("￥0");
                        }else{
                            int spendLimit = Integer.valueOf(etLimit.getText().toString());
                            tvSpendLimit.setText("￥" + spendLimit);
=======
                            Toast.makeText(MineFragment.this.getActivity(), "输入限制金额不能为空", Toast.LENGTH_SHORT).show();
                        }else{
                            int spendLimit = Integer.valueOf(etLimit.getText().toString());
                            tvSpendLimit.setText("" + spendLimit);
>>>>>>> dev
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

    private void showDialog(String message ){
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
    private void showClearDataDialog(){
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

    private void showPasswordDialog(){
        final EditText etPassword = new EditText(this.getActivity());
        etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this.getActivity())
<<<<<<< HEAD
                .setTitle("请输入密码")
=======
                .setTitle("请输入新密码")
>>>>>>> dev
                .setView(etPassword)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
<<<<<<< HEAD
                        SPUtils.put(MineFragment.this.getActivity(),etPassword.getText().toString(), "");
                        dialogInterface.dismiss();
=======
                        if(!"".equals(etPassword.getText().toString())){
                            SPUtils.put(MineFragment.this.getActivity(), "password", etPassword.getText().toString());
                            dialogInterface.dismiss();
                            switchCompatNeedPw.setChecked(true);
                        }else{
                            switchCompatNeedPw.setChecked(false);
                            Toast.makeText(MineFragment.this.getActivity(), "您输入的密码不能为空", Toast.LENGTH_SHORT).show();
                        }
>>>>>>> dev
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
<<<<<<< HEAD
=======
                        switchCompatNeedPw.setChecked(false);
>>>>>>> dev
                    }
                })
                .show();
    }

<<<<<<< HEAD
=======
    /**
     * 验证密码
     */
    private void showTestPasswordDialog(){
        final EditText etPassword = new EditText(this.getActivity());
        etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this.getActivity())
                .setTitle("请输入旧密码")
                .setView(etPassword)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!"".equals(etPassword.getText().toString())){
                            if(etPassword.getText().toString().equals(SPUtils.get(MineFragment.this.getActivity(), "password", ""))){
                                SPUtils.put(MineFragment.this.getActivity(), "password", "");
                                dialogInterface.dismiss();
                                showPasswordDialog();
                            }else{
                                switchCompatNeedPw.setChecked(true);
                                Toast.makeText(MineFragment.this.getActivity(), "您输入的密码不正确", Toast.LENGTH_SHORT).show();
                            }
                        }else{
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
>>>>>>> dev
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(getActivity() == null){
            Log.e("---->OUT","getactivity == null");
        }else{
            if(resultCode == 202){
                tvLogin.setText((String)SPUtils.get(getActivity(), "username", ""));
                isLogin = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
