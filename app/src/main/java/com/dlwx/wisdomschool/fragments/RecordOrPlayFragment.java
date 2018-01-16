package com.dlwx.wisdomschool.fragments;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.interfac.VoiceRecordOrPlayListener;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 录音和播放
 */
public class RecordOrPlayFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener{

    @BindView(R.id.cb_record)
    CheckBox cbRecord;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.cb_play)
    CheckBox cbPlay;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_play)
    LinearLayout llPlay;
    @BindView(R.id.tv_recordType)
    TextView tv_recordType;
    @BindView(R.id.tv_time)
    TextView tv_time;
    Unbinder unbinder;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_record_or_play;

    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        cbRecord.setOnCheckedChangeListener(recordListener);
        seekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_record, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_record://重新录制
                break;
            case R.id.tv_delete://删除
                break;
        }
    }

    private CompoundButton.OnCheckedChangeListener recordListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {//开始录制
                VoicetranscribeAndPlayUtiles.start((Activity) ctx, cbRecord, tv_time);
                tv_recordType.setText("正在录制……");
            } else {//结束录制
                outFile = VoicetranscribeAndPlayUtiles.stop();
                VoiceRecordOrPlayListener.recordListener.backFile(outFile);
                tvRecord.setText("点击开始录制");
                llPlay.setVisibility(View.VISIBLE);
                llRecord.setVisibility(View.GONE);
                startPlay();
            }
        }
    };
    private String outFile;
    private MediaPlayer mMediaPlayer;
    private int duration;//音频时长
    private int currTime;
    private void startPlay() {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(outFile);
            mMediaPlayer.prepare();
            duration = mMediaPlayer.getDuration();
            mMediaPlayer.start();
            seekbar.setMax(duration);
            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (currTime >= duration) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    seekbar.setProgress(0);
                    currTime = 0;
            }else{
                seekbar.setProgress(currTime);
                handler.postDelayed(mUpdateMicStatusTimer,100);
            }
        }
    };
    private  Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            currTime += 100;
            handler.sendEmptyMessage(0);
        }
    };


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        mMediaPlayer.seekTo(progress);
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }
}
