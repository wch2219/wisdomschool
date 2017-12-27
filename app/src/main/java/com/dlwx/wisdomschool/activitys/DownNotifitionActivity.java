package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 下载家长通知单
 */
public class DownNotifitionActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.btn_phone)
    Button btnPhone;
    @BindView(R.id.btn_mailbox)
    Button btnMailbox;
    private AlertDialog diashow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_down_notifition);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("下载家长使用通知单");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.btn_phone, R.id.btn_mailbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_phone:
                break;
            case R.id.btn_mailbox:
                showDia();
                break;
        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_mail, null);
        ViewHolder vh = new ViewHolder(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        diashow = builder.show();
    }

    private class ViewHolder implements View.OnClickListener{
        public View rootView;
        public EditText et_mail;
        public Button btn_send;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_mail = (EditText) rootView.findViewById(R.id.et_mail);
            this.btn_send = (Button) rootView.findViewById(R.id.btn_send);
            this.btn_send.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
             switch (view.getId()){
                        case R.id.btn_send:
                            diashow.dismiss();
                            break;
                    }
        }
    }
}
