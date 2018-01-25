package com.dlwx.wisdomschool.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.WorkDescActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作业
 */
public class WorkFragment extends BaseFragment {
    @BindView(R.id.ll_essay)
    LinearLayout llEssay;
    @BindView(R.id.ll_stuwork)
    LinearLayout llStuwork;
    @BindView(R.id.ll_habit)
    LinearLayout llHabit;
    Unbinder unbinder;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_essay, R.id.ll_stuwork, R.id.ll_habit})
    public void onViewClicked(View view) {
        Intent   intent = new Intent(ctx, WorkDescActivity.class);
        switch (view.getId()) {
            case R.id.ll_essay://文章
                intent.putExtra("title","家长作业");
                intent.putExtra("type","7");
                startActivity(intent);
                break;
            case R.id.ll_stuwork://学生作业
                intent.putExtra("title","学生作业");
                intent.putExtra("type","6");
                startActivity(intent);
                break;
            case R.id.ll_habit://习惯
                intent.putExtra("title","习惯养成");
                intent.putExtra("type","5");
                startActivity(intent);
                break;
        }
    }
}
