package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建班级完成
 */
public class CreateClassCompleteActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_inclass)
    TextView tvInclass;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.ll_QQ)
    LinearLayout llQQ;
    @BindView(R.id.ll_WX)
    LinearLayout llWX;
    @BindView(R.id.btn_invite)
    Button btnInvite;
    @BindView(R.id.tv_classname)
    TextView tvClassname;
    @BindView(R.id.tv_classcode)
    TextView tvClasscode;
    private String classcode;//班级号
    private String classname;
    private String filepath;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        classcode = intent.getStringExtra("classcode");
        classname = intent.getStringExtra("classname");
        filepath = intent.getStringExtra("filepath");
        setContentView(R.layout.activity_create_class_complete);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级属性");
        initTabBar(toolBar);
        tvClassname.setText(classname);
        tvClasscode.setText("班级号:"+classcode);
        Glide.with(ctx).load(filepath).into(ivPic);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_inclass, R.id.ll_QQ, R.id.ll_WX, R.id.btn_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_inclass://进入班级
                startActivity(new Intent(ctx, ClassDescActivity.class));
                break;
            case R.id.ll_QQ://从QQ邀请
                break;
            case R.id.ll_WX://从微信邀请
                break;
            case R.id.btn_invite://更多邀请方式
                break;
        }
    }

}
