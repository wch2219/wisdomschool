package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class ClassInfoBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"cnid":"6","class_name":"004","class_pic":"http://192.168.0.199/school/Uploads/20170116/587c812ea953ex1000.png","school_name":"罗丹画室","true_classname":"高中高一3班","is_tel_find":"1"}
     */

    private int code;
    private String result;
    private BodyBean body;

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

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * cnid : 6
         * class_name : 004
         * class_pic : http://192.168.0.199/school/Uploads/20170116/587c812ea953ex1000.png
         * school_name : 罗丹画室
         * true_classname : 高中高一3班
         * is_tel_find : 1
         */

        private String cnid;
        private String class_name;
        private String class_pic;
        private String school_name;
        private String true_classname;
        private int is_tel_find;

        public String getCnid() {
            return cnid;
        }

        public void setCnid(String cnid) {
            this.cnid = cnid;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getClass_pic() {
            return class_pic;
        }

        public void setClass_pic(String class_pic) {
            this.class_pic = class_pic;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getTrue_classname() {
            return true_classname;
        }

        public void setTrue_classname(String true_classname) {
            this.true_classname = true_classname;
        }

        public int getIs_tel_find() {
            return is_tel_find;
        }

        public void setIs_tel_find(int is_tel_find) {
            this.is_tel_find = is_tel_find;
        }
    }
}
