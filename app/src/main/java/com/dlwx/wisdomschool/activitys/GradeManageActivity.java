package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassGradeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学生成绩管理
 */
public class GradeManageActivity extends BaseActivity 
        implements ExpandableListView.OnChildClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_create)
    LinearLayout llCreate;
    @BindView(R.id.lv_list)
    ExpandableListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_grade_manage);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("学生成绩管理");
        initTabBar(toolBar);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        lvList.setIndicatorBounds(width - 100, width - 10);
    lvList.setAdapter(new ClassGradeAdapter(ctx));
    }

    @Override
    protected void initListener() {
        lvList.setOnChildClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        if (i1 == 0) {
            startActivity(new Intent(ctx,GradeRankingActivity.class));
        }else {
            startActivity(new Intent(ctx,GradeAnalyzeAllListActivity.class));
        }
        return true;
    }
    @OnClick(R.id.ll_create)
    public void onViewClicked() {

            startActivity(new Intent(ctx,SeleteClassActivity.class));
    }
}
