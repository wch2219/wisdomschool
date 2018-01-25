package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AgeWeeklyActivity;
import com.dlwx.wisdomschool.activitys.WebUrlActivity;
import com.dlwx.wisdomschool.adapter.HomeItemAdapter;
import com.dlwx.wisdomschool.adapter.HomeTitleAdapter;
import com.dlwx.wisdomschool.bean.HomelistBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements BaseRecrviewAdapter.OnItemClickListener{
    @BindView(R.id.rv_across)
    RecyclerView rvAcross;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private String[] strs;
    private HomeTitleAdapter titleAdapter;
    private List<HomelistBean.BodyBean> body;

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
        getData();
    }

    @Override
    public void downOnRefresh() {
        getData();
    }

    private void getData() {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HttpUrl.HomeList,HttpUrl.HomeList+Token);
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

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        HomelistBean homelistBean = gson.fromJson(s, HomelistBean.class);
        if (homelistBean.getCode() == 200) {
            body = homelistBean.getBody();
            LinearLayoutManager manager = new LinearLayoutManager(ctx);
            manager.setOrientation(LinearLayout.VERTICAL);
            rvRecyclerview.setLayoutManager(manager);
            HomeItemAdapter homeItemAdapter = new HomeItemAdapter(ctx, body, refreshLayout);
            rvRecyclerview.setAdapter(homeItemAdapter);
            homeItemAdapter.setOnItemClickListener(this);
        }else{
            Toast.makeText(ctx, homelistBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setOnClick(int position) {

        HomelistBean.BodyBean bodyBean = body.get(position);
        if (!TextUtils.isEmpty(bodyBean.getUrl())) {
            Intent intent = new Intent(ctx, WebUrlActivity.class);
            intent.putExtra("url",bodyBean.getUrl());
            intent.putExtra("unTitle",true);
            startActivity(intent);
        }

    }
}
