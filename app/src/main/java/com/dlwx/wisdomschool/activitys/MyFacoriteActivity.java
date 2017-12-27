package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.HomeItmeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的收藏
 */
public class MyFacoriteActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_facorite);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的收藏");
        initTabBar(toolBar);
        LinearLayoutManager itemManager = new LinearLayoutManager(ctx);
        rvRecyclerview.setLayoutManager(itemManager);
        rvRecyclerview.setAdapter(new HomeItmeAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


}
