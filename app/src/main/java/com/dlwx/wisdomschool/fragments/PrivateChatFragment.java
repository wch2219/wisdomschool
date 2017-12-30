package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ChatActivity;
import com.dlwx.wisdomschool.adapter.FriendListAdapter;
import com.hyphenate.easeui.EaseConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivateChatFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.lv_list)
    ListView lvList;
    Unbinder unbinder;

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
        lvList.setAdapter(new FriendListAdapter(ctx));
//        try {
//            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//        } catch (HyphenateException e)  {
//            e.printStackTrace();
//        }
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
        startActivity(new Intent(ctx, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "娜娜"));;
    }
}
