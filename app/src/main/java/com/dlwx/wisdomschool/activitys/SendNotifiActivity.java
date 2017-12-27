package com.dlwx.wisdomschool.activitys;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SendNotiAddActionAdapter;
import com.dlwx.wisdomschool.adapter.SendNotiSeleteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发送通知
 */
public class SendNotifiActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_send)
    LinearLayout llSend;
    @BindView(R.id.tv_classname)
    TextView tvClassname;
    @BindView(R.id.ll_seleteclass)
    LinearLayout llSeleteclass;
    @BindView(R.id.tv_actionname)
    TextView tvActionname;
    @BindView(R.id.ll_seleteaction)
    LinearLayout llSeleteaction;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.tv_addaction)
    TextView tvAddaction;
    @BindView(R.id.iv_mp3)
    ImageView ivMp3;
    @BindView(R.id.iv_addpic)
    ImageView ivAddpic;
    @BindView(R.id.iv_face)
    ImageView ivFace;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_send_notifi);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("发送调查");
        initTabBar(toolBar);
        lvList.setAdapter(new SendNotiAddActionAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.ll_send, R.id.ll_seleteclass, R.id.ll_seleteaction, R.id.tv_addaction, R.id.iv_mp3, R.id.iv_addpic, R.id.iv_face})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_send://发送
                break;
            case R.id.ll_seleteclass://选择班级
                showseltePopu(llSeleteclass,new String[]{"一年级八班","一年级八班","一年级八班","一年级八班","一年级八班"});
                break;
            case R.id.ll_seleteaction://选择活动
                showseltePopu(llSeleteaction,new String[]{"班级通知","群组讨论","调查问卷","视频（实时）纪录","活动收集","布置作业"});
                break;
            case R.id.tv_addaction://添加活动
                break;
            case R.id.iv_mp3://添加语音
                break;
            case R.id.iv_addpic://添加图片
                break;
            case R.id.iv_face://添加表情
                break;
        }
    }

    private void showseltePopu(View parentview, String[] strs) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_selete, null);
        ViewHolder vh = new ViewHolder(view);
        PopupWindow popu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.blacktran));
        popu.setBackgroundDrawable(drawable);
        popu.showAsDropDown(parentview, 0, 0);
        vh.lv_list.setAdapter(new SendNotiSeleteAdapter(ctx,strs));
    }

    private class ViewHolder {
        public View rootView;
        public ListView lv_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lv_list = (ListView) rootView.findViewById(R.id.lv_list);
        }

    }
}
