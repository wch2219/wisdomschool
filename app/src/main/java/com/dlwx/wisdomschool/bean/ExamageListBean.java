package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25/025.
 */

public class ExamageListBean {

    /**
     * body : [{"enid":"1","name":"0岁以下考试"},{"enid":"2","name":"1岁考试"},{"enid":"3","name":"2岁考试"},{"enid":"4","name":"3岁考试"},{"enid":"5","name":"4岁考试"},{"enid":"6","name":"5岁考试"},{"enid":"7","name":"6岁考试"},{"enid":"8","name":"7岁考试"},{"enid":"9","name":"8岁考试"},{"enid":"10","name":"9岁考试"},{"enid":"11","name":"10岁考试"},{"enid":"12","name":"11岁考试"},{"enid":"13","name":"12岁考试"},{"enid":"14","name":"13岁考试"},{"enid":"15","name":"14岁考试"},{"enid":"16","name":"15岁考试"},{"enid":"17","name":"16岁考试"},{"enid":"18","name":"17岁考试"},{"enid":"19","name":"18岁考试"},{"enid":"20","name":"19岁考试"},{"enid":"21","name":"离婚考试"},{"enid":"22","name":"结婚考试"}]
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
         * enid : 1
         * name : 0岁以下考试
         */

        private String enid;
        private String name;

        public String getEnid() {
            return enid;
        }

        public void setEnid(String enid) {
            this.enid = enid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
