package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改称呼
 */
public class ChangeNamedActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_name)
    EditText etName;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_named);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("修改称呼");
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
