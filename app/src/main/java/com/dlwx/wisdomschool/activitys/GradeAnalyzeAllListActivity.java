package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.GradeAnalyzeAllListAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全部年度成绩分析列表
 */
public class GradeAnalyzeAllListActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private String[] yearstrs;
    private String cnid;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        cnid = intent.getStringExtra("cnid");
        setContentView(R.layout.activity_grade_analyze_all_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成绩分析");
        initTabBar(toolBar);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        wch(year);
        yearstrs = new String[5];
        for (int i = year; i>=year-4 ; i--) {
            yearstrs[year-(i)] = i+"";
        }
        lvList.setAdapter(new GradeAnalyzeAllListAdapter(ctx, yearstrs));
    }

    @Override
    protected void initListener() {
            lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String yearstr = yearstrs[i];
        startActivity(new Intent(ctx,YearAllStuGardAnalyzeActivity.class).putExtra("year",yearstr).putExtra("cnid",cnid));
    }
}
