package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26/026.
 */

public class GradeMapBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"irid":"8","sort":"1","score":"98.00"},{"irid":"26","sort":"2","score":"98.00"}]
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
         * irid : 8
         * sort : 1
         * score : 98.00
         */
        private String irid;
        private String sort;
        private double score;

        public String getIrid() {
            return irid;
        }

        public void setIrid(String irid) {
            this.irid = irid;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
