package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 申请学校培训
 */
public class SchoolTrainActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.rl_pro)
    RelativeLayout rlPro;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.rl_city)
    RelativeLayout rlCity;
    @BindView(R.id.et_schoolname)
    EditText etSchoolname;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_duty)
    EditText etDuty;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_school_train);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("在线申请预约讲座");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.rl_pro, R.id.rl_city, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_pro:
                break;
            case R.id.rl_city:
                break;
            case R.id.btn_submit:
                finish();
                break;
        }
    }
}
