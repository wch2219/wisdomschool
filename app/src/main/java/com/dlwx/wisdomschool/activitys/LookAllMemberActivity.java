package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassAllMemberAdapter;
import com.dlwx.wisdomschool.bean.ClassDescBean;
import com.dlwx.wisdomschool.utiles.SpUtiles;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 查看全部成员
 */
public class LookAllMemberActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.gv_list)
    GridView gvList;
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    private String classid;

    @Override
    protected void initView() {
        add_user = (List<ClassDescBean.BodyBean.AddUserBean>) getIntent().getSerializableExtra("adduser");
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_look_all_member);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("查看全部成员");
        initTabBar(toolBar);
        gvList.setAdapter(new ClassAllMemberAdapter(ctx,add_user));
    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String jcid = add_user.get(i).getJcid();
        String userid = add_user.get(i).getUserid();
        if (userid.equals(sp.getString(SpUtiles.Userid,""))) {
            startActivity(new Intent(ctx,PersionMessActivity.class));
        }else{
            Intent intent = new Intent(ctx, MemberMessActivity.class);
            intent.putExtra("jcid",jcid);
            intent.putExtra("classid",classid);
            startActivity(intent);
        }

    }
}
