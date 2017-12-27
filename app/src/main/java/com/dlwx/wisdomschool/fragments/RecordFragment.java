package com.dlwx.wisdomschool.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.PublishGroupUpActivity;
import com.dlwx.wisdomschool.activitys.RecordDescActivity;
import com.dlwx.wisdomschool.activitys.SynthesizeActivity;
import com.dlwx.wisdomschool.adapter.RecordScreenAddAdapter;
import com.dlwx.wisdomschool.adapter.RecordScreenCreateAdapter;
import com.dlwx.wisdomschool.adapter.RecordScreenPrivateAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 成长纪录
 */
public class RecordFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_screen)
    ImageView ivScreen;
    @BindView(R.id.fab_edit)
    Button fabEdit;
    @BindView(R.id.ll_list)
    LinearLayout iv_list;
    @BindView(R.id.rl_synthesize)
    RelativeLayout rl_synthesize;
    Unbinder unbinder;
    private AlertDialog diashow;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_record;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_screen, R.id.fab_edit, R.id.ll_list,R.id.rl_synthesize})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_screen://筛选
                showDia();
                break;
            case R.id.rl_synthesize://综合素质大提升
                startActivity(new Intent(ctx,SynthesizeActivity.class));
                break;
            case R.id.fab_edit://编辑
                showPopu();
                break;
            case R.id.ll_list://纪录详情
                startActivity(new Intent(ctx, RecordDescActivity.class));
                break;
        }
    }

    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_record_edit, null);
        ViewHolderPopu vhpopu = new ViewHolderPopu(view);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                popupWindow.dismiss();
            }
        });
        vhpopu.ll_close.setOnClickListener(this);
        vhpopu.tv_synthesize.setOnClickListener(this);
        vhpopu.tv_video.setOnClickListener(this);
        vhpopu.tv_picandtext.setOnClickListener(this);
        popupWindow.showAtLocation(iv_list, Gravity.BOTTOM, 0, 0);
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_recordscreen, null);
        ViewHolderDia vhDia = new ViewHolderDia(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        diashow = builder.show();
        vhDia.iv_close.setOnClickListener(this);
        vhDia.lv_listcreate.setAdapter(new RecordScreenCreateAdapter(ctx));
        vhDia.lv_listadd.setAdapter(new RecordScreenAddAdapter(ctx));
        vhDia.lv_listprivacy.setAdapter(new RecordScreenPrivateAdapter(ctx));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_synthesize:
                popupWindow.dismiss();
                publishGroupUp();
                break;
            case R.id.tv_video:
                popupWindow.dismiss();
                publishGroupUp();
                break;
            case R.id.tv_picandtext:
                popupWindow.dismiss();
                publishGroupUp();
                break;
            case R.id.ll_close:
                popupWindow.dismiss();
                publishGroupUp();
                break;
            case R.id.iv_close:
                diashow.dismiss();
                publishGroupUp();
                break;
        }
    }

    /**
     * 发布成长
     */
    private void publishGroupUp() {
        startActivity(new Intent(ctx,PublishGroupUpActivity.class));
    }

    private class ViewHolderDia {
        public View rootView;
        public ImageView iv_close;
        public CheckBox cb_check;
        public MyListView lv_listcreate;
        public TextView tv_mycreatenum;
        public MyListView lv_listadd;
        public MyListView lv_listprivacy;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
            this.lv_listcreate = (MyListView) rootView.findViewById(R.id.lv_listcreate);
            this.tv_mycreatenum = (TextView) rootView.findViewById(R.id.tv_mycreatenum);
            this.lv_listadd = (MyListView) rootView.findViewById(R.id.lv_listadd);
            this.lv_listprivacy = (MyListView) rootView.findViewById(R.id.lv_listprivacy);

        }
    }

    public static class ViewHolderPopu {
        public View rootView;
        public TextView tv_synthesize;
        public TextView tv_video;
        public TextView tv_picandtext;
        public LinearLayout ll_close;
        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_synthesize = (TextView) rootView.findViewById(R.id.tv_synthesize);
            this.tv_video = (TextView) rootView.findViewById(R.id.tv_video);
            this.tv_picandtext = (TextView) rootView.findViewById(R.id.tv_picandtext);
            this.ll_close = (LinearLayout) rootView.findViewById(R.id.ll_close);
        }
    }
}
