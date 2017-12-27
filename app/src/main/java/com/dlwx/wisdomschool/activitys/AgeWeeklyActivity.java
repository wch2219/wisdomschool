package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AgeWeekHeadAdapter;
import com.dlwx.wisdomschool.adapter.AgeWeeklyAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 0到19岁周刊
 */
public class AgeWeeklyActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_seach)
    EditText etSeach;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ViewHolderHead vhHead;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_age_weekly);
        ButterKnife.bind(this);
        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadmore(true);//是否启用上拉加载功能
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        refreshLayout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
        View headView = LayoutInflater.from(ctx).inflate(R.layout.head_ageweekly, null);
        vhHead = new ViewHolderHead(headView);
        lvList.addHeaderView(headView);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        vhHead.gv_list.setAdapter(new AgeWeekHeadAdapter(ctx));
        lvList.setAdapter(new AgeWeeklyAdapter(ctx));
    }

    @Override
    protected void initListener() {
        refreshLayout.setRefreshHeader(new FalsifyHeader(ctx));
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
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ctx,WeeklyDescActivity.class);
    }

    private class ViewHolderHead {
        public View rootView;
        public MyGridView gv_list;

        public ViewHolderHead(View rootView) {
            this.rootView = rootView;
            this.gv_list = (MyGridView) rootView.findViewById(R.id.gv_list);
        }

    }
}
