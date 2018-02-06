package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.MermberMessBean;
import com.dlwx.wisdomschool.utiles.GlideuploadUtils;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 我i加入的班级 个人信息
 */
public class MyJoinClassPersionMessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_privatechat)
    LinearLayout llPrivatechat;
    private int tag;
    private String jcid;
    private String classid;
    private MermberMessBean.BodyBean body;
    private String userid;

    @Override
    protected void initView() {
        tag = getIntent().getIntExtra("tag", 0);
        jcid = getIntent().getStringExtra("jcid");
        classid = getIntent().getStringExtra("classid");
        userid = getIntent().getStringExtra("userid");
        setContentView(R.layout.activity_my_join_class_persion_mess);
        ButterKnife.bind(this);
        if (tag == 0) {
            llPrivatechat.setVisibility(View.VISIBLE);
        }else{
            llPrivatechat.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        tvTitle.setText("个人信息");
        initTabBar(toolBar);
        wch(userid);
        getData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.ll_privatechat)
    public void onViewClicked() {
        Intent intent = new Intent(ctx,ChatActivity.class);
        intent.putExtra("title",body.getJoin_role());
        intent.putExtra(SpUtiles.OtherHeadPic,body.getHeader_pic());
        intent.putExtra(SpUtiles.OtherNickName,body.getJoin_role());
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userid);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        startActivity(intent);
    }

    /**
     * 获取联系人数据
     */
    private void getData() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        if (TextUtils.isEmpty(userid)) {

            map.put("jcid", jcid);
        }else{
            map.put("userid",userid);
        }
        mPreenter.fetch(map, true, HttpUrl.LookMemberMess, null);
    }
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        if (HttpType == 1) {

            mermbermess(s);
        }
    }

    private void mermbermess(String s) {
        Gson gson = new Gson();
        try {
            MermberMessBean mermberMessBean = gson.fromJson(s, MermberMessBean.class);
            if (mermberMessBean.getCode() == 200) {
                body = mermberMessBean.getBody();
                GlideuploadUtils.glideUPload(ctx, body.getHeader_pic()).into(ivHead);
                tvName.setText(body.getJoin_role());
            } else {
                Toast.makeText(ctx, mermberMessBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            //
//            Toast.makeText(ctx, "解析其他内容", Toast.LENGTH_SHORT).show();
        }
    }
}
