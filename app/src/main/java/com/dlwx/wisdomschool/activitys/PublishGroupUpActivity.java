package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PublishLableAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布成长
 */
public class PublishGroupUpActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_aff)
    TextView tvAff;
    @BindView(R.id.ll_seleteclass)
    LinearLayout llSeleteclass;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_addlabel)
    TextView tvAddlabel;
    @BindView(R.id.gv_list)
    RecyclerView gvList;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.iv_send)
    ImageView ivSend;
    @BindView(R.id.iv_album)
    ImageView ivAlbum;
    @BindView(R.id.ll_album)
    LinearLayout llAlbum;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_camera)
    LinearLayout llCamera;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_publish_group_up);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("发送智慧成长");
        initTabBar(toolBar);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        gvList.setLayoutManager(manager);
        gvList.setAdapter(new PublishLableAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_aff, R.id.ll_seleteclass, R.id.iv_pic, R.id.tv_addlabel, R.id.iv_face, R.id.iv_send, R.id.ll_album, R.id.ll_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_aff://发送
                break;
            case R.id.ll_seleteclass://选择班级
                startActivity(new Intent(ctx, SeletePublishClassActivity.class));
                break;
            case R.id.iv_pic://添加图片
                break;
            case R.id.tv_addlabel://添加标签
                startActivity(new Intent(ctx,AddLableActivity.class));
                break;
            case R.id.iv_face://表情
                break;
            case R.id.iv_send://发送想说的话
                break;
            case R.id.ll_album://相册
                break;

            case R.id.ll_camera://相机
                break;
        }
    }
}
