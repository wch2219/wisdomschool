package com.dlwx.wisdomschool.interfac;

/**
 * 语音录制和播放
 */

public class VoiceRecordOrPlayListener {
    /**
     * 语音录制
     */
    public static RecordListener recordListener;
        public interface RecordListener{
            void backFile(String file);
        }
    public static void setRecordListener(RecordListener recordListener) {
        VoiceRecordOrPlayListener.recordListener = recordListener;
    }

    /**
     * 录音播放显示隐藏
     */
    public static VisibilityListener visibilityLis;
    public interface VisibilityListener{

            void isVisibi(int i);
    }

    public static void setVisibilityListener(VisibilityListener visibilityListener) {
       visibilityLis = visibilityListener;
    }
    /**
     * 清除或重新录制
     */
    public interface CleanOrAnewRecordListener{

        void clean();
    }
    public static CleanOrAnewRecordListener anewRecordListener;

    public static void setAnewRecordListener(CleanOrAnewRecordListener anewRecordListener) {
        VoiceRecordOrPlayListener.anewRecordListener = anewRecordListener;
    }
}
