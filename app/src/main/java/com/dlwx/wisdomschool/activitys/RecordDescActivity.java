package com.dlwx.wisdomschool.activitys;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.RecordCommentListAdapter;
import com.dlwx.wisdomschool.adapter.RecordPicListAdapter;
import com.dlwx.wisdomschool.anima.ShakeAnima;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.RecordListBean;
import com.dlwx.wisdomschool.fragments.EmojiconFragment;
import com.dlwx.wisdomschool.interfac.EmoInterface;
import com.dlwx.wisdomschool.utiles.EmoSwichUtiles;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.LookPic;
import com.dlwx.wisdomschool.utiles.PopuShareUtiles;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;
import com.google.gson.Gson;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 纪录详情
 */
public class RecordDescActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_classname)
    TextView tvClassname;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.rl_voide)
    RelativeLayout rlVoide;
    @BindView(R.id.iv_playing)
    ImageView ivPlaying;
    @BindView(R.id.tv_viceotime)
    TextView tvViceotime;
    @BindView(R.id.rl_viceo)
    RelativeLayout rlViceo;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    @BindView(R.id.iv_praise)
    ImageView ivPraise;
    @BindView(R.id.tv_praisenum)
    TextView tvPraisenum;
    @BindView(R.id.ll_praise)
    LinearLayout llPraise;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.lv_comment)
    MyListView lvComment;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_sendsms)
    TextView tvSendsms;
    @BindView(R.id.emojicon_menu_container)
    FrameLayout emojiconMenuContainer;
    private RecordListBean.BodyBean bodyBean;
    private List<RecordListBean.BodyBean.PinglunHuifu> pinglun_huifu = new ArrayList<>();
    private RecordCommentListAdapter recordCommentListAdapter;

    @Override
    protected void initView() {
        bodyBean = (RecordListBean.BodyBean) getIntent().getSerializableExtra("bodyBean");
        setContentView(R.layout.activity_record_desc);
        ButterKnife.bind(this);
        //初始化表情
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EmojiconFragment emojiconFragment = new EmojiconFragment();
        fragmentTransaction.add(R.id.emojicon_menu_container, emojiconFragment);
        fragmentTransaction.show(emojiconFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("纪录详情");
        //头像
        Glide.with(ctx).load(bodyBean.getHeader_pic()).into(ivPic);
        //昵称
        tvName.setText(bodyBean.getNickname());
//        vh.tv_classname.setText(bodyBean.getC);
        //创建时间
        tvTime.setText(bodyBean.getCreate_time());
        //突破片的数量
        if (bodyBean.getImgs().size() ==1) {

            gvList.setNumColumns(1);
        }else if (bodyBean.getImgs().size() == 2) {
            gvList.setNumColumns(2);
        }else{
            gvList.setNumColumns(3);
        }
        //判断是是视频还是图片
        if (TextUtils.isEmpty(bodyBean.getVideo())) {
            gvList.setVisibility(View.VISIBLE);
            rlVoide.setVisibility(View.GONE);
        }else{
            gvList.setVisibility(View.GONE);
            rlViceo.setVisibility(View.VISIBLE);
            Glide.with(ctx).asBitmap().load(bodyBean.getVideo()).apply(new RequestOptions().centerCrop()).into(ivVideo);
        }
        //展示图片列表
        List<String> imgs = bodyBean.getImgs();
        gvList.setAdapter(new RecordPicListAdapter(ctx,imgs));
        //语音
        if (TextUtils.isEmpty(bodyBean.getVoice())) {
            rlViceo.setVisibility(View.GONE);

        }else{
            rlViceo.setVisibility(View.VISIBLE);
            String time = VoicetranscribeAndPlayUtiles.durationTime(bodyBean.getVoice());
           tvViceotime.setText(time);
        }

//        标签
        List<String> mVals = new ArrayList<>();
        for (int i = 0; i < bodyBean.getPerson_sign().size(); i++) {
            mVals.add((String) bodyBean.getPerson_sign().get(i));
        }
        for (int i = 0; i < bodyBean.getQuality_sign().size(); i++) {
            mVals.add((String) bodyBean.getQuality_sign().get(i));
        }
        idFlowlayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(ctx).inflate(R.layout.tv_tag,
                        idFlowlayout, false);
                tv.setText("#"+s+"#");
                return tv;
            }
        });

        //赞
        final int is_praise = bodyBean.getPraise().getIs_praise();
        if (is_praise == 0) {
            Glide.with(ctx).load(R.mipmap.icon_czjlzana).into(ivPraise);
        }else{
            Glide.with(ctx).load(R.mipmap.icon_czjlzan).into(ivPraise);
        }
        tvPraisenum.setText("赞("+bodyBean.getPraise().getNum()+")");
        //评论列表
        pinglun_huifu = bodyBean.getPinglun_huifu();
        if (pinglun_huifu != null) {
            recordCommentListAdapter = new RecordCommentListAdapter(ctx, pinglun_huifu);
            lvComment.setAdapter(recordCommentListAdapter);
        }
        EmoSwichUtiles.toSwich(ctx,tvConnect,bodyBean.getRecord_bf());

        /**
         * 图片点击
         */
        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> imgs1 = bodyBean.getImgs();
                List<Image> images = new ArrayList<>();
                for (int i = 0; i < imgs1.size(); i++) {
                    Image image = new Image();
                    image.setPath(imgs1.get(i));
                    image.setOldposition(i);
                    images.add(image);
                }
                LookPic.showPic(ctx,tvClassname,images,position);
            }
        });
        llComment.setVisibility(View.GONE);
        lvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordListBean.BodyBean.PinglunHuifu pinglunHuifu = pinglun_huifu.get(position);

                RecordDescActivity.this.hf_name = pinglunHuifu.getHf_name();
                RecordDescActivity.this.pl_name = pinglunHuifu.getPl_name();
                RecordDescActivity.this.hf_id = pinglunHuifu.getHfid();
                RecordDescActivity.this.pl_id = pinglunHuifu.getPlid();

                if (!TextUtils.isEmpty(pinglunHuifu.getHfid())) {
                    if (pinglunHuifu.getMy_id().equals(pinglunHuifu.getHfid())) {

                        return;
                    }
                    RecordDescActivity.this.otherId = pinglunHuifu.getHfid();
                }else{
                    if (pinglunHuifu.getMy_id().equals(pinglunHuifu.getPlid())) {

                        return;
                    }
                    RecordDescActivity.this.otherId = pinglunHuifu.getPlid();
                }
                etContent.setFocusable(true);
                etContent.setFocusableInTouchMode(true);
                etContent.requestFocus();
                mHandler.sendEmptyMessageDelayed(0, 200);
            }
        });
    }
    private String recorid, otherId,hf_name,pl_name,hf_id,pl_id,content;
    @Override
    protected void initListener() {
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
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    

    @OnClick({R.id.ll_share, R.id.rl_voide, R.id.rl_viceo, R.id.ll_praise, R.id.ll_comment, R.id.iv_face, R.id.tv_sendsms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_share://分享
                new PopuShareUtiles(ctx,ivFace);
                break;
            case R.id.rl_voide://
                Intent intent = new Intent(ctx, VideoPlayActivity.class);
                intent.putExtra("path", bodyBean.getVideo());
                ctx.startActivity(intent);
                break;
            case R.id.rl_viceo:
               ivPlaying.setImageResource(R.drawable.anim_viceo_play);
                VoicetranscribeAndPlayUtiles.play(ivPlaying,bodyBean.getVoice());
                break;
            case R.id.ll_praise:
                ObjectAnimator animator = ShakeAnima.tada(ivPraise, 1f);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setDuration(10);
                animator.start();

                Map<String, String> map = new HashMap<>();
                map.put("token", Token);
                map.put("id", recorid);
                map.put("type", bodyBean.getPraise().getIs_praise()+1+"");
                HttpType = 2;
                mPreenter.fetch(map, true, HttpUrl.RecordPrise, "");
                break;
            case R.id.ll_comment:
                break;
            case R.id.iv_face:
                changeEmo();
                break;
            case R.id.tv_sendsms:
                String content = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(ctx, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpType = 1;
                RecordDescActivity.this.content = content;
                Map<String, String> map1 = new HashMap<>();
                map1.put("token", Token);
                map1.put("id", bodyBean.getId());
                if (otherId == null) {
                    map1.put("view_content", content);
                    mPreenter.fetch(map1, false, HttpUrl.RecordComment, "");
                } else {
                    map1.put("reply_content", content);
                    map1.put("uid", otherId);
                    mPreenter.fetch(map1, false, HttpUrl.Recordreply, "");
                }
                etContent.setText("");
                changeEmo();
                break;
        }
    }
        private void changeEmo(){
            int visibility = emojiconMenuContainer.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
            int i = visibility == View.VISIBLE ? View.GONE : View.VISIBLE;
            emojiconMenuContainer.setVisibility(i);
        }
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            submitComm(s, gson);
        }else{
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                int is_praise1 = bodyBean.getPraise().getIs_praise();
                if (is_praise1 == 0) {
                    bodyBean.getPraise().setIs_praise(1);
                    bodyBean.getPraise().setNum(bodyBean.getPraise().getNum()+1);
                }else{
                    bodyBean.getPraise().setIs_praise(0);
                    bodyBean.getPraise().setNum(bodyBean.getPraise().getNum()-1);
                }
                //赞
                final int is_praise = bodyBean.getPraise().getIs_praise();
                if (is_praise == 0) {
                    Glide.with(ctx).load(R.mipmap.icon_czjlzana).into(ivPraise);
                }else{
                    Glide.with(ctx).load(R.mipmap.icon_czjlzan).into(ivPraise);
                }
                tvPraisenum.setText("赞("+bodyBean.getPraise().getNum()+")");
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void submitComm(String s, Gson gson) {
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
            RecordListBean.BodyBean.PinglunHuifu  huifu = new RecordListBean.BodyBean.PinglunHuifu();
            if (otherId == null) {
                huifu.setContent(content);
                huifu.setPl_name(sp.getString(SpUtiles.Nickname,""));
            }else{
                huifu.setContent(content);
                huifu.setHf_name(sp.getString(SpUtiles.Nickname,""));
                if (TextUtils.isEmpty(hf_name)) {
                    huifu.setPl_name(pl_name);
                }else{
                    huifu.setPl_name(hf_name);
                }

            }
            pinglun_huifu.add(huifu);
            if (recordCommentListAdapter != null) {
                recordCommentListAdapter.notifyDataSetChanged();
            }
            otherId = null;
        }
        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
    }

    private InputMethodManager imm;

    public void inputshoworhind() {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        boolean active = imm.isActive();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            inputshoworhind();
        }
    };
}
