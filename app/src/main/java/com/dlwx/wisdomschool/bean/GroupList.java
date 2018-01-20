package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/19/019.
 */

public class GroupList {


    /**
     * code : 200
     * result : 获取成功
     * body : [{"groupid":"1","group_name":"我的群名称","imgid":"6","imgurl":"http://192.168.0.199/school//Uploads/20161223/585c9226aae09x1000.jpg"},{"groupid":"2","group_name":"我的群名称1","imgid":"6","imgurl":"http://192.168.0.199/school//Uploads/20161223/585c9226aae09x1000.jpg"}]
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

    public static class BodyBean {
        /**
         * groupid : 1
         * group_name : 我的群名称
         * imgid : 6
         * imgurl : http://192.168.0.199/school//Uploads/20161223/585c9226aae09x1000.jpg
         */

        private String groupid;
        private String group_name;
        private String imgid;
        private String imgurl;

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getImgid() {
            return imgid;
        }

        public void setImgid(String imgid) {
            this.imgid = imgid;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
