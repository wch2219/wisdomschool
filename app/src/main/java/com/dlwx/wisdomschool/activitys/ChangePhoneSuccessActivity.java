package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更换手机号成功
 */
public class ChangePhoneSuccessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_phone_success);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("更换成功");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
