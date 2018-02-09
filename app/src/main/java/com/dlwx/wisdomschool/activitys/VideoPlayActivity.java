package com.dlwx.wisdomschool.activitys;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private int type;
    private Pattern compile;
    private Matcher matcher;

    @Override
    protected void initView() {
        path = getIntent().getStringExtra("path");
        type = getIntent().getIntExtra("type",0);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        //添加播放控制条,还是自定义好点
        videoview.setMediaController(new MediaController(this));
        // 播放在线视频
        compile = Pattern.compile("http://");
        matcher = compile.matcher(path);
        if (!matcher.find()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                mVideoUri = FileProvider.getUriForFile(this, ctx.getPackageName() + ".fileprovider", new File(path));
            }else {
                mVideoUri = Uri.parse(path);
            }
        }else{
            mVideoUri = Uri.parse(path);
        }
        showLoading();
        videoview.setVideoPath(mVideoUri.toString());
        videoview.start();
        videoview.requestFocus();
    }

    @Override
    protected void initListener() {
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                disLoading();
            }
        });
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

