package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16/016.
 */

public class PublishGrouBean {

    /**
     * code : 200
     * result : OK
     * body : {"uncomplete_sign":["2","3","4"],"is_infour":"2"}
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
         * uncomplete_sign : ["2","3","4"]
         * is_infour : 2
         */

        private String is_infour;
        private List<String> uncomplete_sign;

        public String getIs_infour() {
            return is_infour;
        }

        public void setIs_infour(String is_infour) {
            this.is_infour = is_infour;
        }

        public List<String> getUncomplete_sign() {
            return uncomplete_sign;
        }

        public void setUncomplete_sign(List<String> uncomplete_sign) {
            this.uncomplete_sign = uncomplete_sign;
        }
    }
}
