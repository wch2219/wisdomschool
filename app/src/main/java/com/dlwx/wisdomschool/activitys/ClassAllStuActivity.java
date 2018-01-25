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
import com.dlwx.wisdomschool.adapter.ClassAllStuAdapter;
import com.dlwx.wisdomschool.bean.GreadRankBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 班级内的全部人员成绩排名
 */
public class ClassAllStuActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @BindView(R.id.lv_list)
    ListView lvList;
    private String irid;
    private String classid;
    private String xuekeid;

    @Override
    protected void initView() {
        irid = getIntent().getStringExtra("irid");
        classid = getIntent().getStringExtra("classid");
        xuekeid = getIntent().getStringExtra("xuekeid");
        setContentView(R.layout.activity_class_all_stu);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("成绩排名");

        getData();

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
        if ("999".equals(classid)) {
            startActivity(new Intent(ctx,GradeActivity.class));
        }
    }

    private void getData() {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("classid",classid);
        map.put("irid",irid);
        map.put("xuekeid",xuekeid);
        mPreenter.fetch(map,true, HttpUrl.user_score_sort,HttpUrl.user_score_sort+Token+classid+irid+xuekeid);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        GreadRankBean greadRankBean = gson.fromJson(s, GreadRankBean.class);
        if (greadRankBean.getCode() == 200) {
            List<GreadRankBean.BodyBean> body = greadRankBean.getBody();
            lvList.setAdapter(new ClassAllStuAdapter(ctx,body));
        }
    }
}
