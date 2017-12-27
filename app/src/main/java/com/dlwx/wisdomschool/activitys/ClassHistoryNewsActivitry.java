package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 班级消息，或历史消息
 */
public class ClassHistoryNewsActivitry extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.rev_view)
    RecyclerView revView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_history_news_activitry);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("消息");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
