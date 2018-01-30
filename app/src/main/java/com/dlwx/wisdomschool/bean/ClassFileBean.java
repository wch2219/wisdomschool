package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class ClassFileBean {

    /**
     * body : {"list":[{"cfid":"5","file_pic":"","name":"文件夹1","size":"1.00","type":"3","fileid":"12","time":"2018-01-03"},{"cfid":"6","file_pic":"","name":"文件夹2","size":"1.00","type":"3"},{"cfid":"3","file_pic":"","fileid":"12","name":"33.mp3","size":"100.00","time":"2018-01-03","type":"2"},{"cfid":"4","file_pic":"","fileid":"22","name":"44.mp4","size":"200.00","time":"2018-01-03","type":"2"},{"cfid":"1","file_pic":"http://192.168.0.199/school/Uploads/20161227/5861fe0107285x1000.jpg","fileid":"9","name":"11.jpg","size":"466.00","time":"2018-01-03","type":"1"},{"cfid":"2","file_pic":"http://192.168.0.199/school/Uploads/20161227/58620064c0942.png","fileid":"10","name":"22.jpg","size":"2.00","time":"2018-01-03","type":"1"}],"use_size":"775.00"}
     * code : 200
     * result : 获取成功
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
         * list : [{"cfid":"5","file_pic":"","name":"文件夹1","size":"1.00","type":"3"},{"cfid":"6","file_pic":"","name":"文件夹2","size":"1.00","type":"3"},{"cfid":"3","file_pic":"","fileid":"12","name":"33.mp3","size":"100.00","time":"2018-01-03","type":"2"},{"cfid":"4","file_pic":"","fileid":"22","name":"44.mp4","size":"200.00","time":"2018-01-03","type":"2"},{"cfid":"1","file_pic":"http://192.168.0.199/school/Uploads/20161227/5861fe0107285x1000.jpg","fileid":"9","name":"11.jpg","size":"466.00","time":"2018-01-03","type":"1"},{"cfid":"2","file_pic":"http://192.168.0.199/school/Uploads/20161227/58620064c0942.png","fileid":"10","name":"22.jpg","size":"2.00","time":"2018-01-03","type":"1"}]
         * use_size : 775.00
         */

        private double use_size;
        private List<ListBean> list;

        public double getUse_size() {
            return use_size;
        }

        public void setUse_size(double use_size) {
            this.use_size = use_size;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * cfid : 5
             * file_pic :
             * name : 文件夹1
             * size : 1.00
             * type : 3
             * fileid : 12
             * time : 2018-01-03
             */

            private String cfid;
            private String file_pic;
            private String name;
            private String size;
            private int type;
            private String fileid;
            private String time;

            public String getCfid() {
                return cfid;
            }

            public void setCfid(String cfid) {
                this.cfid = cfid;
            }

            public String getFile_pic() {
                return file_pic;
            }

            public void setFile_pic(String file_pic) {
                this.file_pic = file_pic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getFileid() {
                return fileid;
            }

            public void setFileid(String fileid) {
                this.fileid = fileid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
