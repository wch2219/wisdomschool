package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.SeleteClassDiaUtiles;
import com.dlwx.wisdomschool.views.SeleteHeadDiautils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 班级信息
 */
public class ClassMessageActivitry extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_classname)
    TextView tvClassname;
    @BindView(R.id.ll_classname)
    LinearLayout llClassname;
    @BindView(R.id.tv_school)
    TextView tvSchool;
    @BindView(R.id.ll_schoolname)
    LinearLayout llSchoolname;
    @BindView(R.id.tv_realclass)
    TextView tvRealclass;
    @BindView(R.id.ll_realclass)
    LinearLayout llRealclass;
    @BindView(R.id.swich)
    Switch swich;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_message_activitry);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("班级信息");

    }

    @Override
    protected void initListener() {
        swich.setOnCheckedChangeListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_head, R.id.ll_classname, R.id.ll_schoolname, R.id.ll_realclass})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_head://班徽
                showSeleteHead();
                break;
            case R.id.ll_classname://班级名称
                intent = new Intent(ctx, ChangeClassNameActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_schoolname://所属学校
                intent = new Intent(ctx, SchoolAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_realclass://真实班级
                showSeleteClassDia();
                break;
        }
    }

    /**
     * 选择班徽
     */
    private void showSeleteHead() {
        SeleteHeadDiautils headDiautils = new SeleteHeadDiautils(ctx);
        headDiautils.init();

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        //TODO
    }

    /**
     * 显示选择班级的窗体
     */
    private void showSeleteClassDia() {
        SeleteClassDiaUtiles seleteClassDiaUtiles = new SeleteClassDiaUtiles(ctx);
        seleteClassDiaUtiles.init(ctx);
        seleteClassDiaUtiles.setSeleteResult(seleteResult);
    }

    private SeleteClassDiaUtiles.SeleteResult seleteResult = new SeleteClassDiaUtiles.SeleteResult() {
        @Override
        public void setResult(String s) {
            if (!TextUtils.isEmpty(s)) {

                tvRealclass.setText(s);
            }
        }
    };
}
