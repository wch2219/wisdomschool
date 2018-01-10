package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PublishLableAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布成长
 */
public class PublishGroupUpActivity extends BaseActivity {
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
    @BindView(R.id.iv_addpic)
    ImageView iv_addpic;
    @BindView(R.id.tv_addlabel)
    TextView tvAddlabel;
    @BindView(R.id.gv_list)
    RecyclerView gvList;
    @BindView(R.id.iv_face1)
    ImageView ivFace1;
    @BindView(R.id.ll_face)
    LinearLayout ll_face;
    @BindView(R.id.ll_picandrtext)
    LinearLayout ll_picandrtext;
    @BindView(R.id.ll_uppic)
    LinearLayout ll_uppic;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.iv_send)
    ImageView ivSend;
    @BindView(R.id.iv_album)
    ImageView ivAlbum;
    @BindView(R.id.ll_album)
    LinearLayout llAlbum;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_camera)
    LinearLayout llCamera;
    @BindView(R.id.ll_video)
    LinearLayout ll_video;
    @BindView(R.id.iv_videopic)
    ImageView iv_videopic;
    @BindView(R.id.tv_anewrecord)//重录
    TextView tv_anewrecord;
    private String videofile;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        videofile = intent.getStringExtra("videofile");
        setContentView(R.layout.activity_publish_group_up);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(videofile)) {//判断是视频
            iv_addpic.setVisibility(View.GONE);
            ll_video.setVisibility(View.VISIBLE);
            ll_picandrtext.setVisibility(View.GONE);
            ll_face.setVisibility(View.VISIBLE);
            ll_uppic.setVisibility(View.GONE);

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videofile);
            Bitmap bitmap = mmr.getFrameAtTime();
//            Glide.with(ctx).load(bitmap).into(iv_videopic);

            iv_videopic.setImageBitmap(ImageCrop(bitmap));

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
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        gvList.setLayoutManager(manager);
        gvList.setAdapter(new PublishLableAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_aff, R.id.ll_seleteclass, R.id.iv_addpic, R.id.tv_addlabel, R.id.iv_face, R.id.iv_face1,
            R.id.iv_send, R.id.ll_album, R.id.ll_camera,R.id.tv_anewrecord,R.id.iv_videopic})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_aff://发送
                break;
            case R.id.ll_seleteclass://选择班级
                intent = new Intent(ctx, SeletePublishClassActivity.class);
               startActivityForResult(intent,101);
                break;
            case R.id.iv_addpic://添加图片
                break;
            case R.id.tv_addlabel://添加标签
                startActivity(new Intent(ctx, AddLableActivity.class));
                break;
            case R.id.iv_face://表情
                break;

            case R.id.iv_face1://表情1
                break;


            case R.id.iv_send://发送想说的话
                break;
            case R.id.ll_album://相册
                break;
            case R.id.ll_camera://相机
                break;
            case R.id.tv_anewrecord://重录
                intent = new Intent(ctx, RecordVideoActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_videopic://点击播放
                intent = new Intent(ctx,VideoPlayActivity.class);
                intent.putExtra("path",videofile);
                startActivity(intent);
                break;

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
                String vodeofile = data.getStringExtra("vodeofile");
                wch("视频："+vodeofile);
                intent = new Intent(ctx,PublishGroupUpActivity.class);
                intent.putExtra("videofile",vodeofile);
                startActivity(intent);
                break;
            case 2://图片
                break;
            case 101://选择的班级

                break;
        }
    }
}
