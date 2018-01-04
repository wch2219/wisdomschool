package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/2/002.
 */

public class LoginBean {

    /**
     * code : 200
     * result : 登录成功
     * body : {"userid":"2","token":"aaa42296669b958c3cee6c0475c8093e","telephone":"18072723804","nickname":"0","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg"}
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
         * userid : 2
         * token : aaa42296669b958c3cee6c0475c8093e
         * telephone : 18072723804
         * nickname : 0
         * header_pic : http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg
         */

        private String userid;
        private String token;
        private String telephone;
        private String nickname;
        private String header_pic;
        private int isteacher;
        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
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

        public int getIsteacher() {
            return isteacher;
        }

        public void setIsteacher(int isteacher) {
            this.isteacher = isteacher;
        }
    }
}
