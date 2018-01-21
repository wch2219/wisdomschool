package com.dlwx.baselib.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.dlwx.baselib.R;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.baselib.utiles.MyProgressLoading;
import com.dlwx.baselib.utiles.SpUtiles;
import com.dlwx.baselib.view.ViewInterface;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yinglan.keyboard.HideUtil;


/**
 * Created by Administrator on 2017/8/12/012.
 */

public abstract class BaseFragment<V,T extends Presenter<V>> extends Fragment implements ViewInterface {
    public Context ctx;
    public T mPreenter;
    public MyProgressLoading loading;
    public SharedPreferences sp;
    public int HttpType;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ctx = getActivity();
        View view = inflater.inflate(getLayoutID(),null);
        mPreenter = createPresenter();
        loading = new MyProgressLoading(ctx, R.style.DialogStyle);
        sp = ctx.getSharedPreferences(SpUtiles.SP_Mode,Context.MODE_PRIVATE);
        HideUtil.init(getActivity());
        initView(view);
        initDate();
        initListener();

        return view;
    }
    /**
     * 获得布局id
     * @return
     */
    public abstract int getLayoutID();

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initDate();

    /**
     * 时间监听
     */
    protected abstract void initListener();

    @Override
    public void showLoading() {
        loading.show();
    }

    @Override
    public void showData(String s) {

    }
    public void wch(Object o){
        LogUtiles.LogI(o+"");
    }
    @Override
    public void disLoading() {
        loading.dismiss();
    }
    protected abstract T createPresenter();
    @Override
    public void onError() {
        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
        loading.dismiss();
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 下拉刷新基本设置
     * @param refreshLayout
     * @param isLoadmore 是否启用下拉刷新
     */
    public void initrefresh (SmartRefreshLayout refreshLayout, boolean isLoadmore){
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        if (isLoadmore) {

            refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        }else{
            refreshLayout.setEnableLoadmore(false);//是否启用上拉加载功能
        }
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setRefreshHeader(new WaterDropHeader(ctx));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new FalsifyFooter(ctx));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
                downOnRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
                loadmore();
            }
        });
    }
    /**
     * 下拉刷新
     */
    public void downOnRefresh(){

    }

    /**
     * 上拉加载更多
     */
    public void loadmore(){

    }

}
