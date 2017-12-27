package com.dlwx.wisdomschool.activitys;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.fragments.FamialyRegisterFragment;
import com.dlwx.wisdomschool.fragments.RecordSummaryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 备注信息
 */
public class RemarkMessActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rb_famialy)
    RadioButton rbFamialy;
    @BindView(R.id.rb_hister)
    RadioButton rbHister;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.fl_contents)
    FrameLayout fl_contents;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_remark_mess);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("成员备注");
            fragments.add(new FamialyRegisterFragment());
            fragments.add(new RecordSummaryFragment());
    }

    @Override
    protected void initListener() {
        rgGroup.setOnCheckedChangeListener(this);
       rbFamialy.setChecked(true);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_famialy://家访登记表
                changeFragment(0);
                break;
            case R.id.rb_hister://纪录汇总
                changeFragment(1);
                break;
        }
    }
    private Fragment fragment;
    private FragmentTransaction transaction;
    private Fragment lastFragment;

    private void changeFragment(int i) {

        transaction = getSupportFragmentManager().beginTransaction();
        // 上一个不为空 隐藏上一个
        if (lastFragment != null) {
            transaction.hide(lastFragment);
//            transaction.remove(lastFragment);
        }

        fragment = fragments.get(i);
        // fragment不能重复添加 // 添加过 显示 没有添加过 就隐藏
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_contents, fragment);
        }
        transaction.commitAllowingStateLoss();
        lastFragment = fragment;
    }
}
