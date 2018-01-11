package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10/010.
 */

public class ConnonTagBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"id":"1","userid":"4","common_sign":"人生呐"},{"id":"2","userid":"4","common_sign":"劳动最光荣"},{"id":"3","userid":"4","common_sign":"练字"}]
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
         * id : 1
         * userid : 4
         * common_sign : 人生呐
         */

        private String id;
        private String userid;
        private String common_sign;
        private boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getCommon_sign() {
            return common_sign;
        }

        public void setCommon_sign(String common_sign) {
            this.common_sign = common_sign;
        }
    }
}
