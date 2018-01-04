package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.CountDownUtiles;
import com.dlwx.baselib.utiles.VerificationCodeUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.utiles.AuthWindow;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 填写注册信息
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.et_auth)
    EditText etAuth;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_pwd)
    ImageView ivPwd;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private VerificationCodeUtiles codeUtiles;
    private String role;

    @Override
    protected void initView() {
        role = getIntent().getStringExtra("role");
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.register);
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    private boolean isshow = false;
    @OnClick({R.id.tv_auth, R.id.iv_pwd, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_auth:
                if (tvAuth.getText().equals("获取验证码") || tvAuth.getText().equals("重新发送"))
                getAuth();
                break;
            case R.id.iv_pwd:
                if (isshow) {
                    isshow = false;
                    Toast.makeText(ctx, "true", Toast.LENGTH_SHORT).show();
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Glide.with(ctx).load(R.mipmap.icon_yanjinga).into(ivPwd);
                }else{
                    isshow = true;
                    Toast.makeText(ctx, "false", Toast.LENGTH_SHORT).show();
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Glide.with(ctx).load(R.mipmap.icon_yanjing).into(ivPwd);
                }
                break;
            case R.id.btn_register:

                goregister();
                break;
        }
    }

    /**
     * 提交注册
     */
    private void goregister() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {

            vibrator.vibrate(50);
            Toast.makeText(ctx, R.string.editphone, Toast.LENGTH_SHORT).show();
            return;
        }
        String auth = etAuth.getText().toString().trim();
        if (TextUtils.isEmpty(auth)) {

            vibrator.vibrate(50);
            Toast.makeText(ctx, R.string.editauth, Toast.LENGTH_SHORT).show();
            return;
        }
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, R.string.editpwd, Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("role",role);
        map.put("telephone",phone);
        map.put("password",pwd);
        mPreenter.fetch(map,false, HttpUrl.register,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
            finish();
        }
        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取图片验证码
     */
    private void getAuth() {
       String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            vibrator.vibrate(50);

            Toast.makeText(ctx, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthWindow authWindow = new AuthWindow(ctx);
        authWindow.startShowPopu(ctx, tvAuth, "");
        authWindow.setAuthListener(authListener);
    }
    /**
     * 输入图形验证码后返回的数据
     */
    private AuthWindow.AuthListener authListener = new AuthWindow.AuthListener() {
        @Override
        public void getResuktAuth(String auth) {
            if (!TextUtils.isEmpty(auth)) {
                CountDownUtiles countDownUtiles = new CountDownUtiles(tvAuth);
                if (tvAuth.getText().equals("获取验证码") || tvAuth.getText().equals("重新发送")) {
//                    codeUtiles = new VerificationCodeUtiles(ctx, phone, 0, countDownUtiles,imgname,auth);
//                    codeUtiles.sendVerificationCode("", loading);
                }
            }
        }

        @Override
        public void gainImg() {
            getAuth();
        }
    };
}
