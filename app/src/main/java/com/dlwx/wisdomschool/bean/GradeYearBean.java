package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15/015.
 */

public class GradeYearBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"irid":"7","date":"01月1日","sort":"1"},{"irid":"8","date":"01月1日","sort":"2"}]
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
         * irid : 7
         * date : 01月1日
         * sort : 1
         */

        private String irid;
        private String date;
        private String sort;

        public String getIrid() {
            return irid;
        }

        public void setIrid(String irid) {
            this.irid = irid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
