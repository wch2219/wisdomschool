package com.dlwx.wisdomschool.utiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.wisdomschool.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 音频播放
 */

public class MediaPlayUtils implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mediaPlayer;
    private int duration;
    private Activity ctx;
    private ViewHolder vh;
    private PopupWindow popupWindow;
    private List<Image> mp3s;
    private int currItem;//当前播放的

    public MediaPlayUtils(Context ctx) {
        this.ctx = (Activity) ctx;
    }

    public void showPopu(View parent, List<Image> mp3s, int oldpos) {
        this.mp3s = mp3s;
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_vociaplay, null);
        vh = new ViewHolder(view);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ColorDrawable colorDrawable = new ColorDrawable(0x99000000);
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //窗体关闭后停止播放
                //停止
                mediaPlayer.stop();
                //释放资源
                mediaPlayer.release();
            }
        });
        vh.rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float y = motionEvent.getY();
                float y1 = vh.ll_popu.getY();
                if (y < y1) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });

        popupWindow.setAnimationStyle(R.style.AnimationPopuAp);
        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.enter_anim);
        vh.ll_popu.startAnimation(animation);

        Animation animation1 = AnimationUtils.loadAnimation(ctx, R.anim.enter_anim_popu);
        vh.rl_rootview.startAnimation(animation);

        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        init(vh);
        for (int i = 0; i < mp3s.size(); i++) {
            Image image = mp3s.get(i);
            if (image.getOldposition() == oldpos) {
                vh.tv_filename.setText(mp3s.get(i).getName());

                currItem = i;
                staPlay(mp3s.get(i).getPath());
            }
        }

    }

    private void init(ViewHolder vh) {
        mediaPlayer = new MediaPlayer();
        vh.iv_shang.setOnClickListener(this);
        vh.iv_xia.setOnClickListener(this);
        vh.tv_close.setOnClickListener(this);
        vh.cb_play.setOnCheckedChangeListener(playcheckListener);
        vh.cb_continuous.setOnCheckedChangeListener(lianxucheckListener);
        vh.seekbar.setOnSeekBarChangeListener(this);
    }


    private void staPlay(String path) {
        try {
            mediaPlayer.reset();
            curccduration = 0;
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();//准备
            duration = mediaPlayer.getDuration();
            vh.seekbar.setMax(duration);
            vh.seekbar.setProgress(0);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    boolean checked = vh.cb_continuous.isChecked();
                    curccduration = 0;
                    vh.seekbar.setProgress(0);
                    if (checked) {//连续播放
                        if (currItem == mp3s.size() - 1) {
                            Toast.makeText(ctx, "没有下一曲了", Toast.LENGTH_SHORT).show();
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            vh.cb_play.setChecked(false);
                            return;
                        }
                        currItem++;
                        vh.tv_filename.setText(mp3s.get(currItem).getName());
                        staPlay(mp3s.get(currItem).getPath());
                    }else{
                        vh.seekbar.setProgress(0);
                        mediaPlayer.seekTo(0);
                        vh.cb_play.setChecked(false);
                    }
                }
            });
            mediaPlayer.start();
           vh.cb_play.setChecked(true);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    StartProgress();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void StartProgress(){
        //开辟新的Thread用于定期刷新SeekBar;
        DelayThread dThread = new DelayThread(100);
        dThread.start();
    }
    //开启一个线程进行实时刷新
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (popupWindow.isShowing() ) {

                curccduration = mediaPlayer.getCurrentPosition();
                vh.seekbar.setProgress(curccduration);
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                String curccTime = format.format(new Date(curccduration));
                vh.tv_curcctime.setText(curccTime);
                String durationTime = format.format(new Date(duration));
                vh.tv_totaltime.setText(durationTime);
            }
        }
    };
    public class DelayThread extends Thread{
        int milliseconds;
        public DelayThread(int i){
            milliseconds=i;
        }
        public void run(){
            while(true){
                try {
                    sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }

    private int curccduration;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shang://上一曲
                if (currItem == 0) {
                    Toast.makeText(ctx, "没有上一曲了", Toast.LENGTH_SHORT).show();
                    return;
                }
                currItem--;
                vh.tv_filename.setText(mp3s.get(currItem).getName());
                staPlay(mp3s.get(currItem).getPath());
                break;
            case R.id.iv_xia://下一曲
                if (currItem == mp3s.size() - 1) {
                    Toast.makeText(ctx, "没有下一曲了", Toast.LENGTH_SHORT).show();
                    return;
                }
                currItem++;
                vh.tv_filename.setText(mp3s.get(currItem).getName());
                staPlay(mp3s.get(currItem).getPath());
                break;
            case R.id.tv_close:
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 播放按钮
     */
    private CompoundButton.OnCheckedChangeListener playcheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();

                }
                startrotate(vh.cb_play);
            } else {
                if (mediaPlayer == null) {
                    return;

                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                stoprotate(vh.cb_play);
            }
        }
    };

    /**
     * 是否连续播放按钮
     */
    private CompoundButton.OnCheckedChangeListener lianxucheckListener = new CompoundButton.OnCheckedChangeListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                Toast toast = Toast.makeText(ctx, "连续播放已经打开", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast toast = Toast.makeText(ctx, "连续播放已经关闭", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        LogUtiles.LogI("当前进度：" + i);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        mediaPlayer.seekTo(progress);
    }

    /**
     * 开始旋转
     *
     * @param view
     */
    private void startrotate(View view) {
        Animation rotate = AnimationUtils.loadAnimation(ctx, R.anim.play_rotate);
        view.startAnimation(rotate);

    }

    /**
     * 停止旋转
     *
     * @param view
     */
    private void stoprotate(View view) {
        view.clearAnimation();
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_filename;
        public SeekBar seekbar;
        public ImageView iv_shang;
        public CheckBox cb_play;
        public ImageView iv_xia;
        public CheckBox cb_continuous;
        public TextView tv_close;
        public LinearLayout ll_popu;
        public TextView tv_curcctime;
        public TextView tv_totaltime;
        public RelativeLayout rl_rootview;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_filename = (TextView) rootView.findViewById(R.id.tv_filename);
            this.seekbar = (SeekBar) rootView.findViewById(R.id.seekbar);
            this.iv_shang = (ImageView) rootView.findViewById(R.id.iv_shang);
            this.cb_play = (CheckBox) rootView.findViewById(R.id.cb_play);
            this.iv_xia = (ImageView) rootView.findViewById(R.id.iv_xia);
            this.cb_continuous = (CheckBox) rootView.findViewById(R.id.cb_continuous);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_curcctime = (TextView) rootView.findViewById(R.id.tv_curcctime);
            this.tv_totaltime = (TextView) rootView.findViewById(R.id.tv_totaltime);
            this.ll_popu = rootView.findViewById(R.id.ll_popu);
            this.rl_rootview = rootView.findViewById(R.id.rl_rootview);
        }

    }
}
