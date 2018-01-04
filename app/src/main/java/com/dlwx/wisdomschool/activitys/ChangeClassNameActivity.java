package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
 * 修改班级名称
 */
public class ChangeClassNameActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @Override
    protected void initView() {
        String classname = getIntent().getStringExtra("classname");
        setContentView(R.layout.activity_change_class_name);
        ButterKnife.bind(this);
        etName.setText(classname);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("修改班级名称");
        int length = etName.getText().toString().trim().length();
        etName.setSelection(length);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.btn_create)
    public void onViewClicked() {
        Toast.makeText(ctx, "修改成功", Toast.LENGTH_SHORT).show();
        String className = etName.getText().toString().trim();
        if (TextUtils.isEmpty(className)) {
            Toast.makeText(ctx, "班级名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("classname",className);
        setResult(10,intent);
        finish();
    }
}
