package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.SeleteClassDiaUtiles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 班级属性
 */
public class ClassPropertyActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_schoolname)
    TextView tvSchoolname;
    @BindView(R.id.ll_school)
    LinearLayout llSchool;
    @BindView(R.id.ll_class)
    LinearLayout llClass;
    @BindView(R.id.tv_classname)
    TextView tv_classname;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_property);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级属性");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_school, R.id.ll_class, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_school:
                startActivity(new Intent(ctx,SchoolAddressActivity.class));
                break;
            case R.id.ll_class:
                showSeleteClassDia();
                break;
            case R.id.btn_complete:
                startActivity(new Intent(ctx,CreateClassCompleteActivity.class));
                break;
        }
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

                tv_classname.setText(s);
            }
        }
    };
}
