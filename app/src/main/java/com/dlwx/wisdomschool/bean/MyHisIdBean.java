package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30/030.
 */

public class MyHisIdBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"urid":"4","role_name":"高飞妈妈"},{"urid":"5","role_name":"高飞爸爸"},{"urid":"6","role_name":"高飞奶奶"}]
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
         * urid : 4
         * role_name : 高飞妈妈
         */

        private String urid;
        private String role_name;

        public String getUrid() {
            return urid;
        }

        public void setUrid(String urid) {
            this.urid = urid;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }
    }
}
