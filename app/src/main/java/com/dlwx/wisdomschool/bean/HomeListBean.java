package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24/024.
 */

public class HomelistBean {


    /**
     * code : 200
     * result : 获取成功
     * body : [{"id":"1","title":"视频标题","pic":"","video":"http://39.107.74.235/school//Uploads/Videos/20180123/5a66e77181c6b.mp4","view_num":"0","createdtime":"2018/01/23 10:47","is_article":"1"},{"id":"2","title":"图文标题","pic":"http://39.107.74.235/school//Uploads/5a66de2b0418fx250.png","video":"","view_num":"0","createdtime":"2018/01/23 10:50","is_article":"1"},{"id":"1","title":"二胎时代，想一碗水端平到底有多难？9大难题及解决之道，二宝家庭比看！","pic":"http://39.107.74.235/school//Uploads/5a658377e91dex250.png","video":"二宝养育经","view_num":"146","createdtime":"2018/01/08 17:10","is_article":"2"},{"id":"3","title":"一个月后期末考，这份语数外复习攻略，让孩子每科至少涨10分~","pic":"http://39.107.74.235/school//Uploads/5a66e7a1e14b7x250.png","video":"备战期末考","view_num":"4","createdtime":"2018/01/08 17:38","is_article":"2"}]
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
         * title : 视频标题
         * pic :
         * video : http://39.107.74.235/school//Uploads/Videos/20180123/5a66e77181c6b.mp4
         * view_num : 0
         * createdtime : 2018/01/23 10:47
         * is_article : 1
         */

        private String id;
        private String title;
        private String pic;
        private String video;
        private String view_num;
        private String createdtime;
        private String is_article;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getView_num() {
            return view_num;
        }

        public void setView_num(String view_num) {
            this.view_num = view_num;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }

        public String getIs_article() {
            return is_article;
        }

        public void setIs_article(String is_article) {
            this.is_article = is_article;
        }
    }
}
