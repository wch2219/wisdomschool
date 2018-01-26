package com.dlwx.wisdomschool.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AddSeachClassActivity;
import com.dlwx.wisdomschool.activitys.ClassDescActivity;
import com.dlwx.wisdomschool.activitys.MyJoinClassDescActivity;
import com.dlwx.wisdomschool.adapter.MeAddCLassAdapter;
import com.dlwx.wisdomschool.bean.ClassListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dlwx.wisdomschool.base.MyApplication.Token;
/**
 * 我加入的班级
 */
public class AddClassFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.btn_addclass)
    Button btnAddclass;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.btn_noentryadd)
    Button btnNoentryadd;
    @BindView(R.id.ll_noentry)
    LinearLayout llNoentry;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private List<ClassListBean.BodyBean> body;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_add_class;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
            initrefresh(refreshLayout,true);

    }

    @Override
    public void onResume() {
        getClassList();
        super.onResume();
    }

    @Override
    public void downOnRefresh() {
        getClassList();
    }

    /**
     * 获取班级列表
     */
    private void getClassList() {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("type","2");
        mPreenter.fetch(map,true, HttpUrl.Classroom,HttpUrl.Classroom+Token+"1");
    }
    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
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

    @OnClick({R.id.btn_addclass, R.id.btn_noentryadd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_addclass:
                startActivity(new Intent(ctx,AddSeachClassActivity.class));
                break;
            case R.id.btn_noentryadd:
                startActivity(new Intent(ctx,AddSeachClassActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //判断可发消息还是不可发消息
        ClassListBean.BodyBean bodyBean = body.get(i);
        if (bodyBean.getIs_guanli() == 1) {//可以管理班级，2不可以管理
            Intent intent = new Intent(ctx, ClassDescActivity.class);
            intent.putExtra("classid",bodyBean.getCnid());
            startActivity(intent);
        }else{
            Intent intent = new Intent(ctx, MyJoinClassDescActivity.class);
            intent.putExtra("classid",bodyBean.getCnid());
            startActivity(intent);
        }

    }
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ClassListBean classListBean = gson.fromJson(s, ClassListBean.class);
        if (classListBean.getCode() == 200) {
            body = classListBean.getBody();
            if (body != null | body.size() != 0) {
                llNoentry.setVisibility(View.VISIBLE);
                llEntry.setVisibility(View.GONE);
                MeAddCLassAdapter meCreateCLassAdapter = new MeAddCLassAdapter(ctx, body);
                lvList.setAdapter(meCreateCLassAdapter);
            }else{
                llEntry.setVisibility(View.VISIBLE);
                llNoentry.setVisibility(View.GONE);
            }

        }
    }
}
