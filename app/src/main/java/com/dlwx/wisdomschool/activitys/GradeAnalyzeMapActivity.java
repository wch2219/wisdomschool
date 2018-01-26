package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.views.LineChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 单科成绩分析图
 */
public class GradeAnalyzeMapActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lineView)
    LineChartView lineView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_grade_analyze_map);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("高飞成绩");
        initTabBar(toolBar);
        List<Double> datas = new ArrayList<>();
        datas.add(300d);
        datas.add(20d);
        datas.add(40d);
        datas.add(50d);
        datas.add(50d);
        datas.add(60d);
        datas.add(60d);
        datas.add(80d);
        datas.add(80d);

        List<String> description = new ArrayList<>();
        description.add("1");
        description.add("2");
        description.add("3");
        description.add("4");
        description.add("5");
        description.add("6");
        description.add("7");
        description.add("8");
        description.add("9");

        lineView.setDatas(datas, description);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
