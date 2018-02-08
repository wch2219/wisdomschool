package com.dlwx.wisdomschool.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.wisdomschool.activitys.ClassHistoryNewsActivitry;
import com.dlwx.wisdomschool.activitys.ClassManageActivity;
import com.dlwx.wisdomschool.base.MainActivity;
import com.dlwx.wisdomschool.base.MyApplication;
import com.dlwx.wisdomschool.bean.NotifitBean;
import com.dlwx.wisdomschool.listener.ListenerUtile;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "wch";
    //    private NotiFicaBean notiFicaBean;
    private Context ctx;
    private PendingIntent pi;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.ctx = context;
        try {
            Bundle bundle = intent.getExtras();
//			Log.d(TAG, "[NotiReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.i(TAG, "[NotiReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.i(TAG, "[NotiReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.i(TAG, "[NotiReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.d(TAG, "[NotiReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                processCustomMessage(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.i("wch", "[NotiReceiver] 用户点击打开了通知" + MyApplication.EXTRAS);
                Intent inten = null;
                Gson gson = new Gson();
                NotifitBean notifitBean = gson.fromJson(MyApplication.EXTRAS, NotifitBean.class);
                int type = notifitBean.getType();
//                1:同意申请加入班级，给申请人推消息
//                2:被设置为任课老师
//                3:请出班级
//                4:退班申请成功
//                5:申请加入班级，给老师推消息
//                6:申请退出班级，给老师推消息
                switch (type) {
                    case 1://同意入班
                        inten = new Intent(context, ClassManageActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(inten);
                        break;
                    case 2://2:被设置为任课老师
                        inten = new Intent(context, ClassManageActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(inten);
                        break;
                    case 3:// 3:请出班级
                        inten = new Intent(context, ClassManageActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(inten);
                        break;
                    case 4:// 3:请出班级
                        inten = new Intent(context, ClassManageActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(inten);
                        break;

                    case 5://申请加入班级，老师收到消息
                        inten = new Intent(context, ClassManageActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(inten);
                        break;
                    case 6:

                        break;
                    case 7://收到通知 请及时点阅
                        inten = new Intent(context, ClassHistoryNewsActivitry.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(inten);
                        break;
                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.i(TAG, "[NotiReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.i(TAG, "[NotiReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.i(TAG, "[NotiReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            Log.i("wch", e.getMessage());
        }

    }


    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String string = bundle.getString(JPushInterface.EXTRA_ALERT);
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtiles.LogI(string + title);
        Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        try {
            JSONObject extraJson = new JSONObject(extras);
            MyApplication.EXTRAS = extraJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        NotifitBean notifitBean = gson.fromJson(MyApplication.EXTRAS, NotifitBean.class);
        LogUtiles.LogI(extras.toString());
        int type = notifitBean.getType();
        //                1:同意申请加入班级，给申请人推消息
//                2:被设置为任课老师
//                3:请出班级
//                4:退班申请成功
//                5:申请加入班级，给老师推消息
//                6:申请退出班级，给老师推消息
        switch (type) {
            case 1://同意入班
                if (ListenerUtile.classNotifitionListener != null) {
                    try {
                        ListenerUtile.classNotifitionListener.agree();
                    } catch (Exception e) {
                        LogUtiles.LogI(e.getMessage());
                    }
                }
                break;
            case 2: // 2:被设置为任课老师
                if (ListenerUtile.classNotifitionListener != null) {
                    try {
                        ListenerUtile.classNotifitionListener.send();
                    } catch (Exception e) {
                        LogUtiles.LogI(e.getMessage());
                    }
                }
                break;
            case 3:// 3:请出班级
                break;
            case 4:// 4:退班申请成功

                break;
            case 5://申请加入班级，老师收到消息
                if (ListenerUtile.classNotifitionListener != null) {
                    try {
                        ListenerUtile.classNotifitionListener.send();
                    } catch (Exception e) {
                        LogUtiles.LogI(e.getMessage());
                    }
                }
                break;
            case 6:

                break;


        }

    }

}
