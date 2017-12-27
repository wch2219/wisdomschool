package com.dlwx.wisdomschool.activitys;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PublishUpPicPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发布综合素质
 */
public class PublishUpPicActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.vp_view)
    ViewPager vpView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_publish_up_pic);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("发布综合素质");
            initTabBar(toolBar);
        vpView.setAdapter(new PublishUpPicPageAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
