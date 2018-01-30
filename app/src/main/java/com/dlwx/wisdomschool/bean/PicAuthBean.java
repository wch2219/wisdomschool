package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/29/029.
 */

public class PicAuthBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"imgname":"150813370029.png","imgurl":"http://192.168.0.199/amoy/smsimgcode/150813370029.png"}
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
         * imgname : 150813370029.png
         * imgurl : http://192.168.0.199/amoy/smsimgcode/150813370029.png
         */

        private String imgname;
        private String imgurl;

        public String getImgname() {
            return imgname;
        }

        public void setImgname(String imgname) {
            this.imgname = imgname;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
