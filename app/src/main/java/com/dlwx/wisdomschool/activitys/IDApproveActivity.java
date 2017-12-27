package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void initView() {
        setContentView(R.layout.activity_idapprove);
        ButterKnife.bind(this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.tv_forgitpwd, R.id.btn_aff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgitpwd:
                startActivity(new Intent(ctx,ForgetPwdActivity.class));
                break;
            case R.id.btn_aff:
                break;
        }
    }
}
