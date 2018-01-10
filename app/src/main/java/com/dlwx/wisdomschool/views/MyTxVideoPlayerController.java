package com.dlwx.wisdomschool.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.wisdomschool.R;
import com.xiao.nicevideoplayer.ChangeClarityDialog;
import com.xiao.nicevideoplayer.ChangeClarityDialog.OnClarityChangedListener;
import com.xiao.nicevideoplayer.Clarity;
import com.xiao.nicevideoplayer.NiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.R.drawable;
import com.xiao.nicevideoplayer.R.id;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyTxVideoPlayerController extends MyNiceVideoPlayerController implements OnClickListener, OnSeekBarChangeListener, OnClarityChangedListener {
    private Context mContext;
    private ImageView mImage;
    private ImageView mCenterStart;
    private LinearLayout mTop;
    private ImageView mBack;
    private TextView mTitle;
    private LinearLayout mBatteryTime;
    private ImageView mBattery;
    private TextView mTime;
    private LinearLayout mBottom;
    private ImageView mRestartPause;
    private TextView mPosition;
    private TextView mDuration;
    private SeekBar mSeek;
    private TextView mClarity;
    private ImageView mFullScreen;
    private TextView mLength;
    private LinearLayout mLoading;
    private TextView mLoadText;
    private LinearLayout mChangePositon;
    private TextView mChangePositionCurrent;
    private ProgressBar mChangePositionProgress;
    private LinearLayout mChangeBrightness;
    private ProgressBar mChangeBrightnessProgress;
    private LinearLayout mChangeVolume;
    private ProgressBar mChangeVolumeProgress;
    private LinearLayout mError;
    private TextView mRetry;
    private LinearLayout mCompleted;
    private TextView mReplay;
    private TextView mShare;
    private boolean topBottomVisible;
    private CountDownTimer mDismissTopBottomCountDownTimer;
    private List<Clarity> clarities;
    private int defaultClarityIndex;
    private ChangeClarityDialog mClarityDialog;
    private boolean hasRegisterBatteryReceiver;
    private BroadcastReceiver mBatterReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra("status", 1);
            if(status == 2) {
                MyTxVideoPlayerController.this.mBattery.setImageResource(drawable.battery_charging);
            } else if(status == 5) {
                MyTxVideoPlayerController.this.mBattery.setImageResource(drawable.battery_full);
            } else {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 0);
                int percentage = (int)((float)level / (float)scale * 100.0F);
                if(percentage <= 10) {
                    MyTxVideoPlayerController.this.mBattery.setImageResource(drawable.battery_10);
                } else if(percentage <= 20) {
                    MyTxVideoPlayerController.this.mBattery.setImageResource(drawable.battery_20);
                } else if(percentage <= 50) {
                    MyTxVideoPlayerController.this.mBattery.setImageResource(drawable.battery_50);
                } else if(percentage <= 80) {
                    MyTxVideoPlayerController.this.mBattery.setImageResource(drawable.battery_80);
                } else if(percentage <= 100) {
                    MyTxVideoPlayerController.this.mBattery.setImageResource(drawable.battery_100);
                }
            }

        }
    };

    public MyTxVideoPlayerController(Context context) {
        super(context);
        this.mContext = context;
        this.init();
    }

    private void init() {
        LayoutInflater.from(this.mContext).inflate(R.layout.activity_video, this, true);
        this.mCenterStart = (ImageView)this.findViewById(id.center_start);
        this.mImage = (ImageView)this.findViewById(id.image);
        this.mTop = (LinearLayout)this.findViewById(id.top);
        this.mBack = (ImageView)this.findViewById(id.back);
        this.mTitle = (TextView)this.findViewById(id.title);
        this.mBatteryTime = (LinearLayout)this.findViewById(id.battery_time);
        this.mBattery = (ImageView)this.findViewById(id.battery);
        this.mTime = (TextView)this.findViewById(id.time);
        this.mBottom = (LinearLayout)this.findViewById(id.bottom);
        this.mRestartPause = (ImageView)this.findViewById(id.restart_or_pause);
        this.mPosition = (TextView)this.findViewById(id.position);
        this.mDuration = (TextView)this.findViewById(id.duration);
        this.mSeek = (SeekBar)this.findViewById(id.seek);
        this.mFullScreen = (ImageView)this.findViewById(id.full_screen);
        this.mClarity = (TextView)this.findViewById(id.clarity);
        this.mLength = (TextView)this.findViewById(id.length);
        this.mLoading = (LinearLayout)this.findViewById(id.loading);
        this.mLoadText = (TextView)this.findViewById(id.load_text);
        this.mChangePositon = (LinearLayout)this.findViewById(id.change_position);
        this.mChangePositionCurrent = (TextView)this.findViewById(id.change_position_current);
        this.mChangePositionProgress = (ProgressBar)this.findViewById(id.change_position_progress);
        this.mChangeBrightness = (LinearLayout)this.findViewById(id.change_brightness);
        this.mChangeBrightnessProgress = (ProgressBar)this.findViewById(id.change_brightness_progress);
        this.mChangeVolume = (LinearLayout)this.findViewById(id.change_volume);
        this.mChangeVolumeProgress = (ProgressBar)this.findViewById(id.change_volume_progress);
        this.mError = (LinearLayout)this.findViewById(id.error);
        this.mRetry = (TextView)this.findViewById(id.retry);
        this.mCompleted = (LinearLayout)this.findViewById(id.completed);
        this.mReplay = (TextView)this.findViewById(id.replay);
        this.mShare = (TextView)this.findViewById(id.share);

        this.mCenterStart.setOnClickListener(this);
        this.mBack.setOnClickListener(this);
        this.mRestartPause.setOnClickListener(this);
        this.mFullScreen.setOnClickListener(this);
        this.mClarity.setOnClickListener(this);
        this.mRetry.setOnClickListener(this);
        this.mReplay.setOnClickListener(this);
        this.mShare.setOnClickListener(this);
        this.mSeek.setOnSeekBarChangeListener(this);
        this.setOnClickListener(this);
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

    public ImageView imageView() {
        return this.mImage;
    }

    public void setImage(Bitmap bitmap) {
        this.mImage.setImageBitmap(bitmap);
    }

    public void setLenght(long length) {
        this.mLength.setText(NiceUtil.formatTime(length));
    }

    public void setNiceVideoPlayer(MyNiceVideoPlayer niceVideoPlayer) {
        super.setNiceVideoPlayer(niceVideoPlayer);
        if(this.clarities != null && this.clarities.size() > 1) {
            this.mNiceVideoPlayer.setUp(((Clarity)this.clarities.get(this.defaultClarityIndex)).videoUrl, (Map)null);
        }

    }

    public void setClarity(List<Clarity> clarities, int defaultClarityIndex) {
        if(clarities != null && clarities.size() > 1) {
            this.clarities = clarities;
            this.defaultClarityIndex = defaultClarityIndex;
            List<String> clarityGrades = new ArrayList();
            Iterator var4 = clarities.iterator();

            while(var4.hasNext()) {
                Clarity clarity = (Clarity)var4.next();
                clarityGrades.add(clarity.grade + " " + clarity.p);
            }

            this.mClarity.setText(((Clarity)clarities.get(defaultClarityIndex)).grade);
            this.mClarityDialog = new ChangeClarityDialog(this.mContext);
            this.mClarityDialog.setClarityGrade(clarityGrades, defaultClarityIndex);
            this.mClarityDialog.setOnClarityCheckedListener(this);
            if(this.mNiceVideoPlayer != null) {
                this.mNiceVideoPlayer.setUp(((Clarity)clarities.get(defaultClarityIndex)).videoUrl, (Map)null);
            }
        }

    }

    protected void onPlayStateChanged(int playState) {
        switch(playState) {
            case -1:
                this.cancelUpdateProgressTimer();
                this.setTopBottomVisible(false);
                this.mTop.setVisibility(VISIBLE);
                this.mError.setVisibility(VISIBLE);
            case 0:
            default:
                break;
            case 1:
                this.mImage.setVisibility(GONE);
                this.mLoading.setVisibility(VISIBLE);
                this.mLoadText.setText("正在准备...");
                this.mError.setVisibility(GONE);
                this.mCompleted.setVisibility(GONE);
                this.mTop.setVisibility(GONE);
                this.mBottom.setVisibility(GONE);
                this.mCenterStart.setVisibility(GONE);
                this.mLength.setVisibility(GONE);
                break;
            case 2:
                this.startUpdateProgressTimer();
                break;
            case 3:
                this.mLoading.setVisibility(GONE);
                this.mRestartPause.setImageResource(drawable.ic_player_pause);
                this.startDismissTopBottomTimer();
                break;
            case 4:
                this.mLoading.setVisibility(GONE);
                this.mRestartPause.setImageResource(drawable.ic_player_start);
                this.cancelDismissTopBottomTimer();
                break;
            case 5:
                this.mLoading.setVisibility(VISIBLE);
                this.mRestartPause.setImageResource(drawable.ic_player_pause);
                this.mLoadText.setText("正在缓冲...");
                this.startDismissTopBottomTimer();
                break;
            case 6:
                this.mLoading.setVisibility(VISIBLE);
                this.mRestartPause.setImageResource(drawable.ic_player_start);
                this.mLoadText.setText("正在缓冲...");
                this.cancelDismissTopBottomTimer();
                break;
            case 7:
                this.cancelUpdateProgressTimer();
                this.setTopBottomVisible(false);
                this.mImage.setVisibility(VISIBLE);
                this.mCompleted.setVisibility(VISIBLE);
        }

    }

    protected void onPlayModeChanged(int playMode) {
        switch(playMode) {
            case 10:
                this.mBack.setVisibility(GONE);
                this.mFullScreen.setImageResource(drawable.ic_player_enlarge);
                this.mFullScreen.setVisibility(VISIBLE);
                this.mClarity.setVisibility(GONE);
                this.mBatteryTime.setVisibility(GONE);
                if(this.hasRegisterBatteryReceiver) {
                    this.mContext.unregisterReceiver(this.mBatterReceiver);
                    this.hasRegisterBatteryReceiver = false;
                }
                break;
            case 11:
                this.mBack.setVisibility(VISIBLE);
                this.mFullScreen.setVisibility(GONE);
                this.mFullScreen.setImageResource(drawable.ic_player_shrink);
                if(this.clarities != null && this.clarities.size() > 1) {
                    this.mClarity.setVisibility(VISIBLE);
                }

                this.mBatteryTime.setVisibility(VISIBLE);
                if(!this.hasRegisterBatteryReceiver) {
                    this.mContext.registerReceiver(this.mBatterReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                    this.hasRegisterBatteryReceiver = true;
                }
                break;
            case 12:
                this.mBack.setVisibility(VISIBLE);
                this.mClarity.setVisibility(GONE);
        }

    }

    protected void reset() {
        this.topBottomVisible = false;
        this.cancelUpdateProgressTimer();
        this.cancelDismissTopBottomTimer();
        this.mSeek.setProgress(0);
        this.mSeek.setSecondaryProgress(0);
        this.mCenterStart.setVisibility(VISIBLE);
        this.mImage.setVisibility(VISIBLE);
        this.mBottom.setVisibility(GONE);
        this.mFullScreen.setImageResource(drawable.ic_player_enlarge);
        this.mLength.setVisibility(VISIBLE);
        this.mTop.setVisibility(VISIBLE);
        this.mBack.setVisibility(VISIBLE);
        this.mLoading.setVisibility(GONE);
        this.mError.setVisibility(GONE);
        this.mCompleted.setVisibility(GONE);
    }

    public void onClick(View v) {
        if(v == this.mCenterStart) {
            if(this.mNiceVideoPlayer.isIdle()) {
                this.mNiceVideoPlayer.start();
            }
        } else if(v == this.mBack) {
            if(this.mNiceVideoPlayer.isFullScreen()) {
                this.mNiceVideoPlayer.exitFullScreen();
            } else if(this.mNiceVideoPlayer.isTinyWindow()) {
                this.mNiceVideoPlayer.exitTinyWindow();
            }
        } else if(v == this.mRestartPause) {
            if(!this.mNiceVideoPlayer.isPlaying() && !this.mNiceVideoPlayer.isBufferingPlaying()) {
                if(this.mNiceVideoPlayer.isPaused() || this.mNiceVideoPlayer.isBufferingPaused()) {
                    this.mNiceVideoPlayer.restart();
                }
            } else {
                this.mNiceVideoPlayer.pause();
            }
        } else if(v == this.mFullScreen) {
            if(!this.mNiceVideoPlayer.isNormal() && !this.mNiceVideoPlayer.isTinyWindow()) {
                if(this.mNiceVideoPlayer.isFullScreen()) {
                    this.mNiceVideoPlayer.exitFullScreen();
                }
            } else {
                this.mNiceVideoPlayer.enterFullScreen();
            }
        } else if(v == this.mClarity) {
            this.setTopBottomVisible(false);
            this.mClarityDialog.show();
        } else if(v == this.mRetry) {
            this.mNiceVideoPlayer.restart();
        } else if(v == this.mReplay) {
            this.mRetry.performClick();
        } else if(v == this.mShare) {
           closeInterface.close();
        }else if(v == this.mBack){
            Toast.makeText(mContext, "返回", Toast.LENGTH_SHORT).show();
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            closeInterface.close();
        } else if(v == this && (this.mNiceVideoPlayer.isPlaying() || this.mNiceVideoPlayer.isPaused() || this.mNiceVideoPlayer.isBufferingPlaying() || this.mNiceVideoPlayer.isBufferingPaused())) {
            this.setTopBottomVisible(!this.topBottomVisible);
        }

    }

    public void onClarityChanged(int clarityIndex) {
        Clarity clarity = (Clarity)this.clarities.get(clarityIndex);
        this.mClarity.setText(clarity.grade);
        long currentPosition = this.mNiceVideoPlayer.getCurrentPosition();
        this.mNiceVideoPlayer.releasePlayer();
        this.mNiceVideoPlayer.setUp(clarity.videoUrl, (Map)null);
        this.mNiceVideoPlayer.start(currentPosition);
    }

    public void onClarityNotChanged() {
        this.setTopBottomVisible(true);
    }

    private void setTopBottomVisible(boolean visible) {
        this.mTop.setVisibility(visible?VISIBLE:GONE);
        this.mBottom.setVisibility(visible?VISIBLE:GONE);
        this.topBottomVisible = visible;
        if(visible) {
            if(!this.mNiceVideoPlayer.isPaused() && !this.mNiceVideoPlayer.isBufferingPaused()) {
                this.startDismissTopBottomTimer();
            }
        } else {
            this.cancelDismissTopBottomTimer();
        }

    }

    private void startDismissTopBottomTimer() {
        this.cancelDismissTopBottomTimer();
        if(this.mDismissTopBottomCountDownTimer == null) {
            this.mDismissTopBottomCountDownTimer = new CountDownTimer(8000L, 8000L) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    MyTxVideoPlayerController.this.setTopBottomVisible(false);
                }
            };
        }

        this.mDismissTopBottomCountDownTimer.start();
    }

    private void cancelDismissTopBottomTimer() {
        if(this.mDismissTopBottomCountDownTimer != null) {
            this.mDismissTopBottomCountDownTimer.cancel();
        }

    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if(this.mNiceVideoPlayer.isBufferingPaused() || this.mNiceVideoPlayer.isPaused()) {
            this.mNiceVideoPlayer.restart();
        }

        long position = (long)((float)(this.mNiceVideoPlayer.getDuration() * (long)seekBar.getProgress()) / 100.0F);
        this.mNiceVideoPlayer.seekTo(position);
        this.startDismissTopBottomTimer();
    }

    protected void updateProgress() {
        long position = this.mNiceVideoPlayer.getCurrentPosition();
        long duration = this.mNiceVideoPlayer.getDuration();
        int bufferPercentage = this.mNiceVideoPlayer.getBufferPercentage();
        this.mSeek.setSecondaryProgress(bufferPercentage);
        int progress = (int)(100.0F * (float)position / (float)duration);
        this.mSeek.setProgress(progress);
        this.mPosition.setText(NiceUtil.formatTime(position));
        this.mDuration.setText(NiceUtil.formatTime(duration));
        this.mTime.setText((new SimpleDateFormat("HH:mm", Locale.CHINA)).format(new Date()));
    }

    protected void showChangePosition(long duration, int newPositionProgress) {
        this.mChangePositon.setVisibility(VISIBLE);
        long newPosition = (long)((float)(duration * (long)newPositionProgress) / 100.0F);
        this.mChangePositionCurrent.setText(NiceUtil.formatTime(newPosition));
        this.mChangePositionProgress.setProgress(newPositionProgress);
        this.mSeek.setProgress(newPositionProgress);
        this.mPosition.setText(NiceUtil.formatTime(newPosition));
    }

    protected void hideChangePosition() {
        this.mChangePositon.setVisibility(GONE);
    }

    protected void showChangeVolume(int newVolumeProgress) {
        this.mChangeVolume.setVisibility(VISIBLE);
        this.mChangeVolumeProgress.setProgress(newVolumeProgress);
    }

    protected void hideChangeVolume() {
        this.mChangeVolume.setVisibility(GONE);
    }

    protected void showChangeBrightness(int newBrightnessProgress) {
        this.mChangeBrightness.setVisibility(VISIBLE);
        this.mChangeBrightnessProgress.setProgress(newBrightnessProgress);
    }

    protected void hideChangeBrightness() {
        this.mChangeBrightness.setVisibility(GONE);
    }

    private CloseInterface closeInterface;
    public interface CloseInterface{
        void close();
    }

    public void setClose(CloseInterface closeInterface){
        this.closeInterface = closeInterface;
    }
}
