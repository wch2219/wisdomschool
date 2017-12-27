package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.GradeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成绩展示
 */
public class GradeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_grade);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("高飞成绩");
        initTabBar(toolBar);
        lvList.setAdapter(new GradeAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


}
