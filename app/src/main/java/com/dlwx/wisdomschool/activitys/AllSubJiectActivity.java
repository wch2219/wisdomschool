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
import com.dlwx.wisdomschool.adapter.AllSubJiectAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全部学科成绩排名
 */
public class AllSubJiectActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_all_sub_kiect);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成绩排名");
        initTabBar(toolBar);
        lvList.setAdapter(new AllSubJiectAdapter(ctx));
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
        startActivity(new Intent(ctx,ClassAllStuActivity.class));
//        startActivity(new Intent(ctx,GradeActivity.class));
    }
}
