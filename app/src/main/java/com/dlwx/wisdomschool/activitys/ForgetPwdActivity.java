package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.CountDownUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.AuthWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity {
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
    @BindView(R.id.et_affpwd)
    EditText etAffpwd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("忘记密码");
            initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }



    @OnClick({R.id.tv_auth, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_auth:
                if (tvAuth.getText().equals("获取验证码") || tvAuth.getText().equals("重新发送"))
                    getAuth();

                break;
            case R.id.btn_submit:
                break;
        }
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
