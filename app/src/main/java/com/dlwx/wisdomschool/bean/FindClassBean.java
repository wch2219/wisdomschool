package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class FindClassBean {

    /**
     * body : [{"class_name":"一年","class_no":"984012","class_pic":"http://192.168.0.199/school/Uploads/5a4c78600dd40.png","cnid":"9","isadd":2,"teacher_name":"0","total_user":"0","userid":"3"}]
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

    public static class BodyBean {
        /**
         * class_name : 一年
         * class_no : 984012
         * class_pic : http://192.168.0.199/school/Uploads/5a4c78600dd40.png
         * cnid : 9
         * isadd : 2
         * teacher_name : 0
         * total_user : 0
         * userid : 3
         */

        private String class_name;
        private String class_no;
        private String class_pic;
        private String cnid;
        private int isadd;
        private String teacher_name;
        private String total_user;
        private String userid;

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getClass_no() {
            return class_no;
        }

        public void setClass_no(String class_no) {
            this.class_no = class_no;
        }

        public String getClass_pic() {
            return class_pic;
        }

        public void setClass_pic(String class_pic) {
            this.class_pic = class_pic;
        }

        public String getCnid() {
            return cnid;
        }

        public void setCnid(String cnid) {
            this.cnid = cnid;
        }

        public int getIsadd() {
            return isadd;
        }

        public void setIsadd(int isadd) {
            this.isadd = isadd;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getTotal_user() {
            return total_user;
        }

        public void setTotal_user(String total_user) {
            this.total_user = total_user;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
