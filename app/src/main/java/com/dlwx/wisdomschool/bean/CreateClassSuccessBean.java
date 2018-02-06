package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/2/002.
 */

public class CreateClassSuccessBean {

    /**
     * code : 200
     * result : 创建班级成功
     * body : 174866
     */

    private int code;
    private String result;
    private Body body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public class Body{
        private String  classid;
        private String   class_no;

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getClass_no() {
            return class_no;
        }

        public void setClass_no(String class_no) {
            this.class_no = class_no;
        }
    }
}
