package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivateChatFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.list)
    EaseConversationList lvList;
    Unbinder unbinder;
    private List<EMConversation> emConversations;


    @Override
    public int getLayoutID() {
        return R.layout.fragment_private_chat;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {

    }

    @Override
    public void onResume() {
        emConversations = new ArrayList<>();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//初始化，参数为会话列表集合
        Iterator<Map.Entry<String, EMConversation>> iterator = conversations.entrySet().iterator();
        while (iterator.hasNext()) {
            emConversations.add(iterator.next().getValue());
        }

        //删除包含 群聊和聊天室 的会话
        for (int i = 0; i < emConversations.size(); i++) {
            EMConversation emConversation = emConversations.get(i);
            EMConversation.EMConversationType type = emConversation.getType();
            if (type == EMConversation.EMConversationType.GroupChat) {
                emConversations.remove(i);
            }else if (type == EMConversation.EMConversationType.ChatRoom) {
                emConversations.remove(i);
            }
        }

        if (conversations.size() > 0) {
            llEntry.setVisibility(View.GONE);
        }
        lvList.init(emConversations);
//刷新列表
        lvList.refresh();
        super.onResume();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        EMConversation emConversation = emConversations.get(i);
        String userid = emConversation.conversationId();
        String to_username = emConversation.getLastMessage().getStringAttribute("from_username", "");
        Intent intent = new Intent(ctx, ChatActivity.class);
        intent.putExtra("title",to_username);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userid);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        startActivity(intent);
    }
}
