package com.dlwx.wisdomschool.fragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 私聊
 */

public class ChatFragment extends BaseFragment {
    @BindView(R.id.tv_privatechat)
    TextView tvPrivatechat;
    @BindView(R.id.tv_groupchat)
    TextView tvGroupchat;
    @BindView(R.id.fl_chat)
    FrameLayout flClass;
    Unbinder unbinder;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    public int getLayoutID() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
        PrivateChatFragment privateChatFragment = new PrivateChatFragment();
        fragments.add(privateChatFragment);
        fragments.add(new GroupChatFragment());
        changeFragment(0);
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

    @OnClick({R.id.tv_privatechat, R.id.tv_groupchat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_privatechat://私聊
                tvPrivatechat.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvGroupchat.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                changeFragment(0);
                break;
            case R.id.tv_groupchat://群聊
                tvPrivatechat.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                tvGroupchat.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                changeFragment(1);
                break;
        }
    }


    private Fragment fragment;
    private FragmentTransaction transaction;
    private Fragment lastFragment;

    private void changeFragment(int i) {

        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // 上一个不为空 隐藏上一个
        if (lastFragment != null && lastFragment != fragments.get(i)) {
            transaction.hide(lastFragment);
//            transaction.remove(lastFragment);
        }

        fragment = fragments.get(i);
        // fragment不能重复添加 // 添加过 显示 没有添加过 就隐藏
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_chat, fragment);
        }
        transaction.commitAllowingStateLoss();
        lastFragment = fragment;
    }

}
