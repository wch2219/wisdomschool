package com.dlwx.wisdomschool.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.baselib.utiles.SpUtiles;
import com.dlwx.wisdomschool.utiles.ResouseString;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.TbsDownloader;

import java.util.List;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * Created by Administrator on 2017/12/12/012.
 */

public class MyApplication extends Application {
    public static final String BuglyAppID = "567efebd79";//bug  版本更新
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
        SharedPreferences sp = getSharedPreferences(SpUtiles.SP_Mode, MODE_PRIVATE);
        Token = sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Token, "");
        LogUtiles.LogI(Token);
        Bugly.init(getApplicationContext(), BuglyAppID, false);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        if (!TextUtils.isEmpty(Token)) {
//            huanxinLogin(sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Userid,""),sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Userid,""));
        }
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {


            String username = null;
            for (EMMessage message : messages) {
                // group message
                if (message.getChatType() == EMMessage.ChatType.GroupChat || message.getChatType() == EMMessage.ChatType.ChatRoom) {
                    username = message.getTo();
                    EMMessageBody body = message.getBody();
                } else {
                    // single chat message
                    username = message.getFrom();
                }
            }
//            Intent inten = null;
//            inten = new Intent(getApplicationContext(), ChatActivity.class);
//            inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, inten, 0);
//            //获取NotificationManager实例
//            NotificationManager notifyManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            //实例化NotificationCompat.Builde并设置相关属性
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(   getApplicationContext())
//                    //设置小图标
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    //设置通知标题
//
//                    .setContentTitle(username)
//                    .setContentIntent(pi)
//                    //设置通知内容
//                    .setContentText(mess);
//            builder.setDefaults(Notification.DEFAULT_ALL);
//            //设置通知时间，默认为系统发出通知的时间，通常不用设置
//            //.setWhen(System.currentTimeMillis());
//            //通过builder.build()方法生成Notification对象,并发送通知,id=1
//            notifyManager.notify(1, builder.build());

        }


        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

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
        EaseUI.getInstance().init(getApplicationContext(), options);
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

    /**
     * 登录环信
     */
    private void huanxinLogin(String username, String pwd) {
        EMClient.getInstance().login(username, pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                LogUtiles.LogI("登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LogUtiles.LogI("登录聊天服务器失败！");
            }
        });
    }
}
