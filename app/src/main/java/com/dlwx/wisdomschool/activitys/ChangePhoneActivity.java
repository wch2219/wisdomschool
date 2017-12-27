package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改手机号
 */
public class ChangePhoneActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_clean)
    ImageView ivClean;
    @BindView(R.id.btn_next)
    Button btnNext;
    private AlertDialog show;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的手机号");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.iv_clean, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clean:
                break;
            case R.id.btn_next:
                showDia();
                break;
        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_changephone, null);
        ViewHolder vh = new ViewHolder(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        show = builder.show();
        vh.btn_ok.setOnClickListener(this);
        vh.tv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                show.dismiss();
                break;
            case R.id.btn_ok:
                show.dismiss();
                finish();
                startActivity(new Intent(ctx,ChangePhone2Activity.class));
                break;
        }
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_phone;
        public TextView tv_close;
        public Button btn_ok;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.btn_ok = (Button) rootView.findViewById(R.id.btn_ok);

        }

    }
}
