package com.dlwx.wisdomschool.views;

public class MyNiceVideoPlayerManager {
    private MyNiceVideoPlayer mVideoPlayer;
    private static MyNiceVideoPlayerManager sInstance;

    private MyNiceVideoPlayerManager() {
    }

    public static synchronized MyNiceVideoPlayerManager instance() {
        if(sInstance == null) {
            sInstance = new MyNiceVideoPlayerManager();
        }

        return sInstance;
    }

    public MyNiceVideoPlayer getCurrentNiceVideoPlayer() {
        return this.mVideoPlayer;
    }

    public void setCurrentNiceVideoPlayer(MyNiceVideoPlayer videoPlayer) {
        if(this.mVideoPlayer != videoPlayer) {
            this.releaseNiceVideoPlayer();
            this.mVideoPlayer = videoPlayer;
        }

    }

    public void suspendNiceVideoPlayer() {
        if(this.mVideoPlayer != null && (this.mVideoPlayer.isPlaying() || this.mVideoPlayer.isBufferingPlaying())) {
            this.mVideoPlayer.pause();
        }

    }

    public void resumeNiceVideoPlayer() {
        if(this.mVideoPlayer != null && (this.mVideoPlayer.isPaused() || this.mVideoPlayer.isBufferingPaused())) {
            this.mVideoPlayer.restart();
        }

    }

    public void releaseNiceVideoPlayer() {
        if(this.mVideoPlayer != null) {
            this.mVideoPlayer.release();
            this.mVideoPlayer = null;
        }

    }

    public boolean onBackPressd() {
        if(this.mVideoPlayer != null) {
            if(this.mVideoPlayer.isFullScreen()) {
                return this.mVideoPlayer.exitFullScreen();
            }

            if(this.mVideoPlayer.isTinyWindow()) {
                return this.mVideoPlayer.exitTinyWindow();
            }
        }

        return false;
    }
}