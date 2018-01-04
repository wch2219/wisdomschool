package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/2/002.
 */

public class UpPicBean {

    /**
     * code : 200
     * result : 上传成功
     * body : {"fileid":22,"file":"http://192.168.0.199/amoy/Uploads/111.png"}
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
         * fileid : 22
         * file : http://192.168.0.199/amoy/Uploads/111.png
         */

        private int fileid;
        private String file;

        public int getFileid() {
            return fileid;
        }

        public void setFileid(int fileid) {
            this.fileid = fileid;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}
