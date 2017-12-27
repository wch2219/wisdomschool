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
import com.dlwx.wisdomschool.adapter.PatriarchExamStageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 家长考试
 */
public class PatriarchExamActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_certificate)
    TextView tvCertificate;
    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_patriarch_exam);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("阶段考试");
        initTabBar(toolBar);
        lvList.setAdapter(new PatriarchExamStageAdapter(ctx));
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.tv_certificate)
    public void onViewClicked() {
        startActivity(new Intent(ctx,MyCertificateActivity.class));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            startActivity(new Intent(ctx,ChoiceDifficultyExamActivity.class));
    }
}
