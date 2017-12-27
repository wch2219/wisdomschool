package com.dlwx.wisdomschool.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AgeWeeklyActivity;
import com.dlwx.wisdomschool.activitys.SendNotifiActivity;
import com.dlwx.wisdomschool.adapter.HomeItmeAdapter;
import com.dlwx.wisdomschool.adapter.HomeTitleAdapter;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_across)
    RecyclerView rvAcross;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.flbtn_edit)
    Button flbtnEdit;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private HomeTitleAdapter titleAdapter;
    private HomeItmeAdapter homeItmeAdapter;
    private String[] strs;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;

    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        int TeacherOrPatriarch = sp.getInt(SpUtiles.TeacherOrPatriarch, 0);
        if (TeacherOrPatriarch == 1) {
            flbtnEdit.setVisibility(View.GONE);
        }
    }
    @Override
    protected void initDate() {
        strs = ctx.getResources().getStringArray(R.array.hometitle);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        rvAcross.setLayoutManager(manager);
        titleAdapter = new HomeTitleAdapter(ctx, strs);
        rvAcross.setAdapter(titleAdapter);
        LinearLayoutManager itemManager = new LinearLayoutManager(ctx);
        rvRecyclerview.setLayoutManager(itemManager);
        homeItmeAdapter = new HomeItmeAdapter(ctx);
        rvRecyclerview.setAdapter(homeItmeAdapter);
    }

    @Override
    protected void initListener() {
        titleAdapter.setOnItemClickListener(titAdapter);
        refreshLayout.setRefreshHeader(new WaterDropHeader(ctx));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new FalsifyFooter(ctx));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();

            }
        });
        homeItmeAdapter.setOnItemClickListener(titAdapter);
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

    @OnClick(R.id.flbtn_edit)
    public void onViewClicked() {
        startActivity(new Intent(ctx,SendNotifiActivity.class));

    }

    /**
     * 头部条目点击事件
     */
    private BaseRecrviewAdapter.OnItemClickListener titAdapter = new BaseRecrviewAdapter.OnItemClickListener() {
        @Override
        public void setOnClick(int position) {
            //TODO
            Intent intent = new Intent(ctx,AgeWeeklyActivity.class);
            intent.putExtra("age",strs[position]);
            startActivity(intent);
        }
    };

}
