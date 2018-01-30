package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29/029.
 */

public class MorePicBean {

    /**
     * body : [{"file_url":"http://192.168.0.199/school//Uploads/5a6ece9702dc6x250.jpg","id":"1138","size":277997},{"file_url":"http://192.168.0.199/school//Uploads/5a6ece9704c4bx250.jpg","id":"1139","size":499551}]
     * code : 200
     * result : 上传成功
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
         * file_url : http://192.168.0.199/school//Uploads/5a6ece9702dc6x250.jpg
         * id : 1138
         * size : 277997
         */

        private String file_url;
        private String id;
        private String size;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
