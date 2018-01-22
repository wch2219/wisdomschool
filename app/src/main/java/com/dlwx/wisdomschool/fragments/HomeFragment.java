package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AgeWeeklyActivity;
import com.dlwx.wisdomschool.adapter.HomeTitleAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_across)
    RecyclerView rvAcross;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private String[] strs;
    private HomeTitleAdapter titleAdapter;
    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
        initrefresh(refreshLayout,true);
        strs = ctx.getResources().getStringArray(R.array.hometitle);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        rvAcross.setLayoutManager(manager);
        titleAdapter = new HomeTitleAdapter(ctx, strs);
        rvAcross.setAdapter(titleAdapter);
    }

    @Override
    protected void initListener() {
        titleAdapter.setOnItemClickListener(titAdapter);
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

    /**
     * 头部条目点击事件
     */
    private BaseRecrviewAdapter.OnItemClickListener titAdapter = new BaseRecrviewAdapter.OnItemClickListener() {
        @Override
        public void setOnClick(int position) {
            //TODO
            Intent intent = new Intent(ctx, AgeWeeklyActivity.class);
            intent.putExtra("age", position+1+"");
            startActivity(intent);
        }
    };
}
