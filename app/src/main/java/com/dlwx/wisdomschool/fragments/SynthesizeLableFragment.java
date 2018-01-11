package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AddLableAdapter;
import com.dlwx.wisdomschool.bean.TagListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

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

        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("type","1");
        mPreenter.fetch(map,true, HttpUrl.Signlist,HttpUrl.Signlist+Token+"1");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        TagListBean tagListBean = gson.fromJson(s, TagListBean.class);
        if (tagListBean.getCode() == 200) {
            final List<TagListBean.BodyBean> body = tagListBean.getBody();
            AddLableAdapter addLableAdapter = new AddLableAdapter(ctx, body);
            lvList.setAdapter(addLableAdapter);
            addLableAdapter.setOnItemClickListener(new BaseRecrviewAdapter.OnItemClickListener() {
                @Override
                public void setOnClick(int position) {
                    Intent intent = new Intent();
                    intent.putExtra("tag",body.get(position).getSigname());
                    intent.putExtra("quality_sign",body.get(position).getId());
                    getActivity().setResult(10,intent);
                    getActivity().finish();
                }
            });

        }else{
            Toast.makeText(ctx, tagListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
