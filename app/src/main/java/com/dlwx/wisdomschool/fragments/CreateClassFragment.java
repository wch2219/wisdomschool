package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ClassDescActivity;
import com.dlwx.wisdomschool.activitys.CreateClassActivity;
import com.dlwx.wisdomschool.adapter.MeCreateCLassAdapter;
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
 * 我创创建的班级
 */
public class CreateClassFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.btn_entrycreate)
    Button btnEntryCreate;
    @BindView(R.id.ll_createentry)
    RelativeLayout llCreateentry;
    @BindView(R.id.rl_noentry)
    RelativeLayout rlNoentry;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_list)
    ListView lvList;
    Unbinder unbinder;
    Unbinder unbinder1;
    private List<ClassListBean.BodyBean> body;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_create_class;
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
        super.onResume();
        getClassList();
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
        map.put("type","1");
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
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        ClassListBean.BodyBean bodyBean = body.get(i);

        Intent intent = new Intent(ctx, ClassDescActivity.class);
        intent.putExtra("classid",bodyBean.getCnid());
        startActivity(intent);

    }
    @OnClick({R.id.btn_entrycreate, R.id.btn_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_entrycreate:
                startActivity(new Intent(ctx, CreateClassActivity.class));
                break;
            case R.id.btn_create:
                startActivity(new Intent(ctx, CreateClassActivity.class));
                break;
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
            if (body != null| body.size() != 0) {
            MeCreateCLassAdapter meCreateCLassAdapter = new MeCreateCLassAdapter(ctx, body);
            lvList.setAdapter(meCreateCLassAdapter);
            }else{
                llCreateentry.setVisibility(View.VISIBLE);
                rlNoentry.setVisibility(View.GONE);
            }
        }
    }
}
