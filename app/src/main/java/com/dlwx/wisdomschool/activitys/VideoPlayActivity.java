package com.dlwx.wisdomschool.activitys;

import android.widget.RelativeLayout;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.views.MyNiceVideoPlayer;
import com.dlwx.wisdomschool.views.MyTxVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频播放
 */
public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.nvplayert)
    MyNiceVideoPlayer nvplayert;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    private String path;

    @Override
    protected void initView() {
        path = getIntent().getStringExtra("path");
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        nvplayert.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        nvplayert.setUp(path, null);
        MyTxVideoPlayerController controller = new MyTxVideoPlayerController(this);
        controller.setTitle("");
        nvplayert.setController(controller);
        nvplayert.start();
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
        finish();

    }
}

