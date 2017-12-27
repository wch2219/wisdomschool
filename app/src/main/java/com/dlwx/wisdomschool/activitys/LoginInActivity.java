package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.base.MainActivity;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginInActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_forpwd)
    TextView tvForpwd;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_check)
    CheckBox cb_check;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_login_in);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.Login);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_forpwd, R.id.tv_regist, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forpwd:
                startActivity(new Intent(ctx,ForgetPwdActivity.class));
                break;
            case R.id.tv_regist:
                startActivity(new Intent(ctx,Register1Activity.class));
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {

            vibrator.vibrate(50);
            Toast.makeText(ctx, R.string.editphone, Toast.LENGTH_SHORT).show();
            return;
        }
         String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, R.string.editpwd, Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
        boolean checked = cb_check.isChecked();
        if (checked) {
            sp.edit().putInt(SpUtiles.TeacherOrPatriarch,1).commit();
        }else{
            sp.edit().putInt(SpUtiles.TeacherOrPatriarch,0).commit();
        }
//        huanxinLogin();
        //TODO
        startActivity(new Intent(ctx, MainActivity.class));
    }

    /**
     * 登录环信
     */
    private void huanxinLogin() {
        EMClient.getInstance().login("","",new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LogUtiles.LogI("登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LogUtiles.LogI("登录聊天服务器失败！");
            }
        });
    }
}
