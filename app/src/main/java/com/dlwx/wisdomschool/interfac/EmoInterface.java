package com.dlwx.wisdomschool.interfac;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/12/30/030.
 */

public class EmoInterface {
        public interface  CheckEmoInterface{
            void backEmo(Bitmap bitmap,int i);
        }
        public static CheckEmoInterface checkEmoInterface;
        public static void setCheckEmoInterface(CheckEmoInterface checkEmoInter){
            checkEmoInterface = checkEmoInter;
        }
}
