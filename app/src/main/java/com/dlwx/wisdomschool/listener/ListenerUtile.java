package com.dlwx.wisdomschool.listener;

/**
 * Created by Administrator on 2018/2/6/006.
 */

public class ListenerUtile {

        public interface ClassNotifitionListener{
                //学生申请加入班级，教师端收到通知
                void send();
                //教师同意入班申请，家长收到通知 教师把成员提出班级收到通知
                void agree();
        }
        public static ClassNotifitionListener classNotifitionListener;

        public static void setApplyAddClassNotifitionListener(ClassNotifitionListener classNotifitionListener) {
                ListenerUtile.classNotifitionListener = classNotifitionListener;
        }

        //
        //群聊收到消息，刷新列表获取到纬度消息数量
        public interface GroupChatUnReadListener{
                void groupChatList();
        }
        public static GroupChatUnReadListener groupChatUnReadListener;

        public static void setGroupChatUnReadListener(GroupChatUnReadListener groupChatUnReadListener) {
                ListenerUtile.groupChatUnReadListener = groupChatUnReadListener;
        }

        //私聊收到新消息，刷新会话列表
        public interface PriChatListListener{
                void priChatList();
        }
        public static PriChatListListener priChatListListener;

        public static void setPriChatListListener(PriChatListListener priChatListListener) {
                ListenerUtile.priChatListListener = priChatListListener;
        }
}