package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.GradeRankitemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成绩排名
 */
public class GradeRankingActivity extends BaseActivity
        implements AdapterView.OnItemClickListener,TabLayout.OnTabSelectedListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_grade_ranking);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成绩排名");
        initTabBar(toolBar);
       String[] strs = ctx.getResources().getStringArray(R.array.graderanktitle);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < strs.length; i++) {
            tab_layout.addTab(tab_layout.newTab().setText(strs[i]));//添加tab选项卡
        }
        lvList.setAdapter(new GradeRankitemAdapter(ctx));
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
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(ctx,AllSubJiectActivity.class));
    }
}
