package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 改变身份类型
 */
public class ChangeIdTypeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_teacher)
    RelativeLayout rlTeacher;
    @BindView(R.id.rl_patriarch)
    RelativeLayout rlPatriarch;
    @BindView(R.id.btn_aff)
    Button btnAff;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_id_type);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("更改身份");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.rl_teacher, R.id.rl_patriarch, R.id.btn_aff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_teacher:
                break;
            case R.id.rl_patriarch:
                break;
            case R.id.btn_aff:
                finish();
                break;
        }
    }
}
