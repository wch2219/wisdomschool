package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SharesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 教师邀请码
 */
public class TeacherInvitaCodeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.gv_list)
    GridView gvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_teacher_invita_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("生成教师邀请码");
        initTabBar(toolBar);
        String[] shares = getResources().getStringArray(R.array.shares);
        gvList.setAdapter(new SharesAdapter(ctx,shares));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
}
