package com.dlwx.wisdomschool.activitys;

import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.LookReadAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_onekey)
    TextView tvOnekey;
    @BindView(R.id.rl_bottm)
    RelativeLayout rlBottom;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_look_read);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("反馈");
            initTabBar(toolBar);
            lvList.setAdapter(new LookReadAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.tv_all, R.id.tv_notlook, R.id.tv_onekey})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                tvAll.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvNotlook.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                rlType.setBackgroundResource(R.drawable.shape_loop_bgg_reen);
                rlBottom.setVisibility(View.GONE);
                break;
            case R.id.tv_notlook:
                tvAll.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                tvNotlook.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                rlType.setBackgroundResource(R.drawable.shape_loop_bgg_blue);
                rlBottom.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_onekey:
                showPopu();
                break;
        }
    }

    /**
     * 显示提醒窗体
     */
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_lookread_tishi,null);
        PopupWindow popu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popu.showAtLocation(tvAll, Gravity.BOTTOM,0,0);
        popu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }
}
