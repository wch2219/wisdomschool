package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.swich_mess)
    Switch swichMess;
    @BindView(R.id.swich_shake)
    Switch swichShake;
    @BindView(R.id.tv_cachsize)
    TextView tvCachsize;
    @BindView(R.id.ll_cleancach)
    LinearLayout llCleancach;
    @BindView(R.id.ll_okevaluate)
    LinearLayout llOkevaluate;
    private AlertDialog diaShow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sett);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("设置");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_cleancach, R.id.ll_okevaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_cleancach://清除缓存
                showDia();
                break;
            case R.id.ll_okevaluate://给个好评
                break;
        }
    }

    /**
     * 清除缓存
     */
    private void showDia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_cleancach, null);
        builder.setView(view);
        ViewHolderDia vh = new ViewHolderDia(view);
        vh.tv_close.setOnClickListener(this);
        vh.tv_aff.setOnClickListener(this);
        diaShow = builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                diaShow.dismiss();
                break;
            case R.id.tv_aff:
                diaShow.dismiss();
                break;
        }
    }

    private class ViewHolderDia {
        public View rootView;
        public TextView tv_close;
        public TextView tv_aff;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_aff = (TextView) rootView.findViewById(R.id.tv_aff);
        }

    }
}
