package com.dlwx.wisdomschool.activitys;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.fragments.WorkFragment;

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
    @BindView(R.id.fl_hismess)
    FrameLayout flHismess;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_history_news_activitry);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolBar.setBackgroundResource(android.R.color.transparent);
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_hismess,new WorkFragment());
        fragmentTransaction.commit();
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
