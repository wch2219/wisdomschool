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
import com.dlwx.wisdomschool.adapter.GradeAllMemberListAdapter;
import com.dlwx.wisdomschool.bean.ClassDescBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 某人全部成绩分析
 */
public class SomeOneAllGradeAnalyzeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private String cnid;
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    private String year;
    private String xuekeid;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        year = intent.getStringExtra("year");
        cnid = intent.getStringExtra("cnid");
        xuekeid = intent.getStringExtra("xuekeid");

        setContentView(R.layout.activity_grade_analyze_all_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成绩分析");
        initTabBar(toolBar);
//        lvList.setAdapter(new YearAllStuAnalyzeAdapter(ctx));
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classid", cnid);
        mPreenter.fetch(map, true, HttpUrl.classdesc, HttpUrl.classdesc + cnid + Token);
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
        ClassDescBean.BodyBean.AddUserBean addUserBean = add_user.get(i);
        Intent intent = new Intent(ctx, GradeAnalyzeMapActivity.class);
        intent.putExtra("cnid",cnid).putExtra("year",year).putExtra("xuekeid",xuekeid).putExtra("userid",addUserBean.getUserid()).putExtra("name",addUserBean.getJoin_role());
        startActivity(intent);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ClassDescBean classDescBean = gson.fromJson(s, ClassDescBean.class);
        if (classDescBean.getCode() == 200) {
            ClassDescBean.BodyBean body = classDescBean.getBody();
            add_user = body.getAdd_user();
            lvList.setAdapter(new GradeAllMemberListAdapter(ctx, add_user));
        }
    }
}
