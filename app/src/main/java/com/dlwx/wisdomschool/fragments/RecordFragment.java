package com.dlwx.wisdomschool.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AllPicActivity;
import com.dlwx.wisdomschool.activitys.PublishGroupUpActivity;
import com.dlwx.wisdomschool.activitys.RecordVideoActivity;
import com.dlwx.wisdomschool.activitys.SynthesizeActivity;
import com.dlwx.wisdomschool.adapter.RecordListAdapter;
import com.dlwx.wisdomschool.bean.RecordListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 成长纪录
 */
public class RecordFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_screen)
    ImageView ivScreen;
    @BindView(R.id.fab_edit)
    Button fabEdit;
    @BindView(R.id.rl_synthesize)
    RelativeLayout rl_synthesize;
    @BindView(R.id.ll_seleteclass)
    LinearLayout ll_seleteclass;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_list)
    ListView lv_list;
    Unbinder unbinder;
    private AlertDialog diashow;
    private PopupWindow popupWindow;
    private String type = "";
    private String class_no = "";

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
        getDataList();
    }


    @Override
    protected void initListener() {
        initrefresh(refreshLayout, false);
    }

    @Override
    public void downOnRefresh() {
        getDataList();
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

    @OnClick({R.id.iv_screen, R.id.fab_edit, R.id.rl_synthesize, R.id.ll_seleteclass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_screen://筛选
                showBottomPopu();
                break;
            case R.id.rl_synthesize://综合素质大提升
                startActivity(new Intent(ctx, SynthesizeActivity.class));
                break;
            case R.id.fab_edit://编辑
                showPopu();
                break;
//            case R.id.ll_list://纪录详情
//                startActivity(new Intent(ctx, RecordDescActivity.class));
//                break;
            case R.id.ll_seleteclass://选择班级纪录
                showDia();
                break;

        }
    }

    /**
     *
     */
    private void showBottomPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_record_screen, null);
        ViewHolderSceer viewHolderSceer = new ViewHolderSceer(view);
        if (TextUtils.isEmpty(type)) {
            viewHolderSceer.rb_all.setChecked(true);
        } else {
            viewHolderSceer.rb_myself.setChecked(true);
        }
        final PopupWindow popuSceer = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewHolderSceer.rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_all:
                        popuSceer.dismiss();
                        type = "";
                        getDataList();
                        break;
                    case R.id.rb_myself:
                        popuSceer.dismiss();
                        type = "1";
                        getDataList();
                        break;
                }
            }
        });
        viewHolderSceer.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popuSceer.dismiss();
            }
        });
        popuSceer.setFocusable(true);
        popuSceer.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popuSceer.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        popuSceer.showAtLocation(ivScreen, Gravity.BOTTOM, 0, 0);


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
        popupWindow.showAtLocation(ivScreen, Gravity.BOTTOM, 0, 0);
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_recordscreen, null);
        ViewHolderDia vhDia = new ViewHolderDia(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        diashow = builder.show();
        vhDia.iv_close.setOnClickListener(this);
//        vhDia.lv_listcreate.setAdapter(new RecordScreenCreateAdapter(ctx));
//        vhDia.lv_listadd.setAdapter(new RecordScreenAddAdapter(ctx));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_synthesize:
                popupWindow.dismiss();
                publishGroupUp(1);
                break;
            case R.id.tv_video:
                popupWindow.dismiss();
                publishGroupUp(2);
                break;
            case R.id.tv_picandtext:
                popupWindow.dismiss();
                publishGroupUp(3);
                break;
            case R.id.ll_close:
                popupWindow.dismiss();
                break;
            case R.id.iv_close:
                diashow.dismiss();
                break;
        }
    }

    /**
     * 发布成长
     *
     * @param type 1综合素质 2视频纪录 3图文纪录
     */
    private void publishGroupUp(int type) {
        Intent intent;
        if (type == 1) {
            intent = new Intent(ctx, SynthesizeActivity.class);
            startActivity(intent);
        } else if (type == 3) {
//            intent = new Intent(ctx, PublishGroupUpActivity.class);

            intent = new Intent(ctx, AllPicActivity.class);
            intent.putExtra("type", type);
            startActivityForResult(intent, 2);
        } else {
            intent = new Intent(ctx, RecordVideoActivity.class);
            startActivityForResult(intent, 1);
        }


    }

    private class ViewHolderDia {
        public View rootView;
        public ImageView iv_close;
        public CheckBox cb_check;
        public MyListView lv_listcreate;
        public TextView tv_mycreatenum;
        public MyListView lv_listadd;
        public LinearLayout ll_listprivacy;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
            this.lv_listcreate = (MyListView) rootView.findViewById(R.id.lv_listcreate);
            this.tv_mycreatenum = (TextView) rootView.findViewById(R.id.tv_mycreatenum);
            this.lv_listadd = (MyListView) rootView.findViewById(R.id.lv_listadd);
            this.ll_listprivacy = (LinearLayout) rootView.findViewById(R.id.ll_listprivacy);

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


    /**
     * 拉取数据
     */
    private void getDataList() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("type", type);
        map.put("class_no", class_no);
        mPreenter.fetch(map, true, HttpUrl.Growthrecord, HttpUrl.Growthrecord + Token + type + class_no);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        RecordListBean recordListBean = gson.fromJson(s, RecordListBean.class);
        if (recordListBean.getCode() == 200) {
            List<RecordListBean.BodyBean> body = recordListBean.getBody();
            lv_list.setAdapter(new RecordListAdapter(ctx, body));
        } else {
            Toast.makeText(ctx, recordListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 筛选
     */
    private class ViewHolderSceer {
        public View rootView;
        public RadioButton rb_all;
        public RadioButton rb_myself;
        public RadioGroup rg_group;
        public TextView tv_close;

        public ViewHolderSceer(View rootView) {
            this.rootView = rootView;
            this.rb_all = (RadioButton) rootView.findViewById(R.id.rb_all);
            this.rb_myself = (RadioButton) rootView.findViewById(R.id.rb_myself);
            this.rg_group = (RadioGroup) rootView.findViewById(R.id.rg_group);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {

            return;
        }
        Intent intent;
        switch (requestCode) {
            case 1://视频
                String vodeofile = data.getStringExtra("vodeofile");
                wch("视频："+vodeofile);
                intent = new Intent(ctx,PublishGroupUpActivity.class);
                intent.putExtra("videofile",vodeofile);
                startActivity(intent);
                break;
            case 2://图片
                break;
        }
    }
}
