package com.dlwx.wisdomschool.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.baselib.utiles.SpUtiles;
import com.dlwx.wisdomschool.utiles.ResouseString;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.TbsDownloader;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * Created by Administrator on 2017/12/12/012.
 */

public class MyApplication extends Application {
    public static String classnames;
    private static MyApplication instance;
    public static EaseUI easeUI;
    public static String Token;
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        TbsDownloader.needDownload(getApplicationContext(), false);
        instance = this;
        classnames = ResouseString.classnames;
        easeUIInit();
        SharedPreferences sp = getSharedPreferences(SpUtiles.SP_Mode,MODE_PRIVATE);
       Token = sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Token, "");
        Bugly.init(getApplicationContext(), "567efebd79", false);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    /**
     * 环信初始化
     */
    private void easeUIInit() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);

        //初始化
        EMClient.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        easeUI = EaseUI.getInstance();
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        LogUtiles.LogI("显示帐号已经被移除");
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        LogUtiles.LogI("显示帐号在其他设备登录");
                    } else {
                        if (NetUtils.hasNetwork(getApplicationContext())) {
                            //连接不到聊天服务器
                            LogUtiles.LogI("连接不到聊天服务器");
                        } else {
                            //当前网络不可用，请检查网络设置
                            LogUtiles.LogI("当前网络不可用，请检查网络设置");
                        }
                    }
                }
            });

        }
    }

}
