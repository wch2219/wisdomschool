package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 聊天界面
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_persionmanage)
    ImageView ivPersionmanage;
    @BindView(R.id.rl_allmember)
    RelativeLayout rlAllmember;
    private String title;
    private String toChatUsername;
    private String classid;
    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        title = getIntent().getStringExtra("title");
        int isGroup = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, 2);
        Intent intent = getIntent();
        String otherheadpic = intent.getStringExtra(SpUtiles.OtherHeadPic);
        String othernickname = intent.getStringExtra(SpUtiles.OtherNickName);
        classid = intent.getStringExtra("classid");
        sp.edit().putString(SpUtiles.OtherHeadPic,otherheadpic).commit();
        sp.edit().putString(SpUtiles.OtherNickName,othernickname).commit();
        if (isGroup == 1) {
            rlAllmember.setVisibility(View.GONE);
        }
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        register();
        getSupportFragmentManager().beginTransaction().add(R.id.rl_chat, chatFragment).commit();
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(title);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.iv_persionmanage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_persionmanage:
                ctx.startActivity(new Intent(ctx,GroupMemberManageActivity.class)
                        .putExtra("group_id",toChatUsername)
                        .putExtra("groupname",title)
                        .putExtra("classid",classid));
                break;
        }
    }

}
