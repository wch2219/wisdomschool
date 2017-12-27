package com.dlwx.wisdomschool.activitys;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.MyClassMemberAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的班级成员
 */
public class MyClassMemberActivity extends BaseActivity implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_seach)
    EditText etSeach;
    @BindView(R.id.ablelist)
    ExpandableListView ablelist;
    @BindView(R.id.tv_aff)
    TextView tvAff;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_class_member);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择要添加的成员");
        initTabBar(toolBar);
        ablelist.setAdapter(new MyClassMemberAdapter(ctx));
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        ablelist.setIndicatorBounds(width - 150, width - 40);
    }

    @Override
    protected void initListener() {
        ablelist.setOnGroupClickListener(this);
        ablelist.setOnChildClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {


        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

        return false;
    }

    @OnClick(R.id.tv_aff)
    public void onViewClicked() {
    }
}
