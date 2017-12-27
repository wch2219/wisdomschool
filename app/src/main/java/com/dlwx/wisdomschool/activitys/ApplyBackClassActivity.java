package com.dlwx.wisdomschool.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 申请退出班级
 */
public class ApplyBackClassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_membernum)
    TextView tvMembernum;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_apply_back_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("申请退班");
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

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        finish();
    }
}
