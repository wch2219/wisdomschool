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
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.GradeRankitemAdapter;
import com.dlwx.wisdomschool.bean.GradeYearBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

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
    private String cnid;//班级ID
    private String[] yearstrs;
    private List<GradeYearBean.BodyBean> body;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        cnid = intent.getStringExtra("cnid");
        setContentView(R.layout.activity_grade_ranking);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成绩排名");
        initTabBar(toolBar);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        wch(year);
        yearstrs = new String[5];
        for (int i = year; i>=year-4 ; i--) {
            yearstrs[year-(i)] = i+"";
        }
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < yearstrs.length; i++) {
            tab_layout.addTab(tab_layout.newTab().setText(yearstrs[i]));//添加tab选项卡
        }
        getDataList(year+"");
        tab_layout.setOnTabSelectedListener(this);

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
        int position = tab.getPosition();
        String yearstr = yearstrs[position];
        getDataList(yearstr);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        GradeYearBean.BodyBean bodyBean = body.get(i);
        Intent intent = new Intent(ctx, AllSubJiectActivity.class);
        intent.putExtra("irid",bodyBean.getIrid());
        intent.putExtra("classid",cnid);
        startActivity(intent);
    }
    private void getDataList(String year){
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("classid",cnid);
        map.put("year",year);
        mPreenter.fetch(map,true, HttpUrl.GetScore_record,HttpUrl.GetScore_record+Token+year+cnid);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        GradeYearBean gradeYearBean = gson.fromJson(s, GradeYearBean.class);
        if (gradeYearBean.getCode() == 200) {
            body = gradeYearBean.getBody();
            lvList.setAdapter(new GradeRankitemAdapter(ctx, body));
        }else{
            Toast.makeText(ctx, gradeYearBean.getResult(), Toast.LENGTH_SHORT).show();
        }

    }
}
