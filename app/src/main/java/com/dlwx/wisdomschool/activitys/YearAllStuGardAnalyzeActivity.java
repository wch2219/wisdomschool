package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.YearAllStuAnalyzeAdapter;
import com.dlwx.wisdomschool.bean.AllXuekeBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 某年全部学生列表
 */
public class YearAllStuGardAnalyzeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private String year;
    private String cnid;
    private List<AllXuekeBean.BodyBean> body;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        cnid = intent.getStringExtra("cnid");
        setContentView(R.layout.activity_grade_analyze_all_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成绩分析");
        initTabBar(toolBar);

        getDataList(year);
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
        AllXuekeBean.BodyBean bodyBean = body.get(i);
        startActivity(new Intent(ctx,SomeOneAllGradeAnalyzeActivity.class).putExtra("cnid",cnid).putExtra("year",year).putExtra("xuekeid",bodyBean.getXuekeid()));
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        AllXuekeBean allXuekeBean = gson.fromJson(s, AllXuekeBean.class);
        if (allXuekeBean.getCode() == 200) {
            body = allXuekeBean.getBody();
            lvList.setAdapter(new YearAllStuAnalyzeAdapter(ctx,body));
        }else{
            Toast.makeText(ctx, allXuekeBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataList(String year){
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("classid",cnid);
        map.put("year",year);
        mPreenter.fetch(map,true, HttpUrl.All_Xueke,HttpUrl.All_Xueke+Token+year+cnid);
    }
}
