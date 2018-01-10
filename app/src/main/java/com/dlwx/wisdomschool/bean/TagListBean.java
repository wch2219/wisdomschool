package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10/010.
 */

public class TagListBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"id":"1","signame":"品德少年"},{"id":"2","signame":"热爱学习"},{"id":"3","signame":"体育活动"},{"id":"4","signame":"动手能力"},{"id":"5","signame":"品德发展"},{"id":"6","signame":"公民素养"},{"id":"7","signame":"修习课程"},{"id":"8","signame":"学业成绩"},{"id":"9","signame":"身心健康"},{"id":"10","signame":"艺术素养"},{"id":"11","signame":"实践能力"},{"id":"12","signame":"创新精神"}]
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
         * signame : 品德少年
         */

        private String id;
        private String signame;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSigname() {
            return signame;
        }

        public void setSigname(String signame) {
            this.signame = signame;
        }
    }
}
