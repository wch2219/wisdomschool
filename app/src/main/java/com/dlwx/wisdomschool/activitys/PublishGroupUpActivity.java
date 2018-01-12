package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PublishLableAdapter;
import com.dlwx.wisdomschool.adapter.SendMessPicListAdapter;
import com.dlwx.wisdomschool.bean.PublishSaveTagBean;
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.fragments.EmojiconFragment;
import com.dlwx.wisdomschool.fragments.RecordOrPlayFragment;
import com.dlwx.wisdomschool.interfac.EmoInterface;
import com.dlwx.wisdomschool.interfac.VoiceRecordOrPlayListener;
import com.dlwx.wisdomschool.utiles.GroupPublishBigPic;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.UpFileUtiles;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 发布成长
 */
public class PublishGroupUpActivity extends BaseActivity implements VoiceRecordOrPlayListener.RecordListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_aff)
    TextView tvAff;
    @BindView(R.id.ll_seleteclass)
    LinearLayout llSeleteclass;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.gv_piclist)
    RecyclerView gvPiclist;
    @BindView(R.id.iv_videopic)
    ImageView ivVideopic;
    @BindView(R.id.tv_anewrecord)
    TextView tvAnewrecord;
    @BindView(R.id.ll_video)
    LinearLayout llVideo;
    @BindView(R.id.tv_addlabel)
    TextView tvAddlabel;
    @BindView(R.id.ll_voice)
    LinearLayout ll_voice;
    @BindView(R.id.gv_list)
    RecyclerView gvList;
    @BindView(R.id.iv_mp3)
    ImageView ivMp3;
    @BindView(R.id.iv_addpic)
    ImageView ivAddpic;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.emojicon_menu_container)
    FrameLayout emojiconMenuContainer;
    @BindView(R.id.rl_record)
    FrameLayout rl_record;
    @BindView(R.id.tv_classnames)
    TextView tv_classnames;
    @BindView(R.id.iv_hind)
    ImageView iv_hind;
    private String videofile;
    private int requestCodeVideo = 1;//视频
    private int requestCodePic = 2;//图片
    private int requestCodeClass = 101;//班级
    private int requestCodeTag = 10;//标签
    private PublishLableAdapter publishLableAdapter;
    private String class_nos;//选择的班级
    private ArrayList<String> images = new ArrayList<>();
    private SendMessPicListAdapter sendMessPicListAdapter;
    private String voiceFile;//语音文件

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = getIntent();
        videofile = intent.getStringExtra("videofile");
        images = intent.getStringArrayListExtra("images");
        setContentView(R.layout.activity_publish_group_up);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(videofile)) {//判断是视频
            ivMp3.setVisibility(View.GONE);
            ivAddpic.setVisibility(View.GONE);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videofile);
            Bitmap bitmap = mmr.getFrameAtTime();
            llVideo.setVisibility(View.VISIBLE);
            ivVideopic.setImageBitmap(ImageCrop(bitmap));
        } else {
            llVideo.setVisibility(View.GONE);
            ivMp3.setVisibility(View.VISIBLE);
            ivAddpic.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(ctx);
            manager.setOrientation(LinearLayout.HORIZONTAL);
            gvPiclist.setLayoutManager(manager);
            sendMessPicListAdapter = new SendMessPicListAdapter(ctx, images);
            gvPiclist.setAdapter(sendMessPicListAdapter);
            sendMessPicListAdapter.setOnItemClickListener(new BaseRecrviewAdapter.OnItemClickListener() {
                @Override
                public void setOnClick(final int position) {
                    List<Image> imageList = new ArrayList<>();
                    for (int i = 0; i < images.size(); i++) {
                        Image image = new Image();
                        image.setPath(images.get(i));
                        image.setOldposition(i);
                        imageList.add(image);
                    }

                    GroupPublishBigPic.showPic(ctx, gvList, imageList, position);
                    GroupPublishBigPic.setDeletteListener(new GroupPublishBigPic.DeletteListener() {
                        @Override
                        public void delete(int postion) {
                            images.remove(position);
                            sendMessPicListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int centH = h / 2;
        int wh = w * 3 / 4;
        int retY = (centH - centH / 2);
        //下面这句是关键
        return Bitmap.createBitmap(bitmap, 0, retY, wh, wh, null, false);
    }

    @Override
    protected void initData() {
        tvTitle.setText("发送智慧成长");
        initTabBar(toolBar);
        final LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        gvList.setLayoutManager(manager);
        //初始化表情
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EmojiconFragment emojiconFragment = new EmojiconFragment();
        fragmentTransaction.add(R.id.emojicon_menu_container, emojiconFragment);
        fragmentTransaction.show(emojiconFragment);
        fragmentTransaction.commit();

        //初始化录音和播放
        FragmentTransaction recordTransaction = getSupportFragmentManager().beginTransaction();
        RecordOrPlayFragment recordOrPlayFragment = new RecordOrPlayFragment();
        recordTransaction.add(R.id.rl_record, recordOrPlayFragment);
        recordTransaction.show(recordOrPlayFragment);
        recordTransaction.commit();

//        etContent.addTextChangedListener(new EdiTextInPutListener());

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
    protected void initListener() {
        VoiceRecordOrPlayListener.setRecordListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.tv_aff, R.id.ll_seleteclass, R.id.tv_addlabel, R.id.iv_face, R.id.iv_addpic, R.id.iv_mp3,
            R.id.tv_anewrecord, R.id.iv_videopic, R.id.iv_hind,R.id.ll_voice})
    public void onViewClicked(View view){
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_aff://发送
                submit();

                break;
            case R.id.ll_seleteclass://选择班级
                intent = new Intent(ctx, SeletePublishClassActivity.class);
                intent.putExtra("addclass_nos", addclass_nos);
                intent.putExtra("createclass_nos", createclass_nos);
                startActivityForResult(intent, requestCodeClass);
                break;
            case R.id.tv_addlabel://添加标签
                startActivityForResult(new Intent(ctx, AddLableActivity.class), requestCodeTag);
                break;
            case R.id.iv_face://表情
                int visibility = emojiconMenuContainer.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int i = visibility == View.VISIBLE ? View.GONE : View.VISIBLE;
                emojiconMenuContainer.setVisibility(i);
                iv_hind.setVisibility(i);
                if (i == View.VISIBLE) {

                    rl_record.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_anewrecord://重录
                intent = new Intent(ctx, RecordVideoActivity.class);
                startActivityForResult(intent, requestCodeVideo);
                break;
            case R.id.iv_videopic://点击播放
                intent = new Intent(ctx, VideoPlayActivity.class);
                intent.putExtra("path", videofile);
                startActivity(intent);
                break;
            case R.id.iv_mp3://录音
                int rl_recordVisibility = rl_record.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int j = rl_recordVisibility == View.VISIBLE ? View.GONE : View.VISIBLE;
                iv_hind.setVisibility(j);
                rl_record.setVisibility(j);
                if (j == View.VISIBLE) {

                    emojiconMenuContainer.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_addpic://添加图片
                intent = new Intent(ctx, AllPicActivity.class);
                intent.putExtra("currnum", images.size());
                startActivityForResult(intent, 2);
                break;
            case R.id.iv_hind:
                emojiconMenuContainer.setVisibility(View.GONE);
                rl_record.setVisibility(View.GONE);
                iv_hind.setVisibility(View.GONE);
                break;
            case R.id.ll_voice://播放语音
                //todo
                VoicetranscribeAndPlayUtiles.play();
                break;
        }
    }

    /**
     * 发布
     */
    private void submit() {
        if (TextUtils.isEmpty(class_nos)) {
            Toast.makeText(ctx, "请选择班级", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(videofile)) {
            UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                @Override
                public void success(Response<String> response) {
                    disLoading();
                    Gson gson = new Gson();
                    UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                    if (upPicBean.getCode() == 200) {
                        video = upPicBean.getBody().getFileid() + "";
                        send();

                    } else {
                        Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            UpFileUtiles.start(ctx, new File(videofile), "2", 0);

        } else {
            //上传图片语音
            if (!TextUtils.isEmpty(voiceFile)) {//先判断是否有语音,有语音先上传语音
                UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                    @Override
                    public void success(Response<String> response) {
                        disLoading();
                        Gson gson = new Gson();
                        wch("语音上传："+response.body());
                        UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            video = upPicBean.getBody().getFileid() + "";
                            //上传成功后再多图上传

                        } else {
                            Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                wch(voiceFile);
                UpFileUtiles.start(ctx, new File(voiceFile), "2", 0);
            }
        }
    }

    private String imgs;//图片id
    private String video;//视频id
    private String quality_sign;//综合素质标签
    private String person_sign;//个人素质标签
    private String addclass_nos;//添加的班级
    private String createclass_nos;//创建的班级

    /**
     * 发送
     */
    private void send() {
        HttpType = 2;
        String content = etContent.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("record_bf", content);
        map.put("imgs", imgs);
        map.put("video", video);
        map.put("quality_sign", quality_sign);
        map.put("person_sign", person_sign);
        mPreenter.fetch(map, false, HttpUrl.SendgroupUp, "");

    }

    @Override
    public void showData(String s) {

        disLoading();
        wch(s);
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
                wch("视频：" + vodeofile);
                intent = new Intent(ctx, PublishGroupUpActivity.class);
                intent.putExtra("videofile", vodeofile);
                startActivity(intent);
                break;
            case 2://图片
                List<String> imag = data.getStringArrayListExtra("images");
                for (int i = 0; i < imag.size(); i++) {
                    images.add(imag.get(i));
                }
                gvPiclist.setAdapter(new SendMessPicListAdapter(ctx, images));
                break;
            case 101://选择的班级
                createclass_nos = data.getStringExtra("createclass_nos");
                addclass_nos = data.getStringExtra("addclass_nos");
                String classnames = data.getStringExtra("classnames");
                class_nos = createclass_nos + "," + addclass_nos;
                tv_classnames.setText(classnames);
                break;

            case 10://标签
                String tag = data.getStringExtra("tag");
                String tagid = data.getStringExtra("tagid");
                String qusign = data.getStringExtra("quality_sign");//综合素质标签id
                final PublishSaveTagBean saveTagBean = new PublishSaveTagBean();
                if (TextUtils.isEmpty(quality_sign)) {
                    quality_sign = qusign;
                } else {
                    quality_sign = quality_sign + "," + qusign;
                }
                if (TextUtils.isEmpty(person_sign)) {
                    person_sign = tag;
                } else {
                    person_sign = person_sign + "," + tag;
                }
                if (TextUtils.isEmpty(tag)) {

                    saveTagBean.setTagName(qusign);
                } else {
                    saveTagBean.setTagName(tag);
                }
                saveTagBeans.add(saveTagBean);
                publishLableAdapter = new PublishLableAdapter(ctx, saveTagBeans);
                gvList.setAdapter(publishLableAdapter);
                publishLableAdapter.setDeleteTagListener(new PublishLableAdapter.DeleteTagListener() {
                    @Override
                    public void delete(int postion) {
                        saveTagBeans.remove(postion);
                        publishLableAdapter.notifyDataSetChanged();
                    }
                });

                break;
        }
    }

    /**
     * 存放tag标签的集合
     */
    private List<PublishSaveTagBean> saveTagBeans = new ArrayList<>();

    /**
     * 播放或者录制
     *
     * @param type
     */
    private void showRecordorPlay(int type) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_record_or_play, null);

    }

    @Override
    public void backFile(String file) {
        voiceFile = file;
        //录制完成返回的语音文件
            ll_voice.setVisibility(View.VISIBLE);
    }
}
