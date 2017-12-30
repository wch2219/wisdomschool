package com.dlwx.wisdomschool.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ChatGroupListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 群聊
 */
public class GroupChatFragment extends BaseFragment {
    @BindView(R.id.btn_creategroup)
    Button btnCreategroup;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.ll_addgroup)
    LinearLayout llAddgroup;
    @BindView(R.id.lv_list)
    ExpandableListView lvList;
    @BindView(R.id.ll_noentry)
    LinearLayout llNoentry;
    Unbinder unbinder;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_group_chat;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
//        lvList.setGroupIndicator(this.getResources().getDrawable(R.drawable.expandablelistviewselector));
        lvList.setAdapter(new ChatGroupListAdapter(ctx));
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

    @OnClick({R.id.btn_creategroup, R.id.ll_addgroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_creategroup:
                break;
            case R.id.ll_addgroup:
                break;
        }
    }
}
