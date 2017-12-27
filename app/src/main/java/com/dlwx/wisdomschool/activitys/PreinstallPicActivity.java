package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PreinstallPicAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 预设图片
 */
public class PreinstallPicActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.gv_list)
    GridView gvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_preinstall_pic);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择一个预设班徽");
        initTabBar(toolBar);
        gvList.setAdapter(new PreinstallPicAdapter(ctx));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

}
