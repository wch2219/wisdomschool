package com.dlwx.wisdomschool.utiles;

/**
 * Created by Administrator on 2018/1/17/017.
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * 滑动式取消加载图片
 */
public class SlideCancelLoadPic {

    public static RequestManager loadPic(Context ctx){
        RequestManager with = Glide.with(ctx);
        if (ispause) {//正在滑动取消加载
            with.pauseRequests();
        }else{//停止滑动继续加载
            with.resumeRequests();
        }
        return with;
    }
    private static boolean ispause;
    public interface PauseRequestsListener{
        void isPause(boolean b);
    }
    public static PauseRequestsListener pauseRequestsListener;

    public static void setPauseRequestsListener(PauseRequestsListener pauseRequestsListener) {
        SlideCancelLoadPic.pauseRequestsListener = pauseRequestsListener;
    }
    public static void startListener(){
        setPauseRequestsListener(new PauseRequestsListener() {
            @Override
            public void isPause(boolean b) {
                ispause = b;
            }
        });
    }

}
