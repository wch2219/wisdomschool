package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.LookReadAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.LookReadBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 查看反馈
 */
public class LookReadActivity extends BaseActivity implements LookReadAdapter.SendNotifitiListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rl_type)
    RelativeLayout rlType;
    @BindView(R.id.tv_looknum)
    TextView tvLooknum;
    @BindView(R.id.tv_notlooknum)
    TextView tvNotlooknum;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_notlook)
    TextView tvNotlook;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_remindlook)
    TextView tv_remindlook;
    @BindView(R.id.btn_oneKey)
    Button btn_oneKey;
    private String id;
    //消息的主题
    private int theme;
    private AlertDialog onKeyDia;
    private List<LookReadBean.BodyBean.Unread_info> unread_info;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        theme = intent.getIntExtra("type",0);
        setContentView(R.layout.activity_look_read);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("反馈");
        initTabBar(toolBar);

        getData("1");

    }

    private void getData(String choose) {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("id", id);
        map.put("type", "1");
        map.put("choose", choose);
        mPreenter.fetch(map, true, HttpUrl.Situation, HttpUrl.Situation + Token + id + "1" + choose);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.tv_all, R.id.tv_notlook, R.id.btn_oneKey})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                tvAll.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvNotlook.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                rlType.setBackgroundResource(R.drawable.shape_loop_bgg_reen);
                getData("1");
                btn_oneKey.setVisibility(View.GONE);
                type = 1;
                break;
            case R.id.tv_notlook:
                tvAll.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                tvNotlook.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                rlType.setBackgroundResource(R.drawable.shape_loop_bgg_blue);
                getData("2");
                type = 2;

                break;
            case R.id.btn_oneKey://一键提醒
                showremind();
                break;
        }
    }

    private int type = 1;


    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            listDate(s, gson);
        } else {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                getData(type + "");
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void listDate(String s, Gson gson) {
        LookReadBean lookReadBean = gson.fromJson(s, LookReadBean.class);
        if (lookReadBean.getCode() == 200) {
            LookReadBean.BodyBean body = lookReadBean.getBody();
            tvLooknum.setText(body.getRead_num() + "人已查看");
            tvNotlooknum.setText(body.getUnread_num() + "人未查看");
            tvNum.setText(body.getPercentage());
            LookReadAdapter lookReadAdapter;
            if (type == 1) {
                List<LookReadBean.BodyBean.AllInfoBean> all_info = body.getAll_info();
                lookReadAdapter = new LookReadAdapter(ctx, all_info);
                lvList.setAdapter(lookReadAdapter);
            } else {
                unread_info = body.getUnread_info();
                lookReadAdapter = new LookReadAdapter(ctx, unread_info, type);
                lvList.setAdapter(lookReadAdapter);
                if (unread_info.size() > 0) {
                    btn_oneKey.setVisibility(View.VISIBLE);
                }
            }
            lookReadAdapter.setSendNotifitiListener(this);
        } else {
            Toast.makeText(ctx, lookReadBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示提醒窗体
     */
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_lookread_tishi, null);
        PopupWindow popu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popu.showAtLocation(tvAll, Gravity.BOTTOM, 0, 0);
        popu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }

    //单个提醒用户
    @Override
    public void send(String parent_id) {
        senNoti(parent_id);
    }

    /**
     * 给家长发送通知
     *
     * @param parent_id
     */
    private void senNoti(String parent_id) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("parent_id", parent_id);
        map.put("type", theme+"");
        HttpType = 2;
        mPreenter.fetch(map, true, HttpUrl.Push_message, "");
    }

    /**
     * 弹出提醒框体
     */
    private void showremind() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_renind, null);
        ViewHolder vh = new ViewHolder(view);
        builder.setView(view);
        onKeyDia = builder.show();
        vh.tv_close.setOnClickListener(this);
        vh.tv_aff.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onKeyDia.dismiss();
        switch (v.getId()) {
            case R.id.tv_close:
                break;
            case R.id.tv_aff:
                String parentid = "";
                for (int i = 0; i < unread_info.size(); i++) {
                    LookReadBean.BodyBean.Unread_info unread_info = this.unread_info.get(i);

                        if (TextUtils.isEmpty(parentid)) {
                            parentid = unread_info.getUserid();
                        }else{
                            parentid = parentid + ","+ unread_info.getUserid();
                        }
                }
                senNoti(parentid);
                break;
        }

    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_close;
        public TextView tv_aff;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_aff = (TextView) rootView.findViewById(R.id.tv_aff);
        }

    }
}
