package com.dlwx.wisdomschool.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ChatActivity;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
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
        initrefresh(refreshLayout,true);
    }

    @Override
    public void downOnRefresh() {
        getList();
    }

    @Override
    public void onResume() {
        getList();
        super.onResume();
    }

    private void getList() {
        emConversations = new ArrayList<>();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//初始化，参数为会话列表集合
        Iterator<Map.Entry<String, EMConversation>> iterator = conversations.entrySet().iterator();
        while (iterator.hasNext()) {
            EMConversation value = iterator.next().getValue();
            EMConversation.EMConversationType type = value.getType();
            if (type == EMConversation.EMConversationType.GroupChat) {
              continue;
            }else if (type == EMConversation.EMConversationType.ChatRoom) {
                continue;
            }
            emConversations.add(value);
        }

        if (emConversations.size() > 0) {
            llEntry.setVisibility(View.GONE);
            lvList.setVisibility(View.VISIBLE);
        }else{
            llEntry.setVisibility(View.VISIBLE);
            lvList.setVisibility(View.GONE);
        }
        wch(emConversations.size());
        lvList.init(emConversations);
        //刷新列表
        lvList.refresh();
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
        List<EMMessage> allMessages = emConversation.getAllMessages();
        String to_headportrait = null;
        String to_username = null;
        if(emConversation.getLastMessage()!=null){
            SharedPreferences sp = getContext().getSharedPreferences("sp_mode", Context.MODE_PRIVATE);
            String sendUrl=sp.getString("Header_pic","");
            String userId = sp.getString("UserId", "");
            Log.i("CCC","from:"+emConversation.getLastMessage().getFrom()+"--userid--"+userId);
            if (emConversation.getLastMessage().getFrom().equals(userId)) {
               to_headportrait = emConversation.getLastMessage().getStringAttribute("to_headportrait", "");
               to_username = emConversation.getLastMessage().getStringAttribute("to_username", "");
            }
            else {
                to_headportrait = emConversation.getLastMessage().getStringAttribute("from_headportrait", "");
               to_username = emConversation.getLastMessage().getStringAttribute("from_username", "");
            }
        }
        Intent intent = new Intent(ctx, ChatActivity.class);
        intent.putExtra("title",to_username);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userid);
        intent.putExtra(SpUtiles.OtherHeadPic,to_headportrait);
        intent.putExtra(SpUtiles.OtherNickName,to_username);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        startActivity(intent);
    }
}
