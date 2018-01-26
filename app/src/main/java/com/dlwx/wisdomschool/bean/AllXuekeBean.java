package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26/026.
 */

public class AllXuekeBean {

    /**
     * body : [{"xueke_name":"语文","xuekeid":"1"},{"xueke_name":"数学","xuekeid":"2"},{"xueke_name":"英语","xuekeid":"3"},{"xueke_name":"化学","xuekeid":"8"},{"xueke_name":"生物","xuekeid":"10"},{"xueke_name":"物理","xuekeid":"17"},{"xueke_name":"总分","xuekeid":"999"}]
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
         * xueke_name : 语文
         * xuekeid : 1
         */

        private String xueke_name;
        private String xuekeid;

        public String getXueke_name() {
            return xueke_name;
        }

        public void setXueke_name(String xueke_name) {
            this.xueke_name = xueke_name;
        }

        public String getXuekeid() {
            return xuekeid;
        }

        public void setXuekeid(String xuekeid) {
            this.xuekeid = xuekeid;
        }
    }
}
