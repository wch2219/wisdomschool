package com.dlwx.wisdomschool.utiles;

import android.app.Activity;
import android.os.Environment;

import com.dlwx.wisdomschool.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/29/029.
 */

public class Content {


    public static String GetVoiceFile(Activity ctx){
        String voiceFile = null;
        File fpath;
        File recording;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))// 手机有SD卡的情况
        {
            // 在这里我们创建一个文件，用于保存录制内容
            fpath = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/test_record/");
            fpath.mkdirs();// 创建文件夹
        } else// 手机无SD卡的情况
        {
            //返回在文件系统上应用程序特定的缓存目录的绝对路径
            fpath = ctx.getCacheDir();
        }

        try {
            // 创建临时文件,注意这里的格式为.pcm  .amr  .mp3
           recording = File.createTempFile("recording", ".amr", fpath);
           voiceFile = recording.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return voiceFile;
    }

    /**
     * 获取表情包
     * @return
     */
    public static EaseEmojicon[] getEmojiconDatas (){
        EaseEmojicon[] data = EaseDefaultEmojiconDatas.getData();
        return data;
    }
    public static String[] emojis = new String[]{
            EaseSmileUtils.ee_1,
            EaseSmileUtils.ee_2,
            EaseSmileUtils.ee_3,
            EaseSmileUtils.ee_4,
            EaseSmileUtils.ee_5,
            EaseSmileUtils.ee_6,
            EaseSmileUtils.ee_7,
            EaseSmileUtils.ee_8,
            EaseSmileUtils.ee_9,
            EaseSmileUtils.ee_10,
            EaseSmileUtils.ee_11,
            EaseSmileUtils.ee_12,
            EaseSmileUtils.ee_13,
            EaseSmileUtils.ee_14,
            EaseSmileUtils.ee_15,
            EaseSmileUtils.ee_16,
            EaseSmileUtils.ee_17,
            EaseSmileUtils.ee_18,
            EaseSmileUtils.ee_19,
            EaseSmileUtils.ee_20,
            EaseSmileUtils.ee_21,
            EaseSmileUtils.ee_22,
            EaseSmileUtils.ee_23,
            EaseSmileUtils.ee_24,
            EaseSmileUtils.ee_25,
            EaseSmileUtils.ee_26,
            EaseSmileUtils.ee_27,
            EaseSmileUtils.ee_28,
            EaseSmileUtils.ee_29,
            EaseSmileUtils.ee_30,
            EaseSmileUtils.ee_31,
            EaseSmileUtils.ee_32,
            EaseSmileUtils.ee_33,
            EaseSmileUtils.ee_34,
            EaseSmileUtils.ee_35,

    };

    public static int[] icons = new int[]{
            R.drawable.ee_1,
            R.drawable.ee_2,
            R.drawable.ee_3,
            R.drawable.ee_4,
            R.drawable.ee_5,
            R.drawable.ee_6,
            R.drawable.ee_7,
            R.drawable.ee_8,
            R.drawable.ee_9,
            R.drawable.ee_10,
            R.drawable.ee_11,
            R.drawable.ee_12,
            R.drawable.ee_13,
            R.drawable.ee_14,
            R.drawable.ee_15,
            R.drawable.ee_16,
            R.drawable.ee_17,
            R.drawable.ee_18,
            R.drawable.ee_19,
            R.drawable.ee_20,
            R.drawable.ee_21,
            R.drawable.ee_22,
            R.drawable.ee_23,
            R.drawable.ee_24,
            R.drawable.ee_25,
            R.drawable.ee_26,
            R.drawable.ee_27,
            R.drawable.ee_28,
            R.drawable.ee_29,
            R.drawable.ee_30,
            R.drawable.ee_31,
            R.drawable.ee_32,
            R.drawable.ee_33,
            R.drawable.ee_34,
            R.drawable.ee_35,
    };
}
