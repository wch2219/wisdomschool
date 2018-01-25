package com.dlwx.wisdomschool.activitys;

import android.net.Uri;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频播放
 */
public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.videoview)
    VideoView videoview;
    private String path;
    private Uri mVideoUri;

    @Override
    protected void initView() {
        path = getIntent().getStringExtra("path");
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        //添加播放控制条,还是自定义好点
        videoview.setMediaController(new MediaController(this));
        // 播放在线视频
        mVideoUri = Uri.parse(path);
        videoview.setVideoPath(mVideoUri.toString());

        videoview.start();
        videoview.requestFocus();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
//            nvplayert.releasePlayer();
        if (videoview.isPlaying()) {
            videoview.stopPlayback();
        }
        finish();

    }

}

