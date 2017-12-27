package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SeleteOtherIdAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选则其他身份
 */
public class SeleteOtherIdActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.gv_list)
    GridView gvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_selete_other_id);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("保存");
            initTabBar(toolBar);
            gvList.setAdapter(new SeleteOtherIdAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        finish();
    }
}
