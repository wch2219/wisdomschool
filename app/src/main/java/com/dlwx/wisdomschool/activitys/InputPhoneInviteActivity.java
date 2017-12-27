package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.InputPhoneInviteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 输入手机号邀请好友
 */
public class InputPhoneInviteActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.btn_sendsms)
    Button btnSendsms;
    private int size = 1;
    private InputPhoneInviteAdapter inputPhoneInviteAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_input_phone_invite);
        ButterKnife.bind(this);
        View foodView = LayoutInflater.from(ctx).inflate(R.layout.inputphonefood,null);
        lvList.addFooterView(foodView);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("输入手机号邀请");
        inputPhoneInviteAdapter = new InputPhoneInviteAdapter(ctx, size);
        lvList.setAdapter(inputPhoneInviteAdapter);

    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_sendsms)
    public void onViewClicked() {


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == size) {
            size++;
            inputPhoneInviteAdapter.setSize(size);
        }
    }
}
