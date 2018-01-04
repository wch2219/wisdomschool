package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class MermberMessBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"jcid":"8","cid":"6","userid":"1","join_role":"体育老师","join_user_vip":"3","istui":"2","ischeck":"1","telephone":"18806810978","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg","join_vip_name":"任课老师"}
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
         * jcid : 8
         * cid : 6
         * userid : 1
         * join_role : 体育老师
         * join_user_vip : 3
         * istui : 2
         * ischeck : 1
         * telephone : 18806810978
         * header_pic : http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg
         * join_vip_name : 任课老师
         */

        private String jcid;
        private String cid;
        private String userid;
        private String join_role;
        private int join_user_vip;
        private String istui;
        private String ischeck;
        private String telephone;
        private String header_pic;
        private String join_vip_name;

        public String getJcid() {
            return jcid;
        }

        public void setJcid(String jcid) {
            this.jcid = jcid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
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

        public int getJoin_user_vip() {
            return join_user_vip;
        }

        public void setJoin_user_vip(int join_user_vip) {
            this.join_user_vip = join_user_vip;
        }

        public String getIstui() {
            return istui;
        }

        public void setIstui(String istui) {
            this.istui = istui;
        }

        public String getIscheck() {
            return ischeck;
        }

        public void setIscheck(String ischeck) {
            this.ischeck = ischeck;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }

        public String getJoin_vip_name() {
            return join_vip_name;
        }

        public void setJoin_vip_name(String join_vip_name) {
            this.join_vip_name = join_vip_name;
        }
    }
}
