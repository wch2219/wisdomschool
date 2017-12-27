package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.VideoExpAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 视频讲解
 */
public class VideoExplainActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_video_explain);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧驾校攻略区");
        initTabBar(toolBar);
        lvList.setAdapter(new VideoExpAdapter(ctx));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(ctx,VideoDescActivity.class));
    }
}
