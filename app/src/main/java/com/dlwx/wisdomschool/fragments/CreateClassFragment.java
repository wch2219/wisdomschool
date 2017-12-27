package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ClassDescActivity;
import com.dlwx.wisdomschool.activitys.CreateClassActivity;
import com.dlwx.wisdomschool.adapter.MeCreateCLassAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我创创建的班级
 */
public class CreateClassFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.ll_createentry)
    LinearLayout llCreateentry;
    @BindView(R.id.lv_list)
    ListView lvList;
    Unbinder unbinder;

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

        MeCreateCLassAdapter meCreateCLassAdapter = new MeCreateCLassAdapter(ctx);
        lvList.setAdapter(meCreateCLassAdapter);
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

    @OnClick(R.id.btn_create)
    public void onViewClicked() {
        startActivity(new Intent(ctx,CreateClassActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ctx,ClassDescActivity.class);
        startActivity(intent);

    }
}
