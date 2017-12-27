package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SeleteClassAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择班级
 */
public class SeleteClassActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_createclass)
    LinearLayout llCreateclass;
    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_selete_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择班级");
        initTabBar(toolBar);
        lvList.setAdapter(new SeleteClassAdapter(ctx));
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
            startActivity(new Intent(ctx,CreateGradeActivity.class));
    }

    @OnClick(R.id.ll_createclass)
    public void onViewClicked() {
        startActivity(new Intent(ctx,CreateClassActivity.class));
    }
}
