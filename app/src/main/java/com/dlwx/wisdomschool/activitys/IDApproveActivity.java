package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 身份认证
 */
public class IDApproveActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_forgitpwd)
    TextView tvForgitpwd;
    @BindView(R.id.btn_aff)
    Button btnAff;
    //班级权限设置类型
    private String type;
    private String classid;
    private String jcid;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        classid = intent.getStringExtra("classid");
        jcid = intent.getStringExtra("jcid");
        setContentView(R.layout.activity_idapprove);
        ButterKnife.bind(this);
        register();
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("身份认证");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_forgitpwd, R.id.btn_aff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgitpwd:
                startActivity(new Intent(ctx,ForgetPwdActivity.class));
                break;
            case R.id.btn_aff:
                submit();
                break;
        }
    }

    private void submit() {
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(ctx, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("jcid",jcid);
        map.put("classid",classid);
        map.put("vip",type);
        map.put("password",pwd);
        mPreenter.fetch(map,true, HttpUrl.changeClassJurisd,"");

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
               close();
        }
        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
    }
}
