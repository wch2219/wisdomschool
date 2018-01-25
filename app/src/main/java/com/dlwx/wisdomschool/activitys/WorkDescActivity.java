package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.fragments.WorkDescListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作业详情
 */
public class WorkDescActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_workdesc)
    FrameLayout rlWorkdesc;
    private String title;
    private String type;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        type = intent.getStringExtra("type");
        setContentView(R.layout.activity_work_desc);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText(title);
        Bundle bundle=new Bundle();
        bundle.putString("type", type);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        WorkDescListFragment workDescListFragment = new WorkDescListFragment();
        workDescListFragment.setArguments(bundle);
        transaction.add(R.id.rl_workdesc,workDescListFragment);
        transaction.commit();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
