package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 班级内权限
 */
public class ClassJurisdictionActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rb_general)
    RadioButton rbGeneral;
    @BindView(R.id.rb_cansend)
    RadioButton rbCansend;
    @BindView(R.id.rb_teach)
    RadioButton rbTeach;
    @BindView(R.id.rb_transferclass)
    RadioButton rbTransferclass;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_jurisdiction);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级内权限");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {
        rgGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
         switch (radioGroup.getId()){
                    case R.id.rb_general://普通

                        break;
                    case R.id.rb_cansend://可发送消息
                        break;
                    case R.id.rb_teach://任课老师
                        showDiaDissolveTishi();
                        break;
                    case R.id.rb_transferclass://转让班级
                        showDiaDissolveTishi();
                        break;

                }
    }
    /**
     * 提示
     */
    private void showDiaDissolveTishi() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_jurisdi, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        final AlertDialog show = builder.show();
        Button btn_aff = view.findViewById(R.id.btn_aff);
        TextView tv_close = view.findViewById(R.id.tv_close);
        btn_aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
                startActivity(new Intent(ctx, IDApproveActivity.class));

            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
    }
}
