package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看学生成绩
 */
public class LookStudentGradeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_graderanking)
    LinearLayout llGraderanking;
    @BindView(R.id.ll_gradeanalyze)
    LinearLayout llGradeanalyze;
    private String classid;

    @Override
    protected void initView() {
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_look_student_grade);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("学生成绩");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.ll_graderanking, R.id.ll_gradeanalyze})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_graderanking:
                startActivity(new Intent(ctx,GradeRankingActivity.class).putExtra("cnid",classid));
                break;
            case R.id.ll_gradeanalyze:
                startActivity(new Intent(ctx,GradeAnalyzeAllListActivity.class).putExtra("cnid",classid));
                break;
        }
    }
}
