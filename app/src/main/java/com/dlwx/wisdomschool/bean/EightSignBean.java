package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18/018.
 */

public class EightSignBean {

    /**
     * body : [{"id":5,"num":3},{"id":6,"num":2},{"id":7,"num":1},{"id":8,"num":2},{"id":9,"num":1},{"id":10,"num":2},{"id":11,"num":1},{"id":12,"num":2}]
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
         * id : 5
         * num : 3
         */

        private int id;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
