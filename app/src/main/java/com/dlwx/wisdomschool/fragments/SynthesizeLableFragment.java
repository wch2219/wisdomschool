package com.dlwx.wisdomschool.fragments;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AddLableAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 综合素质标签
 */
public class SynthesizeLableFragment extends BaseFragment {
    @BindView(R.id.rev_view)
    SwipeMenuRecyclerView lvList;
    Unbinder unbinder;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_synthesize_lable;
    }
    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        View foodView = LayoutInflater.from(ctx).inflate(R.layout.food_synthersizelable,null);
        lvList.addFooterView(foodView);
    }

    @Override
    protected void initDate() {
        LinearLayoutManager manager = new LinearLayoutManager(ctx, null,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lvList.setLayoutManager(manager);
        lvList.setAdapter(new AddLableAdapter(ctx));
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
}
