package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ExamAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 开始考试
 */
public class StartExamActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_start_exam);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("一星考试");
        initTabBar(toolBar);
        lvList.setAdapter(new ExamAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
