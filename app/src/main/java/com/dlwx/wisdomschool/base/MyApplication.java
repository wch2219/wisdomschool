package com.dlwx.wisdomschool.base;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.baselib.utiles.SpUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ChatActivity;
import com.dlwx.wisdomschool.activitys.LoginInActivity;
import com.dlwx.wisdomschool.listener.ListenerUtile;
import com.dlwx.wisdomschool.service.MyReceiver;
import com.dlwx.wisdomschool.utiles.ResouseString;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.TbsDownloader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * Created by Administrator on 2017/12/12/012.
 */

public class MyApplication extends Application {
    public static final String BuglyAppID = "567efebd79";//bug  版本更新
    public static String classnames;
    public static EaseUI easeUI;
    public static String Token;
    public static String EXTRAS = "";
    private SharedPreferences sp;
    private int pos;
    public static SoundPool soundPool;
    private int loadpath;

    @Override
    public void onCreate() {
        super.onCreate();

        OkGo.getInstance().init(this);
        TbsDownloader.needDownload(getApplicationContext(), false);
        classnames = ResouseString.classnames;
        easeUIInit();
        sp = getSharedPreferences(SpUtiles.SP_Mode, MODE_PRIVATE);
        Token = sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Token, "");
        LogUtiles.LogI(Token);
        Bugly.init(getApplicationContext(), BuglyAppID, false);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        if (!TextUtils.isEmpty(Token)) {
//            huanxinLogin(sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Userid,""),sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Userid,""));
        }
        pos = 0;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setLatestNotificationNumber(this, 3);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        MyReceiver mReceiver = new MyReceiver();
        registerReceiver(mReceiver, intentFilter);

        String user_id = sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Userid, "");
        if (user_id != null) {

            Set<String> tags = new HashSet<>();
            tags.add(user_id);
            JPushInterface.setTags(getApplicationContext(), 1, tags);
        }
    }
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {


            String username = null;
            String from_username = null;
            String to_headportrait = null;
            String to_username = null;
            int ChatType = 0;
            for (EMMessage message : messages) {
                try {
                    // group message
                    if (message.getChatType() == EMMessage.ChatType.GroupChat || message.getChatType() == EMMessage.ChatType.ChatRoom) {
                        username = message.getTo();
                        ChatType = EaseConstant.CHATTYPE_GROUP;
                        from_username = message.getStringAttribute("groupname");//获取群名称
                    } else {
                        // single chat message
                        username = message.getFrom();
                        ChatType = EaseConstant.CHATTYPE_SINGLE;

                        if (username.equals(sp.getString(com.dlwx.wisdomschool.utiles.SpUtiles.Userid,""))) {
                            to_username = message.getStringAttribute("to_username");
                            from_username = message.getStringAttribute("to_username");
                            to_headportrait = message.getStringAttribute("to_headportrait");
                        }else{
                            to_username = message.getStringAttribute("from_username");
                            from_username = message.getStringAttribute("from_username");
                            to_headportrait = message.getStringAttribute("from_headportrait");
                        }
                        try{
                            if (ListenerUtile.priChatListListener != null) {
                                ListenerUtile.priChatListListener.priChatList();
                            }
                        }catch (Exception e){
                            LogUtiles.LogI(e.getMessage());
                        }
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }

            handler.sendEmptyMessage(1);
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
            if (!name.equals(ChatActivity.class.getName())) {

            Intent inten = null;
            inten = new Intent(getApplicationContext(), ChatActivity.class);
            inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            inten.putExtra("title", from_username);
            inten.putExtra(com.dlwx.wisdomschool.utiles.SpUtiles.OtherHeadPic,to_headportrait);
            inten.putExtra(com.dlwx.wisdomschool.utiles.SpUtiles.OtherNickName,to_username);
            inten.putExtra(EaseConstant.EXTRA_USER_ID, username);
            inten.putExtra(EaseConstant.EXTRA_CHAT_TYPE, ChatType);

            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, inten, PendingIntent.FLAG_UPDATE_CURRENT);
            //获取NotificationManager实例
            NotificationManager notifyManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            //实例化NotificationCompat.Builde并设置相关属性
            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    //设置小图标
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //设置通知标题
                    .setContentTitle("智慧家校")
                    .setContentIntent(pi)
                    //设置通知内容
                    .setContentText("收到一条新消息");
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setAutoCancel(true);
            pos++;
            //设置通知时间，默认为系统发出通知的时间，通常不用设置
            //.setWhen(System.currentTimeMillis());
            //通过builder.build()方法生成Notification对象,并发送通知,id=1
            notifyManager.notify(pos, builder.build());
            }
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
                        EMClient.getInstance().logout(true);//退出环信登录
                        SharedPreferences sp = getSharedPreferences(SpUtiles.SP_Mode, MODE_PRIVATE);
                        sp.edit().putString(com.dlwx.wisdomschool.utiles.SpUtiles.Token, "").commit();
                        sp.edit().putString(com.dlwx.wisdomschool.utiles.SpUtiles.Nickname, "").commit();
                        sp.edit().putString(com.dlwx.wisdomschool.utiles.SpUtiles.Header_pic, "").commit();
                        sp.edit().putString(com.dlwx.wisdomschool.utiles.SpUtiles.Userid, "").commit();
                        sp.edit().putString(com.dlwx.wisdomschool.utiles.SpUtiles.Telephone, "").commit();
                        sp.edit().putInt(com.dlwx.wisdomschool.utiles.SpUtiles.TeacherOrPatriarch, 0).commit();
                        MyApplication.Token = "";
                        Intent intent = new Intent(getApplicationContext(), LoginInActivity.class);
                        intent.putExtra("isshow",true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);

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

   private Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
           loadpath = soundPool.load(getApplicationContext(), R.raw.tishi, 1);
           soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
               @Override
               public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                   LogUtiles.LogI("声音加载完成");

                   AudioManager am = (AudioManager) getApplicationContext()
                           .getSystemService(Context.AUDIO_SERVICE);
                   float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                   float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                   float volumnRatio = volumnCurrent / audioMaxVolumn;
                   soundPool.play(loadpath,
                           audioMaxVolumn,// 左声道音量
                           audioMaxVolumn,// 右声道音量
                           1, // 优先级
                           0,// 循环播放次数
                           1);// 回放速度，该值在0.5-2.0之间 1为正常速度
               }
           });

       }
   };
}
