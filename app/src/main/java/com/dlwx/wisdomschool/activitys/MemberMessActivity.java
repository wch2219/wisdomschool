package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 联系人信息
 */
public class MemberMessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_remarkname)
    TextView tvRemarkname;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.tv_jurisdiction)
    TextView tvJurisdiction;
    @BindView(R.id.ll_jurisdiction)
    LinearLayout llJurisdiction;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.iv_sendmess)
    ImageView ivSendmess;
    @BindView(R.id.ll_remarkmess)
    LinearLayout llRemarkmess;
    @BindView(R.id.ll_clearclass)
    LinearLayout llClearclass;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_member_mess);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("联系人信息");
            initTabBar(toolBar);
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

    @OnClick({R.id.ll_remark, R.id.ll_jurisdiction, R.id.ll_remarkmess, R.id.ll_clearclass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_remark://班级内名称备注
                break;
            case R.id.ll_jurisdiction://班级内权限
                startActivity(new Intent(ctx,ClassJurisdictionActivity.class));
                break;
            case R.id.ll_remarkmess://备注信息
                startActivity(new Intent(ctx,RemarkMessActivity.class));
                break;
            case R.id.ll_clearclass://请出班级
                break;
        }
    }
}
