package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 智慧书包
 */
public class WishDomBagActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private MyPopuWindow popupWindow;
    private AlertDialog diaShow;
    private ViewHolderDia vhDia;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wish_dom_bag);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧书包");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.iv_add)
    public void onViewClicked() {

        showPopu();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_wish, null);
        ViewHolderpopu vh = new ViewHolderpopu(view);
        popupWindow = new MyPopuWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(ivAdd, -20, 20, Gravity.RIGHT);
        vh.tv_uppic.setOnClickListener(this);
        vh.tv_upfile.setOnClickListener(this);
        vh.tv_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_uppic:
                popupWindow.dismiss();
                break;
            case R.id.tv_upfile:
                popupWindow.dismiss();
                break;
            case R.id.tv_new:
                popupWindow.dismiss();
                showDia();
                break;
            case R.id.tv_close:
                 diaShow.dismiss();
                break;
            case R.id.tv_create:
                diaShow.dismiss();
                break;

        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_wishbag, null);
        vhDia = new ViewHolderDia(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        builder.setCancelable(false);
        diaShow = builder.show();
        vhDia.tv_close.setOnClickListener(this);
        vhDia.tv_create.setOnClickListener(this);
    }

    private class ViewHolderpopu {
        public View rootView;
        public TextView tv_uppic;
        public TextView tv_upfile;
        public TextView tv_new;

        public ViewHolderpopu(View rootView) {
            this.rootView = rootView;
            this.tv_uppic = (TextView) rootView.findViewById(R.id.tv_uppic);
            this.tv_upfile = (TextView) rootView.findViewById(R.id.tv_upfile);
            this.tv_new = (TextView) rootView.findViewById(R.id.tv_new);
        }
    }

   private class ViewHolderDia {
        public View rootView;
        public EditText et_filename;
        public TextView tv_close;
        public TextView tv_create;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.et_filename = (EditText) rootView.findViewById(R.id.et_filename);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_create = (TextView) rootView.findViewById(R.id.tv_create);
        }

    }
}
