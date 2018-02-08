package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyRecyclerView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AddActionAdapter;
import com.dlwx.wisdomschool.bean.ActionDescBean;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.fragments.EmojiconFragment;
import com.dlwx.wisdomschool.interfac.EmoInterface;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 参与活动
 */
public class AddActionActivity extends BaseActivity implements AddActionAdapter.ShowInputWindowListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_member)
    RelativeLayout rlMember;
    @BindView(R.id.rv_list)
    MyRecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_joinaction)
    TextView tvJoinaction;
    @BindView(R.id.ll_bottominput)
    LinearLayout llBottominput;
    @BindView(R.id.emojicon_menu_container)
    FrameLayout emojiconMenuContainer;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_sendsms)
    TextView tvSendsms;
    private String id;//活动id
    private PopupWindow bottompopu;
    private ArrayList<String> images;
    private AddActionAdapter addActionAdapter;
    private List<ActionDescBean.BodyBean.PinglunBean> pinglun;
    private ActionDescBean.BodyBean body;
    private int issend;
    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        issend = intent.getIntExtra("issend",1);
        setContentView(R.layout.activity_add_action);
        ButterKnife.bind(this);
        //初始化表情
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EmojiconFragment emojiconFragment = new EmojiconFragment();
        fragmentTransaction.add(R.id.emojicon_menu_container, emojiconFragment);
        fragmentTransaction.show(emojiconFragment);
        fragmentTransaction.commit();
        EmoInterface.setCheckEmoInterface(new EmoInterface.CheckEmoInterface() {
            @Override
            public void backEmo(Bitmap bitmap, int i) {
                ImageSpan imageSpan = new ImageSpan(ctx, bitmap);
                int spanend = i >= 9 ? 5 : 4;
                int suffix = i >= 9 ? i + 1 : (i + 1);
                SpannableString spannableString = new SpannableString("ee_" + suffix);   //“image”是图片名称的前缀
                spannableString.setSpan(imageSpan, 0, spanend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                etContent.append(spannableString);
            }
        });
        if (issend ==  1) {//发布方隐藏底部
            tvJoinaction.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧活动");
        initTabBar(toolBar);
        LinearLayoutManager manager = new LinearLayoutManager(ctx, LinearLayout.VERTICAL, false);
        rvList.setLayoutManager(manager);
        initrefresh(refreshLayout, true);
    }

    @Override
    protected void onResume() {
        getData();
        super.onResume();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.rl_member, R.id.tv_joinaction, R.id.iv_face, R.id.tv_sendsms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_member:
                break;
            case R.id.tv_joinaction:
                showPopuBottom();
                break;
            case R.id.iv_face:
                int visibility = emojiconMenuContainer.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int i = visibility == View.VISIBLE ? View.GONE : View.VISIBLE;
                emojiconMenuContainer.setVisibility(i);
                inputhind(etContent);
                break;
            case R.id.tv_sendsms:
                String content = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(ctx, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                emojiconMenuContainer.setVisibility(View.GONE);
                inputhind(etContent);
                submit(content);
                break;
        }
    }
    private void showPopuBottom() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_actiondesc, null);
        ViewHolderJoin viewHolderJoin = new ViewHolderJoin(view);
        bottompopu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bottompopu.setOutsideTouchable(true);
        bottompopu.setFocusable(true);
        bottompopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bottompopu.dismiss();
                backgroundAlpha(1);
            }
        });
        backgroundAlpha(0.5f);
        bottompopu.showAtLocation(rlMember, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            ActionDescBean actionDescBean = gson.fromJson(s, ActionDescBean.class);
            if (actionDescBean.getCode() == 200) {
                body = actionDescBean.getBody();
                if (body != null) {
                    addActionAdapter = new AddActionAdapter(ctx, body);
                    rvList.setAdapter(addActionAdapter);
                    addActionAdapter.setWindowListener(this);
                    rvList.scrollToPosition(pos);
                    pos = 0;
                }
            } else {
                Toast.makeText(ctx, actionDescBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        } else if (HttpType == 2) {//点赞和取消点赞
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                addActionAdapter.notifyDataSetChanged();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        } else if (HttpType == 3) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                getData();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 活动详情
     */
    private void getData() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("id", id);
        mPreenter.fetch(map, true, HttpUrl.ActionDesc, HttpUrl.ActionDesc + Token + id);
    }

    private class ViewHolderJoin implements View.OnClickListener {
        public View rootView;
        public TextView tv_pic;
        public TextView tv_voice;
        public TextView tv_video;
        public TextView tv_close;


        public ViewHolderJoin(View rootView) {
            this.rootView = rootView;
            this.tv_pic = (TextView) rootView.findViewById(R.id.tv_pic);
            this.tv_voice = (TextView) rootView.findViewById(R.id.tv_voice);
            this.tv_video = (TextView) rootView.findViewById(R.id.tv_video);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_pic.setOnClickListener(this);
            this.tv_voice.setOnClickListener(this);
            this.tv_video.setOnClickListener(this);
            this.tv_close.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.tv_pic:
                    bottompopu.dismiss();
                    intent = new Intent(ctx, AllPicActivity.class);
                    startActivityForResult(intent, 2);
                    break;
                case R.id.tv_voice:
                    intent = new Intent(ctx, JoinActionSendMessActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    bottompopu.dismiss();
                    break;
                case R.id.tv_video:
                    intent = new Intent(ctx, RecordVideoActivity.class);
                    startActivityForResult(intent, 1);
                    bottompopu.dismiss();
                    break;
                case R.id.tv_close:
                    bottompopu.dismiss();
                    break;

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Intent intent;
        switch (requestCode) {
            case 2://图片
                ArrayList<String> images = data.getStringArrayListExtra("images");
                intent = new Intent(ctx, JoinActionSendMessActivity.class);
                intent.putStringArrayListExtra("images", images);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            case 1://视频
                String vodeofile = data.getStringExtra("videofile");
                wch("视频：" + vodeofile);
                intent = new Intent(ctx, JoinActionSendMessActivity.class);
                intent.putExtra("videofile", vodeofile);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void show() {
        llBottominput.setVisibility(View.VISIBLE);
        tvJoinaction.setVisibility(View.GONE);
        etContent.setFocusable(true);
        etContent.requestFocus();
        etContent.setFocusableInTouchMode(true);
        inputShow(etContent);
    }

    @Override
    public void send(String icid, String irid, int pos) {
        this.icid = icid;
        this.irid = irid;
        this.pos = pos;
    }

    private String icid, irid;
    private int pos;//评价的条目

    @Override
    public void pingstar(String icid, String irid, int pos) {
        this.pos = pos;
        this.icid = icid;
        this.irid = irid;
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_xing, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setView(view);
        final RatingBar rb_xing = view.findViewById(R.id.rb_xing);
        final AlertDialog show = dialog.show();
        rb_xing.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ceil = (int) Math.ceil(rating);
                wch(ceil);
                rb_xing.setRating(ceil);
                show.dismiss();
                pingjiaContent = "";
                switch (ceil) {
                    case 1:
                        pingjiaContent = "ee_27";
                        break;
                    case 2:
                        pingjiaContent = "ee_27ee_27";
                        break;
                    case 3:
                        pingjiaContent = "ee_27ee_27ee_27";
                        break;
                    case 4:
                        pingjiaContent = "ee_27ee_27ee_27ee_27";
                        break;
                    case 5:
                        pingjiaContent = "ee_27ee_27ee_27ee_27ee_27";
                        break;

                }
                submit(pingjiaContent);
            }
        });
    }

    /**
     * 点赞
     *
     * @param icid
     * @param irid
     */
    @Override
    public void praise(String icid, String irid) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("irid", irid);
        map.put("icid", icid);
        HttpType = 2;
        mPreenter.fetch(map, true, HttpUrl.ActionDianzan, "");
    }

    private String pingjiaContent;

    /**
     * 提交评论回复星级评价
     *
     * @param content
     */
    private void submit(String content) {
        this.pingjiaContent = content;
        etContent.setText("");
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("irid", irid);
        map.put("icid", icid);
        map.put("pingjia", content);
        HttpType = 3;
        mPreenter.fetch(map, false, HttpUrl.ActionPinglun, "");
    }

    @Override
    public void share(int postion, String recorid, String shareurl) {
        showSharePopu();
    }

    private PopupWindow popushare;
    private ViewHolderShare holderShare;

    private void showSharePopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_record_share, null);
        popushare = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        holderShare = new ViewHolderShare(view);
        popushare.setFocusable(true);
        popushare.setOutsideTouchable(true);
        popushare.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popushare.dismiss();
                backgroundAlpha(1);
            }
        });

        backgroundAlpha(0.5f);
        popushare.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
    }

    private class ViewHolderShare implements View.OnClickListener {
        public View rootView;
        public ImageView iv_close;
        public LinearLayout ll_wxfrent;
        public LinearLayout ll_wx;
        public LinearLayout ll_qq;
        public LinearLayout ll_zone;
        public TextView tv_closemess;
        public TextView tv_close;

        public ViewHolderShare(View rootView) {
            this.rootView = rootView;
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
            this.ll_wxfrent = (LinearLayout) rootView.findViewById(R.id.ll_wxfrent);
            this.ll_wx = (LinearLayout) rootView.findViewById(R.id.ll_wx);
            this.ll_qq = (LinearLayout) rootView.findViewById(R.id.ll_qq);
            this.ll_zone = (LinearLayout) rootView.findViewById(R.id.ll_zone);
            this.tv_closemess = (TextView) rootView.findViewById(R.id.tv_closemess);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.iv_close.setOnClickListener(this);
            this.ll_wxfrent.setOnClickListener(this);
            this.ll_wx.setOnClickListener(this);
            this.ll_qq.setOnClickListener(this);
            this.ll_zone.setOnClickListener(this);
            this.tv_closemess.setOnClickListener(this);
            this.tv_close.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_close:
                    popushare.dismiss();
                    break;
                case R.id.ll_wxfrent://分享朋友圈
                    break;
                case R.id.ll_wx://分享微信
                    popushare.dismiss();
                    break;
                case R.id.ll_qq://分享qq好友
                    popushare.dismiss();
                    break;
                case R.id.ll_zone://分享空间
                    popushare.dismiss();
                    break;
                case R.id.tv_closemess://撤回消息
//                    Map<String, String> map = new HashMap<>();
//                    map.put("token", Token);
//                    map.put("id", recorid);
//                    map.put("type", "1");

//                    mPreenter.fetch(map, true, HttpUrl.DeleteRecord, "");
//                    popushare.dismiss();
                    break;
                case R.id.tv_close://取消
                    popushare.dismiss();
                    break;

            }
        }
    }
}
