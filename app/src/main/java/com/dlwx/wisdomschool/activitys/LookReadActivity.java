package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
public class LookReadActivity extends BaseActivity {
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
    private String id;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
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


    @OnClick({R.id.tv_all, R.id.tv_notlook, R.id.tv_remindlook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                tvAll.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvNotlook.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                rlType.setBackgroundResource(R.drawable.shape_loop_bgg_reen);
                getData("1");
                break;
            case R.id.tv_notlook:
                tvAll.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                tvNotlook.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                rlType.setBackgroundResource(R.drawable.shape_loop_bgg_blue);
                getData("2");
                break;
            case R.id.tv_remindlook://一键提醒
//                showremind();
                break;
        }
    }

    /**
     * 弹出提醒框体
     */
    private void showremind() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_renind, null);
        builder.setView(view);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        LookReadBean lookReadBean = gson.fromJson(s, LookReadBean.class);
        if (lookReadBean.getCode() == 200) {
            LookReadBean.BodyBean body = lookReadBean.getBody();
            tvLooknum.setText(body.getRead_num() + "人已查看");
            tvNotlooknum.setText(body.getUnread_num() + "人未查看");
            tvNum.setText(body.getPercentage());
            List<LookReadBean.BodyBean.AllInfoBean> all_info = body.getAll_info();
            lvList.setAdapter(new LookReadAdapter(ctx, all_info));
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
}
