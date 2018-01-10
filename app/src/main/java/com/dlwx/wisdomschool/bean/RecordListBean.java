package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9/009.
 */

public class RecordListBean {


    /**
     * body : [{"class_no":"466495","classid":"6","create_time":"2018-01-04 15:39:28","header_pic":"","id":"1","imgs":[],"nickname":"汤文艳","person_sign":["劳动","作业反馈","预习课程"],"quality_sign":["品德发展","修习课程"],"record_bf":"记录简介","type":"1","userid":"1","video":"http://192.168.0.196/house/Uploads/Videos/5863334ed4bfd.mp4"},{"class_no":"0","classid":"0","create_time":"2018-01-04 15:39:28","header_pic":"","id":"2","imgs":["http://192.168.0.196/house/Uploads/20161223/585c90f884c39.jpg","http://192.168.0.196/house/Uploads/20161223/585c91074884f.jpg","http://192.168.0.196/house/Uploads/20161223/585c911154748.jpg","http://192.168.0.196/house/Uploads/20161223/585c922695a34.jpg"],"nickname":"汤文艳","person_sign":["作业反馈"],"quality_sign":[],"record_bf":"欢迎使用智慧家校！智慧家校可以帮助您记录汇总孩子的成长点滴，这是一本真正的家校共育概念的成长手册！","type":"2","userid":"0","video":""},{"class_no":"984012","classid":"9","create_time":"2018-01-04 15:39:28","header_pic":"http://192.168.0.196/house/Uploads/5a5043dbcd474.png","id":"3","imgs":[],"nickname":"Sheldon","person_sign":["作业反馈"],"quality_sign":["品德发展"],"record_bf":"记录简介","type":"1","userid":"4","video":"http://192.168.0.196/house/Uploads/Videos/586336035357e.mp4"},{"class_no":"491251","classid":"6","create_time":"2018-01-04 15:39:28","header_pic":"http://192.168.0.196/house/Uploads/20161227/5862023a2cab4.png","id":"4","imgs":["http://192.168.0.196/house/Uploads/20161223/585c90f884c39.jpg","http://192.168.0.196/house/Uploads/20161223/585c91074884f.jpg","http://192.168.0.196/house/Uploads/20161223/585c911154748.jpg","http://192.168.0.196/house/Uploads/20161223/585c922695a34.jpg"],"nickname":"叶明轩","person_sign":[],"quality_sign":["实践能力"],"record_bf":"记录简介","type":"1","userid":"2","video":""},{"class_no":"466495","classid":"6","create_time":"2018-01-05 14:15:35","header_pic":"","id":"12","imgs":["http://192.168.0.196/house/Uploads/20161223/585c90f884c39.jpg","http://192.168.0.196/house/Uploads/20161223/585c91074884f.jpg","http://192.168.0.196/house/Uploads/20161223/585c911154748.jpg"],"nickname":"哈哈哈","person_sign":["作业反馈"],"quality_sign":["身心健康","实践能力"],"record_bf":"记录简介","type":"1","userid":"3","video":""},{"class_no":"466495","classid":"6","create_time":"2018-01-05 17:32:21","header_pic":"","id":"14","imgs":["http://192.168.0.196/house/Uploads/20170103/586b6a1391202.png"],"nickname":"汤文艳","person_sign":[],"quality_sign":["品德少年","热爱学习","体育活动"],"record_bf":"123","type":"1","userid":"1","video":"http://192.168.0.196/house"},{"class_no":"466495","classid":"6","create_time":"2018-01-05 17:35:59","header_pic":"","id":"15","imgs":["http://192.168.0.196/house/Uploads/20170103/586b6a1391202.png"],"nickname":"汤文艳","person_sign":["预习课程"],"quality_sign":[],"record_bf":"123","type":"1","userid":"1","video":"http://192.168.0.196/house"}]
     * code : 200
     * result : 获取成功
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
         * class_no : 466495
         * classid : 6
         * create_time : 2018-01-04 15:39:28
         * header_pic :
         * id : 1
         * imgs : []
         * nickname : 汤文艳
         * person_sign : ["劳动","作业反馈","预习课程"]
         * quality_sign : ["品德发展","修习课程"]
         * record_bf : 记录简介
         * type : 1
         * userid : 1
         * video : http://192.168.0.196/house/Uploads/Videos/5863334ed4bfd.mp4
         */

        private String class_no;
        private String classid;
        private String class_name;
        private String create_time;
        private String header_pic;
        private String id;
        private String share_url;
        private String nickname;
        private String record_bf;
        private String type;
        private String userid;
        private String video;
        private List<String> imgs;
        private List<String> person_sign;
        private List<String> quality_sign;

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getClass_no() {
            return class_no;
        }

        public void setClass_no(String class_no) {
            this.class_no = class_no;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRecord_bf() {
            return record_bf;
        }

        public void setRecord_bf(String record_bf) {
            this.record_bf = record_bf;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<String> getPerson_sign() {
            return person_sign;
        }

        public void setPerson_sign(List<String> person_sign) {
            this.person_sign = person_sign;
        }

        public List<String> getQuality_sign() {
            return quality_sign;
        }

        public void setQuality_sign(List<String> quality_sign) {
            this.quality_sign = quality_sign;
        }
    }
}
