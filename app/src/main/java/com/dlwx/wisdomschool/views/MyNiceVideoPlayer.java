package com.dlwx.wisdomschool.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xiao.nicevideoplayer.LogUtil;
import com.xiao.nicevideoplayer.NiceTextureView;
import com.xiao.nicevideoplayer.NiceUtil;

import java.io.IOException;
import java.util.Map;

import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MyNiceVideoPlayer extends FrameLayout implements MyINiceVideoPlayer, SurfaceTextureListener {
    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_BUFFERING_PLAYING = 5;
    public static final int STATE_BUFFERING_PAUSED = 6;
    public static final int STATE_COMPLETED = 7;
    public static final int MODE_NORMAL = 10;
    public static final int MODE_FULL_SCREEN = 11;
    public static final int MODE_TINY_WINDOW = 12;
    public static final int TYPE_IJK = 111;
    public static final int TYPE_NATIVE = 222;
    private int mPlayerType;
    private int mCurrentState;
    private int mCurrentMode;
    private Context mContext;
    private AudioManager mAudioManager;
    private IMediaPlayer mMediaPlayer;
    private FrameLayout mContainer;
    private NiceTextureView mTextureView;
    private MyNiceVideoPlayerController mController;
    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;
    private String mUrl;
    private Map<String, String> mHeaders;
    private int mBufferPercentage;
    private boolean continueFromLastPosition;
    private long skipToPosition;
    private OnPreparedListener mOnPreparedListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;

    public MyNiceVideoPlayer(Context context) {
        this(context, (AttributeSet)null);
    }

    public MyNiceVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPlayerType = 111;
        this.mCurrentState = 0;
        this.mCurrentMode = 10;
        this.continueFromLastPosition = true;
        this.mOnPreparedListener = new OnPreparedListener() {
            public void onPrepared(IMediaPlayer mp) {
                MyNiceVideoPlayer.this.mCurrentState = 2;
                MyNiceVideoPlayer.this.mController.onPlayStateChanged(MyNiceVideoPlayer.this.mCurrentState);
                LogUtil.d("onPrepared ——> STATE_PREPARED");
                mp.start();
                if(MyNiceVideoPlayer.this.continueFromLastPosition) {
                    long savedPlayPosition = NiceUtil.getSavedPlayPosition(MyNiceVideoPlayer.this.mContext, MyNiceVideoPlayer.this.mUrl);
                    mp.seekTo(savedPlayPosition);
                }

                if(MyNiceVideoPlayer.this.skipToPosition != 0L) {
                    mp.seekTo(MyNiceVideoPlayer.this.skipToPosition);
                }

            }
        };
        this.mOnVideoSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                MyNiceVideoPlayer.this.mTextureView.adaptVideoSize(width, height);
                LogUtil.d("onVideoSizeChanged ——> width：" + width + "， height：" + height);
            }
        };
        this.mOnCompletionListener = new OnCompletionListener() {
            public void onCompletion(IMediaPlayer mp) {
                MyNiceVideoPlayer.this.mCurrentState = 7;
                MyNiceVideoPlayer.this.mController.onPlayStateChanged(MyNiceVideoPlayer.this.mCurrentState);
                LogUtil.d("onCompletion ——> STATE_COMPLETED");
                MyNiceVideoPlayer.this.mContainer.setKeepScreenOn(false);
            }
        };
        this.mOnErrorListener = new OnErrorListener() {
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                if(what != -38 && what != -2147483648 && extra != -38 && extra != -2147483648) {
                    MyNiceVideoPlayer.this.mCurrentState = -1;
                    MyNiceVideoPlayer.this.mController.onPlayStateChanged(MyNiceVideoPlayer.this.mCurrentState);
                    LogUtil.d("onError ——> STATE_ERROR ———— what：" + what + ", extra: " + extra);
                }

                return true;
            }
        };
        this.mOnInfoListener = new OnInfoListener() {
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                if(what == 3) {
                    MyNiceVideoPlayer.this.mCurrentState = 3;
                    MyNiceVideoPlayer.this.mController.onPlayStateChanged(MyNiceVideoPlayer.this.mCurrentState);
                    LogUtil.d("onInfo ——> MEDIA_INFO_VIDEO_RENDERING_START：STATE_PLAYING");
                } else if(what == 701) {
                    if(MyNiceVideoPlayer.this.mCurrentState != 4 && MyNiceVideoPlayer.this.mCurrentState != 6) {
                        MyNiceVideoPlayer.this.mCurrentState = 5;
                        LogUtil.d("onInfo ——> MEDIA_INFO_BUFFERING_START：STATE_BUFFERING_PLAYING");
                    } else {
                        MyNiceVideoPlayer.this.mCurrentState = 6;
                        LogUtil.d("onInfo ——> MEDIA_INFO_BUFFERING_START：STATE_BUFFERING_PAUSED");
                    }

                    MyNiceVideoPlayer.this.mController.onPlayStateChanged(MyNiceVideoPlayer.this.mCurrentState);
                } else if(what == 702) {
                    if(MyNiceVideoPlayer.this.mCurrentState == 5) {
                        MyNiceVideoPlayer.this.mCurrentState = 3;
                        MyNiceVideoPlayer.this.mController.onPlayStateChanged(MyNiceVideoPlayer.this.mCurrentState);
                        LogUtil.d("onInfo ——> MEDIA_INFO_BUFFERING_END： STATE_PLAYING");
                    }

                    if(MyNiceVideoPlayer.this.mCurrentState == 6) {
                        MyNiceVideoPlayer.this.mCurrentState = 4;
                        MyNiceVideoPlayer.this.mController.onPlayStateChanged(MyNiceVideoPlayer.this.mCurrentState);
                        LogUtil.d("onInfo ——> MEDIA_INFO_BUFFERING_END： STATE_PAUSED");
                    }
                } else if(what == 10001) {
                    if(MyNiceVideoPlayer.this.mTextureView != null) {
                        MyNiceVideoPlayer.this.mTextureView.setRotation((float)extra);
                        LogUtil.d("视频旋转角度：" + extra);
                    }
                } else if(what == 801) {
                    LogUtil.d("视频不能seekTo，为直播视频");
                } else {
                    LogUtil.d("onInfo ——> what：" + what);
                }

                return true;
            }
        };
        this.mOnBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                MyNiceVideoPlayer.this.mBufferPercentage = percent;
            }
        };
        this.mContext = context;
        this.init();
    }

    private void init() {
        this.mContainer = new FrameLayout(this.mContext);
        this.mContainer.setBackgroundColor(-16777216);
        LayoutParams params = new LayoutParams(-1, -1);
        this.addView(this.mContainer, params);
    }

    public void setUp(String url, Map<String, String> headers) {
        this.mUrl = url;
        this.mHeaders = headers;
    }

    public void setController(MyNiceVideoPlayerController controller) {
        this.mContainer.removeView(this.mController);
        this.mController = controller;
        this.mController.reset();
        this.mController.setNiceVideoPlayer(this);
        LayoutParams params = new LayoutParams(-1, -1);
        this.mContainer.addView(this.mController, params);
    }

    public void setPlayerType(int playerType) {
        this.mPlayerType = playerType;
    }

    public void continueFromLastPosition(boolean continueFromLastPosition) {
        this.continueFromLastPosition = continueFromLastPosition;
    }

    public void setSpeed(float speed) {
        if(this.mMediaPlayer instanceof IjkMediaPlayer) {
            ((IjkMediaPlayer)this.mMediaPlayer).setSpeed(speed);
        } else {
            LogUtil.d("只有IjkPlayer才能设置播放速度");
        }

    }

    public void start() {
        if(this.mCurrentState == 0) {
            MyNiceVideoPlayerManager.instance().setCurrentNiceVideoPlayer(this);
            this.initAudioManager();
            this.initMediaPlayer();
            this.initTextureView();
            this.addTextureView();
        } else {
            LogUtil.d("NiceVideoPlayer只有在mCurrentState == STATE_IDLE时才能调用start方法.");
        }

    }

    public void start(long position) {
        this.skipToPosition = position;
        this.start();
    }

    public void restart() {
        if(this.mCurrentState == 4) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
            this.mController.onPlayStateChanged(this.mCurrentState);
            LogUtil.d("STATE_PLAYING");
        } else if(this.mCurrentState == 6) {
            this.mMediaPlayer.start();
            this.mCurrentState = 5;
            this.mController.onPlayStateChanged(this.mCurrentState);
            LogUtil.d("STATE_BUFFERING_PLAYING");
        } else if(this.mCurrentState != 7 && this.mCurrentState != -1) {
            LogUtil.d("NiceVideoPlayer在mCurrentState == " + this.mCurrentState + "时不能调用restart()方法.");
        } else {
            this.mMediaPlayer.reset();
            this.openMediaPlayer();
        }

    }

    public void pause() {
        if(this.mCurrentState == 3) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
            this.mController.onPlayStateChanged(this.mCurrentState);
            LogUtil.d("STATE_PAUSED");
        }

        if(this.mCurrentState == 5) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 6;
            this.mController.onPlayStateChanged(this.mCurrentState);
            LogUtil.d("STATE_BUFFERING_PAUSED");
        }

    }

    public void seekTo(long pos) {
        if(this.mMediaPlayer != null) {
            this.mMediaPlayer.seekTo(pos);
        }

    }

    public void setVolume(int volume) {
        if(this.mAudioManager != null) {
            this.mAudioManager.setStreamVolume(3, volume, 0);
        }

    }

    public boolean isIdle() {
        return this.mCurrentState == 0;
    }

    public boolean isPreparing() {
        return this.mCurrentState == 1;
    }

    public boolean isPrepared() {
        return this.mCurrentState == 2;
    }

    public boolean isBufferingPlaying() {
        return this.mCurrentState == 5;
    }

    public boolean isBufferingPaused() {
        return this.mCurrentState == 6;
    }

    public boolean isPlaying() {
        return this.mCurrentState == 3;
    }

    public boolean isPaused() {
        return this.mCurrentState == 4;
    }

    public boolean isError() {
        return this.mCurrentState == -1;
    }

    public boolean isCompleted() {
        return this.mCurrentState == 7;
    }

    public boolean isFullScreen() {
        return this.mCurrentMode == 11;
    }

    public boolean isTinyWindow() {
        return this.mCurrentMode == 12;
    }

    public boolean isNormal() {
        return this.mCurrentMode == 10;
    }

    public int getMaxVolume() {
        return this.mAudioManager != null?this.mAudioManager.getStreamMaxVolume(3):0;
    }

    public int getVolume() {
        return this.mAudioManager != null?this.mAudioManager.getStreamVolume(3):0;
    }

    public long getDuration() {
        return this.mMediaPlayer != null?this.mMediaPlayer.getDuration():0L;
    }

    public long getCurrentPosition() {
        return this.mMediaPlayer != null?this.mMediaPlayer.getCurrentPosition():0L;
    }

    public int getBufferPercentage() {
        return this.mBufferPercentage;
    }

    public float getSpeed(float speed) {
        return this.mMediaPlayer instanceof IjkMediaPlayer?((IjkMediaPlayer)this.mMediaPlayer).getSpeed(speed):0.0F;
    }

    public long getTcpSpeed() {
        return this.mMediaPlayer instanceof IjkMediaPlayer?((IjkMediaPlayer)this.mMediaPlayer).getTcpSpeed():0L;
    }

    private void initAudioManager() {
        if(this.mAudioManager == null) {
            this.mAudioManager = (AudioManager)this.getContext().getSystemService(Context.AUDIO_SERVICE);
            this.mAudioManager.requestAudioFocus((OnAudioFocusChangeListener)null, 3, 1);
        }

    }

    private void initMediaPlayer() {
        if(this.mMediaPlayer == null) {
            switch(this.mPlayerType) {
                case 111:
                default:
                    this.mMediaPlayer = new IjkMediaPlayer();
                    ((IjkMediaPlayer)this.mMediaPlayer).setOption(1, "analyzemaxduration", 100L);
                    ((IjkMediaPlayer)this.mMediaPlayer).setOption(1, "probesize", 10240L);
                    ((IjkMediaPlayer)this.mMediaPlayer).setOption(1, "flush_packets", 1L);
                    ((IjkMediaPlayer)this.mMediaPlayer).setOption(4, "packet-buffering", 0L);
                    ((IjkMediaPlayer)this.mMediaPlayer).setOption(4, "framedrop", 1L);
                    break;
                case 222:
                    this.mMediaPlayer = new AndroidMediaPlayer();
            }

            this.mMediaPlayer.setAudioStreamType(3);
        }

    }

    private void initTextureView() {
        if(this.mTextureView == null) {
            this.mTextureView = new NiceTextureView(this.mContext);
            this.mTextureView.setSurfaceTextureListener(this);
        }

    }

    private void addTextureView() {
        this.mContainer.removeView(this.mTextureView);
        LayoutParams params = new LayoutParams(-1, -1, 17);
        this.mContainer.addView(this.mTextureView, 0, params);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        if(this.mSurfaceTexture == null) {
            this.mSurfaceTexture = surfaceTexture;
            this.openMediaPlayer();
        } else {
            this.mTextureView.setSurfaceTexture(this.mSurfaceTexture);
        }

    }

    private void openMediaPlayer() {
        this.mContainer.setKeepScreenOn(true);
        this.mMediaPlayer.setOnPreparedListener(this.mOnPreparedListener);
        this.mMediaPlayer.setOnVideoSizeChangedListener(this.mOnVideoSizeChangedListener);
        this.mMediaPlayer.setOnCompletionListener(this.mOnCompletionListener);
        this.mMediaPlayer.setOnErrorListener(this.mOnErrorListener);
        this.mMediaPlayer.setOnInfoListener(this.mOnInfoListener);
        this.mMediaPlayer.setOnBufferingUpdateListener(this.mOnBufferingUpdateListener);

        try {
            this.mMediaPlayer.setDataSource(this.mContext.getApplicationContext(), Uri.parse(this.mUrl), this.mHeaders);
            if(this.mSurface == null) {
                this.mSurface = new Surface(this.mSurfaceTexture);
            }

            this.mMediaPlayer.setSurface(this.mSurface);
            this.mMediaPlayer.prepareAsync();
            this.mCurrentState = 1;
            this.mController.onPlayStateChanged(this.mCurrentState);
            LogUtil.d("STATE_PREPARING");
        } catch (IOException var2) {
            var2.printStackTrace();
            LogUtil.e("打开播放器发生错误", var2);
        }

    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return this.mSurfaceTexture == null;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @SuppressLint("WrongConstant")
    public void enterFullScreen() {
        if(this.mCurrentMode != 11) {
            NiceUtil.hideActionBar(this.mContext);
            NiceUtil.scanForActivity(this.mContext).setRequestedOrientation(STATE_IDLE);
            @SuppressLint("ResourceType") ViewGroup contentView = (ViewGroup)NiceUtil.scanForActivity(this.mContext).findViewById(16908290);
            if(this.mCurrentMode == 12) {
                contentView.removeView(this.mContainer);
            } else {
                this.removeView(this.mContainer);
            }

            LayoutParams params = new LayoutParams(-1, -1);
            contentView.addView(this.mContainer, params);
            this.mCurrentMode = 11;
            this.mController.onPlayModeChanged(this.mCurrentMode);
            LogUtil.d("MODE_FULL_SCREEN");
        }
    }

    @SuppressLint("WrongConstant")
    public boolean exitFullScreen() {
        if(this.mCurrentMode == 11) {
            NiceUtil.showActionBar(this.mContext);
            NiceUtil.scanForActivity(this.mContext).setRequestedOrientation(1);
            @SuppressLint("ResourceType") ViewGroup contentView = (ViewGroup)NiceUtil.scanForActivity(this.mContext).findViewById(16908290);
            contentView.removeView(this.mContainer);
            LayoutParams params = new LayoutParams(-1, -1);
            this.addView(this.mContainer, params);
            this.mCurrentMode = 10;
            this.mController.onPlayModeChanged(this.mCurrentMode);
            LogUtil.d("MODE_NORMAL");
            return true;
        } else {
            return false;
        }
    }

    public void enterTinyWindow() {
        if(this.mCurrentMode != 12) {
            this.removeView(this.mContainer);
            @SuppressLint("ResourceType") ViewGroup contentView = (ViewGroup)NiceUtil.scanForActivity(this.mContext).findViewById(16908290);
            LayoutParams params = new LayoutParams((int)((float)NiceUtil.getScreenWidth(this.mContext) * 0.6F), (int)((float)NiceUtil.getScreenWidth(this.mContext) * 0.6F * 9.0F / 16.0F));
            params.gravity = 8388693;
            params.rightMargin = NiceUtil.dp2px(this.mContext, 8.0F);
            params.bottomMargin = NiceUtil.dp2px(this.mContext, 8.0F);
            contentView.addView(this.mContainer, params);
            this.mCurrentMode = 12;
            this.mController.onPlayModeChanged(this.mCurrentMode);
            LogUtil.d("MODE_TINY_WINDOW");
        }
    }

    public boolean exitTinyWindow() {
        if(this.mCurrentMode == 12) {
            @SuppressLint("ResourceType") ViewGroup contentView = (ViewGroup)NiceUtil.scanForActivity(this.mContext).findViewById(16908290);
            contentView.removeView(this.mContainer);
            LayoutParams params = new LayoutParams(-1, -1);
            this.addView(this.mContainer, params);
            this.mCurrentMode = 10;
            this.mController.onPlayModeChanged(this.mCurrentMode);
            LogUtil.d("MODE_NORMAL");
            return true;
        } else {
            return false;
        }
    }

    public void releasePlayer() {
        if(this.mAudioManager != null) {
            this.mAudioManager.abandonAudioFocus((OnAudioFocusChangeListener)null);
            this.mAudioManager = null;
        }

        if(this.mMediaPlayer != null) {
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }

        this.mContainer.removeView(this.mTextureView);
        if(this.mSurface != null) {
            this.mSurface.release();
            this.mSurface = null;
        }

        if(this.mSurfaceTexture != null) {
            this.mSurfaceTexture.release();
            this.mSurfaceTexture = null;
        }

        this.mCurrentState = 0;
    }

    public void release() {
        if(!this.isPlaying() && !this.isBufferingPlaying() && !this.isBufferingPaused() && !this.isPaused()) {
            if(this.isCompleted()) {
                NiceUtil.savePlayPosition(this.mContext, this.mUrl, 0L);
            }
        } else {
            NiceUtil.savePlayPosition(this.mContext, this.mUrl, this.getCurrentPosition());
        }

        if(this.isFullScreen()) {
            this.exitFullScreen();
        }

        if(this.isTinyWindow()) {
            this.exitTinyWindow();
        }

        this.mCurrentMode = 10;
        this.releasePlayer();
        if(this.mController != null) {
            this.mController.reset();
        }

        Runtime.getRuntime().gc();
    }
}
