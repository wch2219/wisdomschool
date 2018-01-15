package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassGradeAdapter;
import com.dlwx.wisdomschool.bean.ClassListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

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
    private List<ClassListBean.BodyBean> body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_grade_manage);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("学生成绩管理");
        initTabBar(toolBar);

        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("type","1");
        mPreenter.fetch(map,true, HttpUrl.Classroom,HttpUrl.Classroom+Token+"1");

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
        ClassListBean.BodyBean bodyBean = body.get(i);
        Intent intent;
        if (i1 == 0) {
            intent = new Intent(ctx,GradeRankingActivity.class);
            intent.putExtra("cnid",bodyBean.getCnid());
            startActivity(intent);
        }else {
            intent = new Intent(ctx,GradeAnalyzeAllListActivity.class);
            intent.putExtra("cnid",bodyBean.getCnid());
            startActivity(intent);
        }
        return true;
    }
    @OnClick(R.id.ll_create)
    public void onViewClicked() {

            startActivity(new Intent(ctx,SeleteClassActivity.class));
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ClassListBean classListBean = gson.fromJson(s, ClassListBean.class);
        if (classListBean.getCode() == 200) {
            body = classListBean.getBody();
            if (body != null | body.size() != 0) {
                int width = getWindowManager().getDefaultDisplay().getWidth();
                lvList.setIndicatorBounds(width - 100, width - 10);
                lvList.setAdapter(new ClassGradeAdapter(ctx, body));

            } else {

            }
        }else{
            Toast.makeText(ctx, classListBean.getResult(), Toast.LENGTH_SHORT).show();
        }

    }
}
