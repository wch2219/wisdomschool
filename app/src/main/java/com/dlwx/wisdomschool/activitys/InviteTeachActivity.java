package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 邀请老师
 */
public class InviteTeachActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.btn_userphone)
    Button btnUserphone;
    private String classid;

    @Override
    protected void initView() {
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_invite_teach);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("添加任课老师");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }



    @OnClick({R.id.btn_userphone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_userphone:
                finish();
                startActivity(new Intent(ctx,InviteTeach2Activity.class).putExtra("classid",classid));
                break;
//            case R.id.iv_appoint:
//                showDia();
//                break;
        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_appoint
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
