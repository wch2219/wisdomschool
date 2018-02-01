package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.base.MainActivity;
import com.dlwx.wisdomschool.base.MyApplication;
import com.dlwx.wisdomschool.bean.LoginBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;
import java.util.Map;

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
    private LoginBean.BodyBean body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login_in);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.Login);
        etPhone.setText("18637051978");
        etPwd.setText("123456");
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
        Map<String,String> map = new HashMap<>();
        map.put("telephone",phone);
        map.put("password",pwd);
        mPreenter.fetch(map,false, HttpUrl.login,"");
    }
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        LoginBean loginBean = gson.fromJson(s, LoginBean.class);
        if (loginBean.getCode() == 200) {
            body = loginBean.getBody();
            showLoading();
            huanxinLogin(body.getUserid(), body.getUserid());
        }else{
            Toast.makeText(ctx, loginBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录环信
     */
    private void huanxinLogin(String username,String pwd) {
        EMClient.getInstance().login(username,pwd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                loginSucc();

            }

            @Override
            public void onProgress(int progress, String status) {
                    wch("pro:"+progress+"sta:"+status);

            }
            @Override
            public void onError(int code, String message) {
                LogUtiles.LogI("登录聊天服务器失败！");
                LogUtiles.LogI("code:"+code+"message:"+message);

                if (code == 200) {
                    loginSucc();
                }else{
                    handler.sendEmptyMessage(2);
                }
            }
        });
    }

    private void loginSucc() {
        sp.edit().putString(SpUtiles.Token, body.getToken()).commit();
        sp.edit().putString(SpUtiles.Nickname, body.getNickname()).commit();
        sp.edit().putString(SpUtiles.Header_pic, body.getHeader_pic()).commit();
        sp.edit().putString(SpUtiles.Userid, body.getUserid()).commit();
        sp.edit().putString(SpUtiles.Telephone, body.getTelephone()).commit();
        sp.edit().putInt(SpUtiles.TeacherOrPatriarch, body.getIsteacher()).commit();
        MyApplication.Token = body.getToken();
        wch(body.getUserid());
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
        ;
        LogUtiles.LogI("登录聊天服务器成功！");
        handler.sendEmptyMessage(1);

//                if (checked) {
//
//                }else{
//                    sp.edit().putInt(SpUtiles.TeacherOrPatriarch,0).commit();
//                }
        startActivity(new Intent(ctx, MainActivity.class));
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
             switch (msg.what){
                        case 1:
                            disLoading();
                            finish();
                            Toast.makeText(ctx, "登录成功", Toast.LENGTH_SHORT).show();
                            break;
                            
                            case 2:
                                disLoading();
                                Toast.makeText(ctx, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                                break;
                    }
            
        }
    };
}
