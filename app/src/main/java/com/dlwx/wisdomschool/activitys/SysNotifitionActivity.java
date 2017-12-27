package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SysyNotiAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统通知
 */
public class SysNotifitionActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_clean)
    TextView tvClean;
    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sys_notifition);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("系统通知");
        initTabBar(toolBar);
        lvList.setAdapter(new SysyNotiAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.tv_clean)
    public void onViewClicked() {
    }
}
