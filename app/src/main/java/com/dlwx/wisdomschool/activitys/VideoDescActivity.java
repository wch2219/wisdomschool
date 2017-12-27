package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频播放界面
 */
public class VideoDescActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.nvplayert)
    NiceVideoPlayer nvplayert;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_video_desc);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧驾校攻略区");
        initTabBar(toolBar);
//        nvplayert.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
//        nvplayert.setUp("", null);

//        TxVideoPlayerController controller = new TxVideoPlayerController(this);
//        controller.setTitle(mTitle);
//        controller.setImage(mImageUrl);
//        mNiceVideoPlayer.setController(controller);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.rl_share, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_share:
                break;
            case R.id.btn_next:
                break;
        }
    }
}
