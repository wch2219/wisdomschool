package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 您的专属二维码
 */
public class ExclusiveCodeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_savetophone)
    Button btnSavetophone;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_exclusive_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("您的专属二维码");
            initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_savetophone)
    public void onViewClicked() {

    }
}
