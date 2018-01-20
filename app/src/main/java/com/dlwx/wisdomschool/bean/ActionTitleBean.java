package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/20/020.
 */

public class ActionTitleBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"id":"1","activity_type":"班级通知"},{"id":"2","activity_type":"群组讨论"},{"id":"3","activity_type":"调查问卷"},{"id":"4","activity_type":"视频（实时）记录"},{"id":"5","activity_type":"活动收集"},{"id":"6","activity_type":"布置作业"}]
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
         * id : 1
         * activity_type : 班级通知
         */

        private String id;
        private String activity_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_type() {
            return activity_type;
        }

        public void setActivity_type(String activity_type) {
            this.activity_type = activity_type;
        }
    }
}
