package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 智慧讨论
 */
public class DisCussActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_persionmanage)
    ImageView ivPersionmanage;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_speck)
    TextView tvSpeck;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.iv_send)
    ImageView ivSend;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_dis_cuss);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.iv_persionmanage, R.id.iv_type, R.id.tv_speck, R.id.iv_face, R.id.iv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_persionmanage:
                startActivity(new Intent(ctx,DisCussSettActivity.class));
                break;
            case R.id.iv_type:
                break;
            case R.id.tv_speck:
                break;
            case R.id.iv_face:
                break;
            case R.id.iv_send:
                break;
        }
    }
}
