package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建班级
 */
public class CreateClassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.rl_pic)
    RelativeLayout rlPic;
    @BindView(R.id.et_classname)
    EditText etClassname;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_create_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("创建班级");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.iv_pic, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pic:
                break;
            case R.id.btn_next:
                startActivity(new Intent(ctx,ClassPropertyActivity.class));
                break;
        }
    }
}
