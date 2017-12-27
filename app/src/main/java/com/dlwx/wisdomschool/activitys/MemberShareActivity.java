package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 成员分享
 */
public class MemberShareActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_classcode)
    TextView tvClasscode;
    @BindView(R.id.tv_peoplenum)
    TextView tvPeoplenum;
    @BindView(R.id.tv_teachname)
    TextView tvTeachname;
    @BindView(R.id.ll_sharemember)
    LinearLayout ll_sharemember;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_member_share);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("成员分享");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_share, R.id.tv_check, R.id.ll_sharemember})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                showDia();
                break;
            case R.id.tv_check:
                break;
            case R.id.ll_sharemember:
                startActivity(new Intent(ctx,WillShareMemberActivity.class));
                break;

        }
    }
    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_classshare
                ,null);
        AlertDialog.Builder builder =  new AlertDialog.Builder(ctx);
        builder.setView(view);
        final AlertDialog show = builder.show();
        Button btn_sett = view.findViewById(R.id.btn_sett);
        TextView tv_nosett = view.findViewById(R.id.tv_nosett);
        btn_sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();

            }
        });
        tv_nosett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
    }
}
