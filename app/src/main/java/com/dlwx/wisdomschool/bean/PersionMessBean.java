package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/11/011.
 */

public class PersionMessBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"app_download_qrcode":"http://192.168.0.199/school/assets/default_user_photo.jpg","info":{"userid":"2","role":"1","school_name":"文三小学","telephone":"18072723804","password":"e10adc3949ba59abbe56e057f20f883e","exten_code":"333263","p_exten_code":null,"token":"aaa42296669b958c3cee6c0475c8093e","nickname":"叶明轩","header_pic":"http://192.168.0.199/school//Uploads/20161227/5862023a2cab4.png","is_easemob":"0","isonline":"0","online_endtime":"0"}}
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
         * app_download_qrcode : http://192.168.0.199/school/assets/default_user_photo.jpg
         * info : {"userid":"2","role":"1","school_name":"文三小学","telephone":"18072723804","password":"e10adc3949ba59abbe56e057f20f883e","exten_code":"333263","p_exten_code":null,"token":"aaa42296669b958c3cee6c0475c8093e","nickname":"叶明轩","header_pic":"http://192.168.0.199/school//Uploads/20161227/5862023a2cab4.png","is_easemob":"0","isonline":"0","online_endtime":"0"}
         */

        private String app_download_qrcode;
        private InfoBean info;

        public String getApp_download_qrcode() {
            return app_download_qrcode;
        }

        public void setApp_download_qrcode(String app_download_qrcode) {
            this.app_download_qrcode = app_download_qrcode;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * userid : 2
             * role : 1
             * school_name : 文三小学
             * telephone : 18072723804
             * password : e10adc3949ba59abbe56e057f20f883e
             * exten_code : 333263
             * p_exten_code : null
             * token : aaa42296669b958c3cee6c0475c8093e
             * nickname : 叶明轩
             * header_pic : http://192.168.0.199/school//Uploads/20161227/5862023a2cab4.png
             * is_easemob : 0
             * isonline : 0
             * online_endtime : 0
             */

            private String userid;
            private String role;
            private String school_name;
            private String telephone;
            private String password;
            private String exten_code;
            private Object p_exten_code;
            private String token;
            private String nickname;
            private String header_pic;
            private String is_easemob;
            private String isonline;
            private String online_endtime;

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getExten_code() {
                return exten_code;
            }

            public void setExten_code(String exten_code) {
                this.exten_code = exten_code;
            }

            public Object getP_exten_code() {
                return p_exten_code;
            }

            public void setP_exten_code(Object p_exten_code) {
                this.p_exten_code = p_exten_code;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getIs_easemob() {
                return is_easemob;
            }

            public void setIs_easemob(String is_easemob) {
                this.is_easemob = is_easemob;
            }

            public String getIsonline() {
                return isonline;
            }

            public void setIsonline(String isonline) {
                this.isonline = isonline;
            }

            public String getOnline_endtime() {
                return online_endtime;
            }

            public void setOnline_endtime(String online_endtime) {
                this.online_endtime = online_endtime;
            }
        }
    }
}
