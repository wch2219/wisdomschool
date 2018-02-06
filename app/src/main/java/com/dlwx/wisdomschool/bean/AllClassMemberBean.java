package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/3/003.
 */

public class AllClassMemberBean {

    /**
     * body : [{"add_user":[{"header_pic":"http://39.107.74.235/school//Uploads/5a72810f3bbb7x250.png","join_role":"Sheldon","userid":"15"},{"header_pic":"http://39.107.74.235/school/assets/default_user_photo.jpg","join_role":"乐乐妈妈","userid":"19"}],"class_name":"熊孩子","class_pic":"http://39.107.74.235/school//Uploads/5a727040e52ccx250.jpg","cnid":"23","total_user":"2"}]
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
         * add_user : [{"header_pic":"http://39.107.74.235/school//Uploads/5a72810f3bbb7x250.png","join_role":"Sheldon","userid":"15"},{"header_pic":"http://39.107.74.235/school/assets/default_user_photo.jpg","join_role":"乐乐妈妈","userid":"19"}]
         * class_name : 熊孩子
         * class_pic : http://39.107.74.235/school//Uploads/5a727040e52ccx250.jpg
         * cnid : 23
         * total_user : 2
         */

        private String class_name;
        private String class_pic;
        private String cnid;
        private String total_user;
        private boolean check;
        private List<AddUserBean> add_user;

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

        public String getTotal_user() {
            return total_user;
        }

        public void setTotal_user(String total_user) {
            this.total_user = total_user;
        }

        public List<AddUserBean> getAdd_user() {
            return add_user;
        }

        public void setAdd_user(List<AddUserBean> add_user) {
            this.add_user = add_user;
        }

        public static class AddUserBean {
            /**
             * header_pic : http://39.107.74.235/school//Uploads/5a72810f3bbb7x250.png
             * join_role : Sheldon
             * userid : 15
             */

            private String header_pic;
            private String join_role;
            private String userid;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getJoin_role() {
                return join_role;
            }

            public void setJoin_role(String join_role) {
                this.join_role = join_role;
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
