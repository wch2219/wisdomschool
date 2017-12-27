package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 综合素质大提升
 */
public class SynthesizeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.btn_start)
    Button btnStart;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_synthesize);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("综合素质首页");
            initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        startActivity(new Intent(ctx,PublishUpPicActivity.class));
    }
}
