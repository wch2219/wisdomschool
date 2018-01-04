package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AddSeachClassActivity;
import com.dlwx.wisdomschool.adapter.MeCreateCLassAdapter;
import com.dlwx.wisdomschool.bean.ClassListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

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
public class AddClassFragment extends BaseFragment {
    @BindView(R.id.btn_addclass)
    Button btnAddclass;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.btn_noentryadd)
    Button btnNoentryadd;
    @BindView(R.id.ll_noentry)
    LinearLayout llNoentry;
    Unbinder unbinder;

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
    public void showData(String s) {
        disLoading();
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ClassListBean classListBean = gson.fromJson(s, ClassListBean.class);
        if (classListBean.getCode() == 200) {
            List<ClassListBean.BodyBean> body = classListBean.getBody();
            if (body != null |body.size() != 0) {

                MeCreateCLassAdapter meCreateCLassAdapter = new MeCreateCLassAdapter(ctx,body);
                lvList.setAdapter(meCreateCLassAdapter);
            }else{
                llEntry.setVisibility(View.VISIBLE);
                llNoentry.setVisibility(View.GONE);
            }

        }
    }
}
