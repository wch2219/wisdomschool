package com.dlwx.wisdomschool.activitys;

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
 * 我i加入的班级 个人信息
 */
public class MyJoinClassPersionMessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_privatechat)
    LinearLayout llPrivatechat;
    private int tag;

    @Override
    protected void initView() {
        tag = getIntent().getIntExtra("tag", 0);
        setContentView(R.layout.activity_my_join_class_persion_mess);
        ButterKnife.bind(this);
        if (tag == 0) {
            llPrivatechat.setVisibility(View.VISIBLE);
        }else{
            llPrivatechat.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        tvTitle.setText("个人信息");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.ll_privatechat)
    public void onViewClicked() {
    }
}
