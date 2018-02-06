package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/6/006.
 */

public class SysMessBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"id":"77","userid":"15","title":"系统提醒","info":"恭喜您！小明老师已经通过了您的申请，您现在已经是【三年二班】班的一员啦！","createdtime":"2018-02-06 15:18:21"},{"id":"74","userid":"15","title":"系统提醒","info":"恭喜您！小明老师已经通过了您的申请，您现在已经是【熊孩子】班的一员啦！","createdtime":"2018-02-06 15:10:07"},{"id":"73","userid":"15","title":"系统提醒","info":"您的班级【哈哈哈哈】:有新的入班申请，请及时处理","createdtime":"2018-02-06 15:08:20"},{"id":"71","userid":"15","title":"系统提醒","info":"恭喜您！小明老师已经通过了您的申请，您现在已经是【三年二班】班的一员啦！","createdtime":"2018-02-06 15:05:57"},{"id":"57","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 13:43:46"},{"id":"50","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:42:01"},{"id":"49","userid":"15","title":"系统提醒","info":"您的班级【哈哈哈哈】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:41:22"},{"id":"46","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:32:00"},{"id":"44","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:27:04"},{"id":"43","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:24:41"},{"id":"42","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:24:16"},{"id":"41","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:23:43"},{"id":"40","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:15:34"},{"id":"39","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:09:42"},{"id":"37","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 11:08:05"},{"id":"26","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:23:55"},{"id":"21","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:19:38"},{"id":"18","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:17:50"},{"id":"17","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:16:45"},{"id":"14","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:15:12"},{"id":"12","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:13:19"},{"id":"11","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:10:55"},{"id":"10","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:09:39"},{"id":"8","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:07:06"},{"id":"7","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:06:50"},{"id":"6","userid":"15","title":"系统提醒","info":"您的班级【五年级一班】:有新的入班申请，请及时处理","createdtime":"2018-02-06 10:05:43"}]
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
         * id : 77
         * userid : 15
         * title : 系统提醒
         * info : 恭喜您！小明老师已经通过了您的申请，您现在已经是【三年二班】班的一员啦！
         * createdtime : 2018-02-06 15:18:21
         */

        private String id;
        private String userid;
        private String title;
        private String info;
        private String createdtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }
    }
}
