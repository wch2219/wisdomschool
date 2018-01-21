package com.dlwx.wisdomschool.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AddActionActivity;
import com.dlwx.wisdomschool.activitys.AgeWeeklyActivity;
import com.dlwx.wisdomschool.activitys.SendNotifiActivity;
import com.dlwx.wisdomschool.adapter.HomeItmeAdapter;
import com.dlwx.wisdomschool.adapter.HomeTitleAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.HomeListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment implements HomeItmeAdapter.ItemOnClickListener, BaseRecrviewAdapter.OnItemClickListener {
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
    private List<HomeListBean.BodyBean> body;

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
        int TeacherOrPatriarch = sp.getInt(SpUtiles.TeacherOrPatriarch, 1);
        if (TeacherOrPatriarch == 2) {
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
        LinearLayoutManager itemManager = new LinearLayoutManager(ctx, LinearLayout.VERTICAL, false);
        rvRecyclerview.setLayoutManager(itemManager);

    }

    @Override
    public void onResume() {
        getDataList();
        super.onResume();
    }

    private void getDataList() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        HttpType = 1;
        mPreenter.fetch(map, true, HttpUrl.HomeList, HttpUrl.HomeList + Token);
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
                getDataList();

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();

            }
        });

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
        startActivity(new Intent(ctx, SendNotifiActivity.class));

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
        if (HttpType == 1) {
            HomeListBean homeListBean = gson.fromJson(s, HomeListBean.class);
            if (homeListBean.getCode() == 200) {
                body = homeListBean.getBody();
                homeItmeAdapter = new HomeItmeAdapter(ctx, body);
                rvRecyclerview.setAdapter(homeItmeAdapter);
                homeItmeAdapter.setItemOnClickListener(this);
                homeItmeAdapter.setOnItemClickListener(this);
            } else {
                Toast.makeText(ctx, homeListBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        } else if (HttpType == 2) {//已阅
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {

            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    public void setOnClick(int position) {
        HomeListBean.BodyBean bodyBean = body.get(position);
        int theme = bodyBean.getTheme();
        Intent intent;
        switch (theme) {
            case 1://班级通知

                break;
            case 2://群组讨论

                break;
            case 3://调查问卷

                break;
            case 4://视频实时纪录

                break;
            case 5://活动收集
                intent = new Intent(ctx, AddActionActivity.class);
                intent.putExtra("id", bodyBean.getId());
                intent.putExtra("issend",bodyBean.getIs_send());
                ctx.startActivity(intent);
                break;
            case 6://布置作业

                break;
            case 7://文章

                break;
            case 10://周刊

                break;
        }
    }

    /**
     * 是否阅读
     *
     * @param id
     */
    @Override
    public void clickYue(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("id", id);
        HttpType = 2;
        map.put("type", "2");
        mPreenter.fetch(map, true, HttpUrl.Situation, "");
    }
}
