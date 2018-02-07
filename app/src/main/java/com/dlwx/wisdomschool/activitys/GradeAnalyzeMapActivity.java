package com.dlwx.wisdomschool.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.GradeMapBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.views.LineChartView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 单科成绩分析图
 */
public class GradeAnalyzeMapActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.line_chart_view)
    LineChartView lineChartView;
    private String gyear;
    private String cnid;
    private String xuekeid;
    private String userid;
    private String name;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        gyear = intent.getStringExtra("year");
        cnid = intent.getStringExtra("cnid");
        xuekeid = intent.getStringExtra("xuekeid");
        userid = intent.getStringExtra("userid");
        name = intent.getStringExtra("name");
        setContentView(R.layout.activity_grade_analyze_map);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classid", cnid);
        map.put("year", gyear);
        map.put("userid", userid);
        mPreenter.fetch(map, true, HttpUrl.GetScore_Tab, HttpUrl.GetScore_Tab + Token + cnid);
        tvTitle.setText(name + "成绩");
        initTabBar(toolBar);


    }

    @Override
    protected void initListener() {
        getAndroiodScreenProperty();
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        GradeMapBean gradeMapBean = gson.fromJson(s, GradeMapBean.class);
        if (gradeMapBean.getCode() == 200) {
            List<GradeMapBean.BodyBean> body = gradeMapBean.getBody();
            List<LineChartView.Data> datas = new ArrayList<>();
            for (int i = 0; i < body.size(); i++) {
//                GradeMapBean.BodyBean bodyBean = body.get(i);
                LineChartView.Data data = new LineChartView.Data((int) body.get(i).getScore());
                datas.add(data);
            }
            lineChartView.setStepSpace(screenWidth/(body.size()+1));
            lineChartView.setData(datas);
            lineChartView.setShowTable(true);
            lineChartView.playAnim();

        } else {
            Toast.makeText(ctx, gradeMapBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        screenHeight = (int) (height / density);// 屏幕高度(dp)
    }

    private int screenWidth;
    private int screenHeight;
}
