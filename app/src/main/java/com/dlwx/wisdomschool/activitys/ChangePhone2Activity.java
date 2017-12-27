package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
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
 * 获取验证码修改手机号
 */
public class ChangePhone2Activity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_aff)
    Button btnAff;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_phone2);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("输入验证码");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_aff)
    public void onViewClicked() {
        finish();
        startActivity(new Intent(ctx,ChangePhoneSuccessActivity.class));
    }
}
