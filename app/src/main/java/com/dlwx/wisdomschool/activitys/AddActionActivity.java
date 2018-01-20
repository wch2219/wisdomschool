package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.baselib.view.MyRecyclerView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.RecordPicListAdapter;
import com.dlwx.wisdomschool.bean.ActionDescBean;
import com.dlwx.wisdomschool.utiles.EmoSwichUtiles;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

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
public class AddActionActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.rl_member)
    RelativeLayout rlMember;
    @BindView(R.id.iv_leftHead)
    ImageView ivLeftHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.gv_piclistleft)
    MyGridView gvPiclistleft;
    @BindView(R.id.iv_videoleft)
    ImageView ivVideoleft;
    @BindView(R.id.rl_voide)
    RelativeLayout rlVoide;
    @BindView(R.id.iv_viecoplaytype)
    ImageView ivViecoplaytype;
    @BindView(R.id.tv_sec)
    TextView tvSec;
    @BindView(R.id.ll_voiceleft)
    LinearLayout llVoiceleft;
    @BindView(R.id.rv_list)
    MyRecyclerView rvList;
    @BindView(R.id.tv_joinaction)
    TextView tvJoinaction;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.contentleft)
    TextView contentleft;
    private String id;//活动id
    private PopupWindow bottompopu;
    private ArrayList<String> images;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        setContentView(R.layout.activity_add_action);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧活动");
        initTabBar(toolBar);


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

    @OnClick({R.id.rl_share, R.id.rl_member, R.id.rl_voide, R.id.ll_voiceleft, R.id.tv_joinaction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_share:
                break;
            case R.id.rl_member:
                break;
            case R.id.rl_voide:
                break;
            case R.id.ll_voiceleft:
                break;
            case R.id.tv_joinaction:
                showPopuBottom();
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
        bottompopu.showAtLocation(ivLeftHead, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            ActionDescBean actionDescBean = gson.fromJson(s, ActionDescBean.class);
            if (actionDescBean.getCode() == 200) {
                ActionDescBean.BodyBean body = actionDescBean.getBody();
                Glide.with(ctx).load(body.getHeader_pic()).into(ivLeftHead);
                tvName.setText(body.getClass_name() + "(" + body.getNickname() + ")");
                tv_type.setText(body.getTheme_name());
                List<String> content_pic = body.getContent_pic();
                //突破片的数量
                if (content_pic.size() == 1) {

                    gvPiclistleft.setNumColumns(1);
                } else if (content_pic.size() == 2) {
                    gvPiclistleft.setNumColumns(2);
                } else {

                    gvPiclistleft.setNumColumns(3);
                }
                gvPiclistleft.setAdapter(new RecordPicListAdapter(ctx, content_pic));
                if (TextUtils.isEmpty(body.getContent_voice())) {
                    llVoiceleft.setVisibility(View.GONE);
                } else {
                    llVoiceleft.setVisibility(View.VISIBLE);
                }
                EmoSwichUtiles.toSwich(ctx, contentleft, body.getContent());
            } else {
                Toast.makeText(ctx, actionDescBean.getResult(), Toast.LENGTH_SHORT).show();
            }
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

    private   class ViewHolderJoin implements View.OnClickListener {
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
                    intent = new Intent(ctx,AllPicActivity.class);
                    startActivityForResult(intent,2);
                    break;
                case R.id.tv_voice:
                    bottompopu.dismiss();
                    break;
                case R.id.tv_video:
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
         switch (requestCode){
                    case 2://图片
                        ArrayList<String> images = data.getStringArrayListExtra("images");
                        intent = new Intent(ctx,JoinActionSendMessActivity.class);
                        intent.putStringArrayListExtra("images",images);
                        startActivity(intent);
                        break;
                }
    }
}
