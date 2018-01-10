package com.dlwx.wisdomschool.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xiao.nicevideoplayer.NiceUtil;

import java.util.Timer;
import java.util.TimerTask;

public abstract class MyNiceVideoPlayerController extends FrameLayout implements OnTouchListener {
    private Context mContext;
    protected MyINiceVideoPlayer mNiceVideoPlayer;
    private Timer mUpdateProgressTimer;
    private TimerTask mUpdateProgressTimerTask;
    private float mDownX;
    private float mDownY;
    private boolean mNeedChangePosition;
    private boolean mNeedChangeVolume;
    private boolean mNeedChangeBrightness;
    private static final int THRESHOLD = 80;
    private long mGestureDownPosition;
    private float mGestureDownBrightness;
    private int mGestureDownVolume;
    private long mNewPosition;

    public MyNiceVideoPlayerController(Context context) {
        super(context);
        this.mContext = context;
        this.setOnTouchListener(this);
    }

    public void setNiceVideoPlayer(MyINiceVideoPlayer niceVideoPlayer) {
        this.mNiceVideoPlayer = niceVideoPlayer;
    }

    public abstract void setTitle(String var1);

    public abstract void setImage(Bitmap var1);

    public abstract ImageView imageView();

    public abstract void setLenght(long var1);

    protected abstract void onPlayStateChanged(int var1);

    protected abstract void onPlayModeChanged(int var1);

    protected abstract void reset();

    protected void startUpdateProgressTimer() {
        this.cancelUpdateProgressTimer();
        if(this.mUpdateProgressTimer == null) {
            this.mUpdateProgressTimer = new Timer();
        }

        if(this.mUpdateProgressTimerTask == null) {
            this.mUpdateProgressTimerTask = new TimerTask() {
                public void run() {
                    MyNiceVideoPlayerController.this.post(new Runnable() {
                        public void run() {
                            MyNiceVideoPlayerController.this.updateProgress();
                        }
                    });
                }
            };
        }

        this.mUpdateProgressTimer.schedule(this.mUpdateProgressTimerTask, 0L, 1000L);
    }

    protected void cancelUpdateProgressTimer() {
        if(this.mUpdateProgressTimer != null) {
            this.mUpdateProgressTimer.cancel();
            this.mUpdateProgressTimer = null;
        }

        if(this.mUpdateProgressTimerTask != null) {
            this.mUpdateProgressTimerTask.cancel();
            this.mUpdateProgressTimerTask = null;
        }

    }

    protected abstract void updateProgress();

    public boolean onTouch(View v, MotionEvent event) {
        if(!this.mNiceVideoPlayer.isFullScreen()) {
            return false;
        } else if(!this.mNiceVideoPlayer.isIdle() && !this.mNiceVideoPlayer.isError() && !this.mNiceVideoPlayer.isPreparing() && !this.mNiceVideoPlayer.isPrepared() && !this.mNiceVideoPlayer.isCompleted()) {
            float x = event.getX();
            float y = event.getY();
            switch(event.getAction()) {
                case 0:
                    this.mDownX = x;
                    this.mDownY = y;
                    this.mNeedChangePosition = false;
                    this.mNeedChangeVolume = false;
                    this.mNeedChangeBrightness = false;
                    break;
                case 1:
                case 3:
                    if(this.mNeedChangePosition) {
                        this.mNiceVideoPlayer.seekTo(this.mNewPosition);
                        this.hideChangePosition();
                        this.startUpdateProgressTimer();
                        return true;
                    }

                    if(this.mNeedChangeBrightness) {
                        this.hideChangeBrightness();
                        return true;
                    }

                    if(this.mNeedChangeVolume) {
                        this.hideChangeVolume();
                        return true;
                    }
                    break;
                case 2:
                    float deltaX = x - this.mDownX;
                    float deltaY = y - this.mDownY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    if(!this.mNeedChangePosition && !this.mNeedChangeVolume && !this.mNeedChangeBrightness) {
                        if(absDeltaX >= 80.0F) {
                            this.cancelUpdateProgressTimer();
                            this.mNeedChangePosition = true;
                            this.mGestureDownPosition = this.mNiceVideoPlayer.getCurrentPosition();
                        } else if(absDeltaY >= 80.0F) {
                            if(this.mDownX < (float)this.getWidth() * 0.5F) {
                                this.mNeedChangeBrightness = true;
                                this.mGestureDownBrightness = NiceUtil.scanForActivity(this.mContext).getWindow().getAttributes().screenBrightness;
                            } else {
                                this.mNeedChangeVolume = true;
                                this.mGestureDownVolume = this.mNiceVideoPlayer.getVolume();
                            }
                        }
                    }

                    int newBrightnessProgress;
                    if(this.mNeedChangePosition) {
                        long duration = this.mNiceVideoPlayer.getDuration();
                        long toPosition = (long)((float)this.mGestureDownPosition + (float)duration * deltaX / (float)this.getWidth());
                        this.mNewPosition = Math.max(0L, Math.min(duration, toPosition));
                        newBrightnessProgress = (int)(100.0F * (float)this.mNewPosition / (float)duration);
                        this.showChangePosition(duration, newBrightnessProgress);
                    }

                    if(this.mNeedChangeBrightness) {
                        deltaY = -deltaY;
                        float deltaBrightness = deltaY * 3.0F / (float)this.getHeight();
                        float newBrightness = this.mGestureDownBrightness + deltaBrightness;
                        newBrightness = Math.max(0.0F, Math.min(newBrightness, 1.0F));
                        WindowManager.LayoutParams params = NiceUtil.scanForActivity(this.mContext).getWindow().getAttributes();
                        params.screenBrightness = newBrightness;
                        NiceUtil.scanForActivity(this.mContext).getWindow().setAttributes(params);
                        newBrightnessProgress = (int)(100.0F * newBrightness);
                        this.showChangeBrightness(newBrightnessProgress);
                    }

                    if(this.mNeedChangeVolume) {
                        deltaY = -deltaY;
                        int maxVolume = this.mNiceVideoPlayer.getMaxVolume();
                        int deltaVolume = (int)((float)maxVolume * deltaY * 3.0F / (float)this.getHeight());
                        int newVolume = this.mGestureDownVolume + deltaVolume;
                        newVolume = Math.max(0, Math.min(maxVolume, newVolume));
                        this.mNiceVideoPlayer.setVolume(newVolume);
                        int newVolumeProgress = (int)(100.0F * (float)newVolume / (float)maxVolume);
                        this.showChangeVolume(newVolumeProgress);
                    }
            }

            return false;
        } else {
            this.hideChangePosition();
            this.hideChangeBrightness();
            this.hideChangeVolume();
            return false;
        }
    }

    protected abstract void showChangePosition(long var1, int var3);

    protected abstract void hideChangePosition();

    protected abstract void showChangeVolume(int var1);

    protected abstract void hideChangeVolume();

    protected abstract void showChangeBrightness(int var1);

    protected abstract void hideChangeBrightness();
}
