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
import com.dlwx.wisdomschool.adapter.MeAddCLassAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
        MeAddCLassAdapter meaddCLassAdapter = new MeAddCLassAdapter(ctx);
        lvList.setAdapter(meaddCLassAdapter);
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
}
