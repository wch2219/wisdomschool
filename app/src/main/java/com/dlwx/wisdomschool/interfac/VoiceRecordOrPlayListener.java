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
}
