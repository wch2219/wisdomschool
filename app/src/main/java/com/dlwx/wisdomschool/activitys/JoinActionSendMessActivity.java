package com.dlwx.wisdomschool.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
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
import com.dlwx.wisdomschool.adapter.SendMessPicListAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * 参与活动发布信息
 */
public class JoinActionSendMessActivity extends BaseActivity implements VoiceRecordOrPlayListener.RecordListener ,
        VoiceRecordOrPlayListener.CleanOrAnewRecordListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_aff)
    TextView tvAff;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.gv_piclist)
    RecyclerView gvPiclist;
    @BindView(R.id.iv_videopic)
    ImageView ivVideopic;
    @BindView(R.id.rl_voide)
    RelativeLayout rlVoide;
    @BindView(R.id.tv_anewrecord)
    TextView tvAnewrecord;
    @BindView(R.id.ll_video)
    LinearLayout llVideo;
    @BindView(R.id.iv_viecoplaytype)
    ImageView ivViecoplaytype;
    @BindView(R.id.tv_sec)
    TextView tvSec;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    @BindView(R.id.iv_mp3)
    ImageView ivMp3;
    @BindView(R.id.iv_addpic)
    ImageView ivAddpic;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.iv_hind)
    ImageView ivHind;
    @BindView(R.id.emojicon_menu_container)
    FrameLayout emojiconMenuContainer;
    @BindView(R.id.rl_record)
    FrameLayout rlRecord;
    private ArrayList<String> images;
    private String videofile;
    private SendMessPicListAdapter sendMessPicListAdapter;
    private int requestCodeVideo = 1;//视频
    private int requestCodePic = 2;//图片
    private String voiceFile;
    private String id;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        videofile = intent.getStringExtra("videofile");
        id = intent.getStringExtra("id");
        setContentView(R.layout.activity_join_action_send_mess);
        ButterKnife.bind(this);
        if (images == null) {
            images = new ArrayList<>();
        }
    }

    @Override
    protected void initData() {
        tvTitle.setText("发表活动成果");
        initTabBar(toolBar);

        if (!TextUtils.isEmpty(videofile)) {//判断是视频
            images = new ArrayList<>();
            ivMp3.setVisibility(View.GONE);
            ivAddpic.setVisibility(View.GONE);
            llVideo.setVisibility(View.VISIBLE);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videofile);
            try {
                FileInputStream fis = new FileInputStream(videofile);
                wch("视频大小："+fis.available());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                    GroupPublishBigPic.showPic(ctx, ivAddpic, imageList, position);
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

        final LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        gvPiclist.setLayoutManager(manager);
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


    @OnClick({R.id.tv_aff, R.id.tv_anewrecord, R.id.ll_video, R.id.ll_voice, R.id.iv_mp3, R.id.iv_addpic,
           R.id.iv_face, R.id.iv_hind})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_aff:
                submit();
                break;
            case R.id.tv_anewrecord://重录
                intent = new Intent(ctx, RecordVideoActivity.class);
                startActivityForResult(intent, requestCodeVideo);
                break;
            case R.id.ll_video://视频播放
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) ctx, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,

                    }, 1002);

                }else{
                    intent = new Intent(ctx, VideoPlayActivity.class);
                    intent.putExtra("path", videofile);
                    startActivity(intent);
                }

                break;
            case R.id.ll_voice://语音
                break;
            case R.id.iv_mp3://录音
                int rl_recordVisibility = rlRecord.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int j = rl_recordVisibility == View.VISIBLE ? View.GONE : View.VISIBLE;
                ivHind.setVisibility(j);
                rlRecord.setVisibility(j);
                if (j == View.VISIBLE) {
                    inputhind(ivFace);
                    emojiconMenuContainer.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_addpic://添加图片
                intent = new Intent(ctx, AllPicActivity.class);
                intent.putExtra("currnum", images.size());
                startActivityForResult(intent, 2);
                inputhind(ivFace);
                break;
            case R.id.iv_face:
                int visibility = emojiconMenuContainer.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int i = visibility == View.VISIBLE ? View.GONE : View.VISIBLE;
                emojiconMenuContainer.setVisibility(i);
                ivHind.setVisibility(i);

                if (i == View.VISIBLE) {
                    inputhind(ivFace);
                    rlRecord.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_hind:
                emojiconMenuContainer.setVisibility(View.GONE);
                rlRecord.setVisibility(View.GONE);
                ivHind.setVisibility(View.GONE);
                VoiceRecordOrPlayListener.visibilityLis.isVisibi(rlRecord.getVisibility());
                break;
        }
    }
    /**
     * 发布
     */
    private void submit() {

        if (!TextUtils.isEmpty(videofile)) {
            UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                @Override
                public void success(Response<String> response) {
                    disLoading();
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
            showLoading();
            UpFileUtiles.start(ctx, new File(videofile), "2", 0);

        } else {

            //上传图片语音
            if (!TextUtils.isEmpty(voiceFile)) {//先判断是否有语音,有语音先上传语音
                showLoading();
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
                            if (images.size() == 0) {
                               send();
                                return;
                            }else{
                                upPic();
                            }
                        } else {
                            Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                wch(voiceFile);
                UpFileUtiles.start(ctx, new File(voiceFile), "2", 0);
            } else {
                //没有语音开始多图上传
                if (images.size() == 0) {//没有图片直接上传
                    send();
                    return;
                }else{
                    upPic();
                }
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
    /**
     * 发送
     */
    private void send() {
        HttpType = 2;
        String content = etContent.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("pl_content", content);
        map.put("pl_img", imgs);
        map.put("irid", id);
        map.put("pl_video", video);
        map.put("pl_voice", voice);
        if (!TextUtils.isEmpty(voice)) {
            map.put("pl_voice_sec",tvSec.getText().toString());
        }
        mPreenter.fetch(map, false, HttpUrl.JoinAction, "");
    }
    @Override
    public void backFile(String file) {
        voiceFile = file;
        //录制完成返回的语音文件
        llVoice.setVisibility(View.VISIBLE);
        tvSec.setText(VoicetranscribeAndPlayUtiles.durationTime(voiceFile)+"''");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
            finish();
        }
        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clean() {
        llVideo.setVisibility(View.GONE);
        voiceFile = "";
    }

    @Override
    protected void onDestroy() {
        VoicetranscribeAndPlayUtiles.stopPlay();
        super.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {

            return;
        }
        Intent intent;
        switch (requestCode) {
            case 1://视频
                images = new ArrayList<>();
                videofile = data.getStringExtra("videofile");
                wch("视频：" + videofile);
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
}
