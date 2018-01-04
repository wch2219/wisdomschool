package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.WillSHareMemberAdapter;
import com.dlwx.wisdomschool.bean.ClassDescBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 要分享的成员
 */
public class WillShareMemberActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_aff)
    TextView tvAff;
    @BindView(R.id.gv_list)
    GridView gvList;
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    private WillSHareMemberAdapter willSHareMemberAdapter;

    @Override
    protected void initView() {
        add_user = (List<ClassDescBean.BodyBean.AddUserBean>) getIntent().getSerializableExtra("adduser");
        setContentView(R.layout.activity_will_share_member);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("要分享的成员");
        willSHareMemberAdapter = new WillSHareMemberAdapter(ctx, add_user);
        gvList.setAdapter(willSHareMemberAdapter);
    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    private String userIds;
    @OnClick(R.id.tv_aff)
    public void onViewClicked() {
        userIds = "";

            Iterator<Map.Entry<Integer, Boolean>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<Integer, Boolean> next = iterator.next();

                boolean value = next.getValue();
                if (value) {
                    ClassDescBean.BodyBean.AddUserBean addUserBean = add_user.get(next.getKey());
                    if (TextUtils.isEmpty(userIds)) {

                    userIds  = addUserBean.getJcid();
                    }else{
                        userIds = userIds+","+addUserBean.getJcid();
                    }
                }


        }
        if (TextUtils.isEmpty(userIds)) {
            Toast.makeText(ctx, "请先添加要分享的成员", Toast.LENGTH_SHORT).show();
                return;
        }
        Intent intent = new Intent();
            intent.putExtra("jcid",userIds);
            setResult(1,intent);
        finish();
    }
    public Map<Integer,Boolean> map = new HashMap<>();
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ClassDescBean.BodyBean.AddUserBean addUserBean = add_user.get(i);
        addUserBean.setIscheck(!addUserBean.isIscheck());
        map.put(i,addUserBean.isIscheck());
        willSHareMemberAdapter.notifyDataSetChanged();
    }
}
