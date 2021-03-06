package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PublishLableAdapter;
import com.dlwx.wisdomschool.adapter.SendMessPicListAdapter;
import com.dlwx.wisdomschool.bean.PublishGrouBean;
import com.dlwx.wisdomschool.bean.PublishSaveTagBean;
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.fragments.EmojiconFragment;
import com.dlwx.wisdomschool.fragments.RecordOrPlayFragment;
import com.dlwx.wisdomschool.interfac.EmoInterface;
import com.dlwx.wisdomschool.interfac.VoiceRecordOrPlayListener;
import com.dlwx.wisdomschool.listener.EdiTextInPutListener;
import com.dlwx.wisdomschool.utiles.GroupPublishBigPic;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.UpFileUtiles;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class PublishGroupUpActivity extends BaseActivity implements VoiceRecordOrPlayListener.RecordListener ,
    VoiceRecordOrPlayListener.CleanOrAnewRecordListener{
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
    @BindView(R.id.iv_viecoplaytype)
    ImageView iv_viecoplaytype;
    @BindView(R.id.tv_sec)
    TextView tv_sec;
    @BindView(R.id.rl_voide)
    RelativeLayout rl_voide;
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
    private String classnames;

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = getIntent();
        videofile = intent.getStringExtra("videofile");
        images = intent.getStringArrayListExtra("images");
        String tagname = intent.getStringExtra("tagname");
        String tagId = intent.getStringExtra("tagId");

        setContentView(R.layout.activity_publish_group_up);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(tagId)) {
            PublishSaveTagBean saveTagBean = new PublishSaveTagBean();
            saveTagBean.setTagName(tagname);
            quality_sign = tagId;
            saveTagBeans.add(saveTagBean);
        }
        publishLableAdapter = new PublishLableAdapter(ctx, saveTagBeans);
        gvList.setAdapter(publishLableAdapter);

        if (!TextUtils.isEmpty(videofile)) {//判断是视频
            images = new ArrayList<>();
            ivMp3.setVisibility(View.GONE);
            ivAddpic.setVisibility(View.GONE);
            llVideo.setVisibility(View.VISIBLE);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videofile);
            Bitmap bitmap = mmr.getFrameAtTime();
            llVideo.setVisibility(View.VISIBLE);
            ivVideopic.setImageBitmap(ImageCrop(bitmap));
            File file = saveBitmapFile(bitmap);
            try{
                images.add(file.toString());
            }catch (Exception e){
                wch("错误信息："+e.getMessage());
            }

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
    public File saveBitmapFile(Bitmap bitmap) {
        File file = new File(createFile(),  "1.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Glide.with(ctx).load(file).into(ivVideopic);
        } catch (IOException e) {
            wch("转化错误："+e.getMessage());
            e.printStackTrace();
        }
        return file;
    }
    private File createFile() {
        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath();
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        return sd;
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

        etContent.addTextChangedListener(new EdiTextInPutListener(tvNum));

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
        VoiceRecordOrPlayListener.setAnewRecordListener(this);
    }
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.tv_aff, R.id.ll_seleteclass, R.id.tv_addlabel, R.id.iv_face, R.id.iv_addpic, R.id.iv_mp3,
            R.id.tv_anewrecord, R.id.rl_voide, R.id.iv_hind, R.id.ll_voice})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_aff://发送
                showLoading();
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
            case R.id.rl_voide://点击播放
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
                VoiceRecordOrPlayListener.visibilityLis.isVisibi(rl_record.getVisibility());
                break;
            case R.id.ll_voice://播放语音
                //todo
                iv_viecoplaytype.setImageResource(R.drawable.anim_viceo_play);
                VoicetranscribeAndPlayUtiles.play(iv_viecoplaytype,voiceFile);
                break;
        }
    }

    /**
     * 发布
     */
    private void submit() {
        if (TextUtils.isEmpty(classnames)) {
            Toast.makeText(ctx, "请选择班级", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(videofile)) {
            UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                @Override
                public void success(Response<String> response) {
                    Gson gson = new Gson();
                    UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                    if (upPicBean.getCode() == 200) {
                        wch("视频上传成功：" + response.body());
                        video = upPicBean.getBody().getFileid() + "";
                        upPic();

                    } else {
                        disLoading();
                        Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            UpFileUtiles.start(ctx, new File(videofile), "2", 0);

        } else {
            if (images.size() == 0) {
                Toast.makeText(ctx, "请先选择图片", Toast.LENGTH_SHORT).show();
                return;
            }
            //上传图片语音
            if (!TextUtils.isEmpty(voiceFile)) {//先判断是否有语音,有语音先上传语音
                UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                    @Override
                    public void success(Response<String> response) {
                        disLoading();
                        Gson gson = new Gson();
                        wch("语音上传：" + response.body());
                        UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            voice = upPicBean.getBody().getFileid() + "";
                            //上传成功后再多图上传
                            upPic();
                        } else {
                            Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                wch(voiceFile);
                UpFileUtiles.start(ctx, new File(voiceFile), "2", 0);
            } else {//没有语音开始多图上传
                upPic();
            }
        }
    }
    private int pos = -1;

    private void upPic() {
        pos++;
        if (pos >= images.size()) {
            //图片上传完成
            send();
            return;
        }
        UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
            @Override
            public void success(Response<String> response) {

                Gson gson = new Gson();
                UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                if (upPicBean.getCode() == 200) {
                    if (TextUtils.isEmpty(imgs)) {
                        imgs = upPicBean.getBody().getFileid() + "";
                    } else {
                        imgs = imgs + "," + upPicBean.getBody().getFileid();
                    }
                    upPic();
                } else {
                    disLoading();
                    Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        UpFileUtiles.start(ctx, new File(images.get(pos)), "1", 0);

    }
    private String imgs;//图片id
    private String video;//视频id
    private String voice;//语音id
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
        map.put("voice", voice);
        map.put("quality_sign", quality_sign);
        map.put("person_sign", person_sign);
        mPreenter.fetch(map, false, HttpUrl.SendgroupUp, "");
    }

    @Override
    public void showData(String s) {

        disLoading();
        wch(s);
        Gson gson = new Gson();
        PublishGrouBean publishGrouBean = gson.fromJson(s, PublishGrouBean.class);
        if (publishGrouBean.getCode() == 200) {
            String is_infour = publishGrouBean.getBody().getIs_infour();
            if (is_infour.equals("1")) {//四个大标签
                Intent intent = new Intent(ctx, PublishCompleteActivity.class);
                startActivity(intent);

            } else {

            }
            finish();
        } else {
            Toast.makeText(ctx, publishGrouBean.getResult(), Toast.LENGTH_SHORT).show();
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
               videofile = data.getStringExtra("videofile");
                wch("视频：" + videofile);
                images = new ArrayList<>();
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(videofile);
                Bitmap bitmap = mmr.getFrameAtTime();
                llVideo.setVisibility(View.VISIBLE);
                ivVideopic.setImageBitmap(ImageCrop(bitmap));
                File file = saveBitmapFile(bitmap);
                images.add(file.toString());

                break;
            case 2://图片
                List<String> imag = data.getStringArrayListExtra("images");
                for (int i = 0; i < imag.size(); i++) {
                    images.add(imag.get(i));
                }
                final SendMessPicListAdapter sendMessPicListAdapter = new SendMessPicListAdapter(ctx, images);
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
                        GroupPublishBigPic.showPic(ctx, gvPiclist, imageList, position);
                        GroupPublishBigPic.setDeletteListener(new GroupPublishBigPic.DeletteListener() {
                            @Override
                            public void delete(int postion) {
                                images.remove(position);
                                sendMessPicListAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                break;
            case 101://选择的班级
                classnames = data.getStringExtra("classnames");
                if ("仅自己可见".equals(classnames)) {
                    class_nos = "";
                }else{

                    createclass_nos = data.getStringExtra("createclass_nos");
                    addclass_nos = data.getStringExtra("addclass_nos");
                    class_nos = createclass_nos + "," + addclass_nos;
                }
                wch(classnames);
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
                    person_sign = tagid;
                } else {
                    person_sign = person_sign + "," + tagid;
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

    @Override
    public void backFile(String file) {
        voiceFile = file;
        //录制完成返回的语音文件
        ll_voice.setVisibility(View.VISIBLE);
        tv_sec.setText(VoicetranscribeAndPlayUtiles.durationTime(voiceFile));
    }

    @Override
    public void clean() {
        ll_voice.setVisibility(View.GONE);
        voiceFile = "";
    }

    @Override
    protected void onDestroy() {
        VoicetranscribeAndPlayUtiles.stopPlay();
        super.onDestroy();
    }
}
