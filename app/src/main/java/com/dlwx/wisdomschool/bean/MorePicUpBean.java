package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class MorePicUpBean {

    /**
     * body : {"file":"http://192.168.0.199/school//Uploads/5a4f3d1d52412x250.jpg,","fileid":"689,"}
     * code : 200
     * result : 上传成功
     */

    private BodyBean body;
    private int code;
    private String result;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

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

    public static class BodyBean {
        /**
         * file : http://192.168.0.199/school//Uploads/5a4f3d1d52412x250.jpg,
         * fileid : 689,
         */

        private String file;
        private String fileid;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getFileid() {
            return fileid;
        }

        public void setFileid(String fileid) {
            this.fileid = fileid;
        }
    }
}
