package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10/010.
 */

public class MyAllClassBean {

    /**
     * body : {"create_list":[{"class_name":"阿坝","class_no":"225212","class_pic":"http://192.168.0.199/school//Uploads/5a4c405a83215.png","cnid":"8","isadd":2,"ischeck":1,"teacher_name":"哈哈哈","total_user":"0","userid":"3"}],"join_list":[{"class_name":"阿坝","class_no":"225212","class_pic":"http://192.168.0.199/school//Uploads/5a4c405a83215.png","cnid":"8","isadd":1,"ischeck":"2","teacher_name":"哈哈哈","total_user":"0","userid":"3"},{"class_name":"阿坝","class_no":"225212","class_pic":"http://192.168.0.199/school//Uploads/5a4c405a83215.png","cnid":"8","isadd":1,"ischeck":"2","teacher_name":"哈哈哈","total_user":"0","userid":"3"},{"class_name":"阿坝","class_no":"225212","class_pic":"http://192.168.0.199/school//Uploads/5a4c405a83215.png","cnid":"8","isadd":1,"ischeck":"2","teacher_name":"哈哈哈","total_user":"0","userid":"3"}]}
     * code : 200
     * result : 获取成功
     */

    private BodyBean body;
    private int code;
    private String result;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

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

    public static class BodyBean {
        private List<CreateListBean> create_list;
        private List<JoinListBean> join_list;

        public List<CreateListBean> getCreate_list() {
            return create_list;
        }

        public void setCreate_list(List<CreateListBean> create_list) {
            this.create_list = create_list;
        }

        public List<JoinListBean> getJoin_list() {
            return join_list;
        }

        public void setJoin_list(List<JoinListBean> join_list) {
            this.join_list = join_list;
        }

        public static class CreateListBean {
            /**
             * class_name : 阿坝
             * class_no : 225212
             * class_pic : http://192.168.0.199/school//Uploads/5a4c405a83215.png
             * cnid : 8
             * isadd : 2
             * ischeck : 1
             * teacher_name : 哈哈哈
             * total_user : 0
             * userid : 3
             */

            private String class_name;
            private String class_no;
            private String class_pic;
            private String cnid;
            private int isadd;
            private int ischeck;
            private String teacher_name;
            private String total_user;
            private String userid;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

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

            public int getIscheck() {
                return ischeck;
            }

            public void setIscheck(int ischeck) {
                this.ischeck = ischeck;
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

        public static class JoinListBean {
            /**
             * class_name : 阿坝
             * class_no : 225212
             * class_pic : http://192.168.0.199/school//Uploads/5a4c405a83215.png
             * cnid : 8
             * isadd : 1
             * ischeck : 2
             * teacher_name : 哈哈哈
             * total_user : 0
             * userid : 3
             */

            private String class_name;
            private String class_no;
            private String class_pic;
            private String cnid;
            private int isadd;
            private String ischeck;
            private String teacher_name;
            private String total_user;
            private String userid;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

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

            public String getIscheck() {
                return ischeck;
            }

            public void setIscheck(String ischeck) {
                this.ischeck = ischeck;
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
}
