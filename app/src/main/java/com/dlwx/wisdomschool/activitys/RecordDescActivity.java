package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 纪录详情
 */
public class RecordDescActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_sendsms)
    TextView tvSendsms;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_record_desc);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("纪录详情");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.iv_face, R.id.tv_sendsms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_face:
                break;
            case R.id.tv_sendsms:
                break;
        }
    }
}
