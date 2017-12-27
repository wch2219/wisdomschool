package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布信息完成
 */
public class PublishCompleteActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.cv_morality)
    CardView cvMorality;
    @BindView(R.id.cv_learn)
    CardView cvLearn;
    @BindView(R.id.cv_sports)
    CardView cvSports;
    @BindView(R.id.cv_startwork)
    CardView cvStartwork;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_publish_complete);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("综合素质纪录");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.cv_morality, R.id.cv_learn, R.id.cv_sports, R.id.cv_startwork})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_morality:
                startActivity(new Intent(ctx,SynthesizeFullActivity.class));
                break;
            case R.id.cv_learn:
                break;
            case R.id.cv_sports:
                break;
            case R.id.cv_startwork:
                break;
        }
    }
}
