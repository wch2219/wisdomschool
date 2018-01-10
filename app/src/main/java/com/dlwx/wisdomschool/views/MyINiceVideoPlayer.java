package com.dlwx.wisdomschool.views;

import java.util.Map;

public interface MyINiceVideoPlayer {
    void setUp(String var1, Map<String, String> var2);

    void start();

    void start(long var1);

    void restart();

    void pause();

    void seekTo(long var1);

    void setVolume(int var1);

    void setSpeed(float var1);

    void continueFromLastPosition(boolean var1);

    boolean isIdle();

    boolean isPreparing();

    boolean isPrepared();

    boolean isBufferingPlaying();

    boolean isBufferingPaused();

    boolean isPlaying();

    boolean isPaused();

    boolean isError();

    boolean isCompleted();

    boolean isFullScreen();

    boolean isTinyWindow();

    boolean isNormal();

    int getMaxVolume();

    int getVolume();

    long getDuration();

    long getCurrentPosition();

    int getBufferPercentage();

    float getSpeed(float var1);

    long getTcpSpeed();

    void enterFullScreen();

    boolean exitFullScreen();

    void enterTinyWindow();

    boolean exitTinyWindow();

    void releasePlayer();

    void release();
}
