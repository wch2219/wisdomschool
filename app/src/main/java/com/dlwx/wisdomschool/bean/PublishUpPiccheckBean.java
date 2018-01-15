package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15/015.
 */

public class PublishUpPiccheckBean {

    /**
     * code : 200
     * result : OK
     * body : ["3","4"]
     */

    private int code;
    private String result;
    private List<Integer> body;

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

    public List<Integer> getBody() {
        return body;
    }

    public void setBody(List<Integer> body) {
        this.body = body;
    }
}
