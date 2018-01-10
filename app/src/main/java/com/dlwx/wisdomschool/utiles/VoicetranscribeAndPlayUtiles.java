package com.dlwx.wisdomschool.utiles;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * 语音录制
 */

public class VoicetranscribeAndPlayUtiles {

    private static MediaRecorder myAudioRecorder;
    private static String outputFile = null;
    private static long time;

    public static void start(Activity ctx){
        outputFile = Content.GetVoiceFile(ctx);
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setOnErrorListener(onErrorListener);
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
        try {
            myAudioRecorder.prepare();
            time = System.currentTimeMillis();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String stop() throws IOException {
       long time1 = System.currentTimeMillis();
        if (((time1-time)/100)>1) {
            myAudioRecorder.setOnErrorListener(null);
            myAudioRecorder.setPreviewDisplay(null);
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder  = null;
        }else{
            myAudioRecorder.release();
            myAudioRecorder  = null;
            return null;
        }

    return outputFile;
    }
    public static  double durationTime(){
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(outputFile);  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double duration= player.getDuration();//获取音频的时间
        Log.d("ACETEST", "### duration: " + duration);
        player.release();//记得释放资源
        return duration;
    }


    public static void play() throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException{

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
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

}
