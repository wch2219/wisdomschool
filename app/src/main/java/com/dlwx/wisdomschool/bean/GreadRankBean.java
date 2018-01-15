package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15/015.
 */

public class GreadRankBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"userid":"6","score":"98.00","sort":1,"user_nickname":"张三"},{"userid":"7","score":"98.00","sort":2,"user_nickname":"李四"},{"userid":"8","score":"98.00","sort":3,"user_nickname":"王五"}]
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
         * userid : 6
         * score : 98.00
         * sort : 1
         * user_nickname : 张三
         */

        private String userid;
        private String score;
        private int sort;
        private String user_nickname;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }
    }
}
