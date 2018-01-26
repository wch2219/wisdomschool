package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.exceptions.HyphenateException;

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
    private String title;

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        //聊天人或群id
       String toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        //参数为要添加的好友的username和添加理由
        try {
            EMClient.getInstance().contactManager().addContact(toChatUsername, "添加");
        } catch (HyphenateException e) {
            e.printStackTrace();
            wch(e.getMessage());
        }
        title = getIntent().getStringExtra("title");
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());

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

                break;
        }
    }
}
