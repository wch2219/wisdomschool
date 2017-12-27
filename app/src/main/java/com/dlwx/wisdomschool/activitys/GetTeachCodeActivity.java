package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 获取教师邀请码
 */
public class GetTeachCodeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_schoolname)
    EditText etSchoolname;
    @BindView(R.id.btn_apply)
    Button btnApply;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_get_teach_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("申请教师邀请码");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        Toast.makeText(ctx, "申请成功，请耐心等待", Toast.LENGTH_SHORT).show();
        finish();
    }
}
