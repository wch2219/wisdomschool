package com.dlwx.wisdomschool.activitys;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.fragments.RecordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 成长纪录
 */
public class RecordActivity extends BaseActivity {
    @BindView(R.id.fl_record_activity)
    FrameLayout flRecordActivity;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {

        getSupportFragmentManager().beginTransaction().add(R.id.fl_record_activity, new RecordFragment()).commit();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
}
