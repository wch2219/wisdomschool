package com.dlwx.wisdomschool.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.fragments.PersionLableFragment;
import com.dlwx.wisdomschool.fragments.SynthesizeLableFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加标签
 */
public class AddLableActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_lable);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("添加成长标签");
            initTabBar(toolBar);
            fragments.add(new SynthesizeLableFragment());
            fragments.add(new PersionLableFragment());
            changeFragment(0);
    }

    @Override
    protected void initListener() {
            tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        changeFragment(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    private Fragment fragment;
    private FragmentTransaction transaction;
    private Fragment lastFragment;

    private void changeFragment(int i) {

        transaction = getSupportFragmentManager().beginTransaction();
        // 上一个不为空 隐藏上一个
        if (lastFragment != null&& lastFragment != fragments.get(i)) {
            transaction.hide(lastFragment);
//            transaction.remove(lastFragment);
        }

        fragment = fragments.get(i);
        // fragment不能重复添加 // 添加过 显示 没有添加过 就隐藏
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_content, fragment);
        }
        transaction.commitAllowingStateLoss();
        lastFragment = fragment;
    }
}
