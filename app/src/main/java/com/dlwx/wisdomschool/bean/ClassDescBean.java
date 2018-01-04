package com.dlwx.wisdomschool.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class ClassDescBean implements Serializable{

    /**
     * code : 200
     * result : 获取成功
     * body : {"cnid":"6","userid":"2","class_name":"004","class_pic":"http://192.168.0.199/school/Uploads/20161223/585c90f884c39x1000.jpg","class_no":"466495","class_qrcode":"http://192.168.0.199/school/qrcode/class_no/151418281263.png","school_name":"罗丹画室","true_classname":"高中,高一,3班","total_user":"1","is_tel_find":"1","is_teacher_open":"1","create_teacher":{"userid":"2","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg","nickname":"叶明轩"},"add_teacher":[{"userid":"1","join_role":"体育老师","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg"}],"add_user":[{"jcid":"5","userid":"2","join_role":"高飞妈妈","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg"}]}
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

    public static class BodyBean implements Serializable{
        /**
         * cnid : 6
         * userid : 2
         * class_name : 004
         * class_pic : http://192.168.0.199/school/Uploads/20161223/585c90f884c39x1000.jpg
         * class_no : 466495
         * class_qrcode : http://192.168.0.199/school/qrcode/class_no/151418281263.png
         * school_name : 罗丹画室
         * true_classname : 高中,高一,3班
         * total_user : 1
         * is_tel_find : 1
         * is_teacher_open : 1
         * create_teacher : {"userid":"2","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg","nickname":"叶明轩"}
         * add_teacher : [{"userid":"1","join_role":"体育老师","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg"}]
         * add_user : [{"jcid":"5","userid":"2","join_role":"高飞妈妈","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg"}]
         */

        private String cnid;
        private String userid;
        private String class_name;
        private String class_pic;
        private String class_no;
        private String class_qrcode;
        private String school_name;
        private String true_classname;
        private String total_user;
        private String is_tel_find;
        private int is_teacher_open;
        private CreateTeacherBean create_teacher;
        private List<AddTeacherBean> add_teacher;
        private List<AddUserBean> add_user;

        public String getCnid() {
            return cnid;
        }

        public void setCnid(String cnid) {
            this.cnid = cnid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
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

        public String getClass_no() {
            return class_no;
        }

        public void setClass_no(String class_no) {
            this.class_no = class_no;
        }

        public String getClass_qrcode() {
            return class_qrcode;
        }

        public void setClass_qrcode(String class_qrcode) {
            this.class_qrcode = class_qrcode;
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

        public String getTotal_user() {
            return total_user;
        }

        public void setTotal_user(String total_user) {
            this.total_user = total_user;
        }

        public String getIs_tel_find() {
            return is_tel_find;
        }

        public void setIs_tel_find(String is_tel_find) {
            this.is_tel_find = is_tel_find;
        }

        public int getIs_teacher_open() {
            return is_teacher_open;
        }

        public void setIs_teacher_open(int is_teacher_open) {
            this.is_teacher_open = is_teacher_open;
        }

        public CreateTeacherBean getCreate_teacher() {
            return create_teacher;
        }

        public void setCreate_teacher(CreateTeacherBean create_teacher) {
            this.create_teacher = create_teacher;
        }

        public List<AddTeacherBean> getAdd_teacher() {
            return add_teacher;
        }

        public void setAdd_teacher(List<AddTeacherBean> add_teacher) {
            this.add_teacher = add_teacher;
        }

        public List<AddUserBean> getAdd_user() {
            return add_user;
        }

        public void setAdd_user(List<AddUserBean> add_user) {
            this.add_user = add_user;
        }

        public static class CreateTeacherBean implements Serializable{
            /**
             * userid : 2
             * header_pic : http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg
             * nickname : 叶明轩
             */

            private String userid;
            private String header_pic;
            private String nickname;

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public static class AddTeacherBean{
            /**
             * userid : 1
             * join_role : 体育老师
             * header_pic : http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg
             */

            private String userid;
            private String join_role;
            private String header_pic;
            private String  jcid;

            public String getJcid() {
                return jcid;
            }

            public void setJcid(String jcid) {
                this.jcid = jcid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getJoin_role() {
                return join_role;
            }

            public void setJoin_role(String join_role) {
                this.join_role = join_role;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }
        }

        public static class AddUserBean implements Serializable {
            /**
             * jcid : 5
             * userid : 2
             * join_role : 高飞妈妈
             * header_pic : http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg
             */

            private String jcid;
            private String userid;
            private String join_role;
            private String header_pic;
            private boolean ischeck;

            public boolean isIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            public String getJcid() {
                return jcid;
            }

            public void setJcid(String jcid) {
                this.jcid = jcid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getJoin_role() {
                return join_role;
            }

            public void setJoin_role(String join_role) {
                this.join_role = join_role;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }
        }
    }
}
