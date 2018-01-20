package com.dlwx.wisdomschool.utiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.wisdomschool.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 语音录制
 */

public class VoicetranscribeAndPlayUtiles {

    private static MediaRecorder myAudioRecorder;
    private static String outputFile = null;
    private static long time;
    private static View mView;
    private static TextView tvTime;
    private static double db;
    private static long startTime;

    public static void start(Activity ctx, View view, TextView tv_time) {
        mView = view;
        tvTime = tv_time;
        outputFile = Content.GetVoiceFile(ctx);
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setOnErrorListener(onErrorListener);
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.MPEG_4);
        myAudioRecorder.setOutputFile(outputFile);
        try {
            myAudioRecorder.prepare();
            time = System.currentTimeMillis();
            myAudioRecorder.start();
            updateMicStatus();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String stop() {
        long time1 = System.currentTimeMillis();
        if (((time1 - time) / 1000) > 1) {
            myAudioRecorder.setOnErrorListener(null);
            myAudioRecorder.setPreviewDisplay(null);
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
            mView.setBackgroundResource(pics[1]);
            tvTime.setText("00:00");
        } else {
            if (myAudioRecorder != null) {
                myAudioRecorder.stop();
                mView.setBackgroundResource(pics[1]);
                myAudioRecorder.release();
                myAudioRecorder = null;
                tvTime.setText("00:00");
                return null;
            }
        }

        return outputFile;
    }


    public static String durationTime(String path) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(path);  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double duration = player.getDuration();//获取音频的时间
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        String time = format.format(new Date((long) duration));
        Log.d("ACETEST", "### duration: " + duration);
        player.release();//记得释放资源
        return time;
    }


    private static MediaRecorder.OnErrorListener onErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int i, int i1) {
            try {
                if (mr != null)
                    mr.reset();
            } catch (IllegalStateException e) {
                Log.w("Yixia", "stopRecord", e);
            } catch (Exception e) {
                Log.w("Yixia", "stopRecord", e);
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int v = (int) (db / 10);
                    LogUtiles.LogI(v + "分贝、10");
                    mView.setBackgroundResource(pics[v]);
                    startTime += 100;
                    if (startTime >= 300000) {
                        stop();
                    } else {
                        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                        String time = format.format(new Date(startTime));
                        tvTime.setText(time);
                    }
                    break;
                default:
                    tvTime.setText("00:00");
                    mView.setBackgroundResource(pics[1]);
                    break;
            }

        }
    };
    private static int[] pics = {R.drawable.ease_record_animate_01, R.drawable.ease_record_animate_02,
            R.drawable.ease_record_animate_03, R.drawable.ease_record_animate_04,
            R.drawable.ease_record_animate_05, R.drawable.ease_record_animate_06,
            R.drawable.ease_record_animate_07, R.drawable.ease_record_animate_08,
            R.drawable.ease_record_animate_09, R.drawable.ease_record_animate_10,
            R.drawable.ease_record_animate_11, R.drawable.ease_record_animate_12,
            R.drawable.ease_record_animate_13, R.drawable.ease_record_animate_14,
    };
    private static Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
            if (myAudioRecorder != null) {

                mHandler.sendEmptyMessage(0);
            }else{
                mHandler.sendEmptyMessage(1);
            }
        }
    };

    /**
     * 更新话筒状态
     */
    private static int BASE = 1;
    private static int SPACE = 100;// 间隔取样时间

    private static void updateMicStatus() {
        if (myAudioRecorder != null) {
            double ratio = (double) myAudioRecorder.getMaxAmplitude() / BASE;
            // 分贝
            db = 0;
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            LogUtiles.LogI("分贝值：" + db);
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);

        }
    }

    private static MediaPlayer mMediaPlayer;
    private static AnimationDrawable drawable;

    public static void play(final ImageView view, String path) {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();

            }
        }
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            drawable = (AnimationDrawable) view.getDrawable();
            drawable.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    drawable.stop();
                    mediaPlayer.release();
                    mMediaPlayer = null;
                    view.setImageResource(R.mipmap.icon_syyuyin);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopPlay() {
        if (mMediaPlayer == null) {

            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();

        }
        mMediaPlayer.release();

    }
}
