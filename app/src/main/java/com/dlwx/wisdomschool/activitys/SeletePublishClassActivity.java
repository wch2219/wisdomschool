package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.RecordScreenAddAdapter;
import com.dlwx.wisdomschool.adapter.RecordScreenCreateAdapter;
import com.dlwx.wisdomschool.adapter.RecordScreenPrivateAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择发布的班级
 */
public class SeletePublishClassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_sendsms)
    TextView tvSendsms;
    @BindView(R.id.lv_listcreate)
    MyListView lvListcreate;
    @BindView(R.id.tv_mycreatenum)
    TextView tvMycreatenum;
    @BindView(R.id.lv_listadd)
    MyListView lvListadd;
    @BindView(R.id.lv_listprivacy)
    MyListView lvListprivacy;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_selete_publish_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择要接收的班级");
        initTabBar(toolBar);
        lvListcreate.setAdapter(new RecordScreenCreateAdapter(ctx));
        lvListadd.setAdapter(new RecordScreenAddAdapter(ctx));
        lvListprivacy.setAdapter(new RecordScreenPrivateAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.tv_sendsms)
    public void onViewClicked() {
        finish();
    }
}
