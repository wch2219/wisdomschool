package com.dlwx.wisdomschool.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/20/020.
 */

public class HomeListBean {

    /**
     * body : [{"class_name":"阿坝","classid":"8","content":"班级通知","content_pic":["http://192.168.0.199/school//Uploads/20161227/5862031a174f9.png","http://192.168.0.199/school//Uploads/20161230/586621896d4bbx1000.jpg","http://192.168.0.199/school//Uploads/20161228/586335db7f3f8x1000.jpg"],"content_voice":"http://192.168.0.199/school//Uploads/Videos/5863334ed4bfd.mp4","createdtime":"2018年01月18日 14:47","header_pic":"http://192.168.0.199/school//Uploads/20161227/5862023a2cab4.png","id":"5","is_del":"2","is_send":2,"nickname":"汤文艳","theme":"1","theme_endtime":"0","theme_name":"班级通知","user_read":"7,8","userid":"1"},{"class_name":"阿坝","classid":"8","content":"这是内容","content_pic":["http://192.168.0.199/school//Uploads/20161227/58620202136c7.png","http://192.168.0.199/school//Uploads/20161228/5863277d657d2x1000.jpg"],"content_voice":"","createdtime":"2018年01月20日 14:20","header_pic":"http://192.168.0.199/school//Uploads/20161227/5862023a2cab4.png","id":"8","is_del":"2","is_send":2,"nickname":"叶明轩","theme":"5","theme_endtime":"0","theme_name":"活动收集","userid":"2"},{"class_name":"阿坝","classid":"8","content":"就咯喔ee_11","content_pic":["http://192.168.0.199/school//Uploads/5a62de7e7e1f9x250.png","http://192.168.0.199/school//Uploads/5a62de7ede34cx250.png","http://192.168.0.199/school//Uploads/5a62de7f4eeaax250.jpg","http://192.168.0.199/school//Uploads/5a62de80293f1x250.jpg"],"content_voice":"http://192.168.0.199/school//Uploads/Videos/5a62dfbb7b803.amr","createdtime":"2018年01月20日 14:20","header_pic":"http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg","id":"9","is_del":"2","is_send":1,"nickname":"只是","theme":"5","theme_endtime":"","theme_name":"活动收集","userid":"3"}]
     * code : 200
     * result : 获取成功
     */

    private int code;
    private String result;
    private List<BodyBean> body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean implements Serializable{
        /**
         * class_name : 阿坝
         * classid : 8
         * content : 班级通知
         * content_pic : ["http://192.168.0.199/school//Uploads/20161227/5862031a174f9.png","http://192.168.0.199/school//Uploads/20161230/586621896d4bbx1000.jpg","http://192.168.0.199/school//Uploads/20161228/586335db7f3f8x1000.jpg"]
         * content_voice : http://192.168.0.199/school//Uploads/Videos/5863334ed4bfd.mp4
         * createdtime : 2018年01月18日 14:47
         * header_pic : http://192.168.0.199/school//Uploads/20161227/5862023a2cab4.png
         * id : 5
         * is_del : 2
         * is_send : 2
         * nickname : 汤文艳
         * theme : 1
         * theme_endtime : 0
         * theme_name : 班级通知
         * user_read : 7,8
         * userid : 1
         */

        private String class_name;
        private String classid;
        private String content;
        private String content_voice;
        private String createdtime;
        private String header_pic;
        private String id;
        private String is_del;
        private int is_send;
        private String nickname;
        private int theme;
        private String theme_endtime;
        private String theme_name;
        private String user_read;
        private String userid;
        private List<String> content_pic;
        private String teacher_isyue;
        private String parent_isyue;
        private String texturl;

        public String getTexturl() {
            return texturl;
        }

        public void setTexturl(String texturl) {
            this.texturl = texturl;
        }

        public String getParent_isyue() {
            return parent_isyue;
        }

        public void setParent_isyue(String parent_isyue) {
            this.parent_isyue = parent_isyue;
        }

        public String getTeacher_isyue() {
            return teacher_isyue;
        }

        public void setTeacher_isyue(String teacher_isyue) {
            this.teacher_isyue = teacher_isyue;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent_voice() {
            return content_voice;
        }

        public void setContent_voice(String content_voice) {
            this.content_voice = content_voice;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public int getIs_send() {
            return is_send;
        }

        public void setIs_send(int is_send) {
            this.is_send = is_send;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getTheme() {
            return theme;
        }

        public void setTheme(int theme) {
            this.theme = theme;
        }

        public String getTheme_endtime() {
            return theme_endtime;
        }

        public void setTheme_endtime(String theme_endtime) {
            this.theme_endtime = theme_endtime;
        }

        public String getTheme_name() {
            return theme_name;
        }

        public void setTheme_name(String theme_name) {
            this.theme_name = theme_name;
        }

        public String getUser_read() {
            return user_read;
        }

        public void setUser_read(String user_read) {
            this.user_read = user_read;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public List<String> getContent_pic() {
            return content_pic;
        }

        public void setContent_pic(List<String> content_pic) {
            this.content_pic = content_pic;
        }
    }
}
