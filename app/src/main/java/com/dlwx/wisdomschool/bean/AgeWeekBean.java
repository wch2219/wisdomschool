package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/21/021.
 */

public class AgeWeekBean {

    /**
     * body : [{"amid":"3","createdtime":"2018-01-09","pl_num":"20000","sign":"教育大趋势","title":"国外五岁宝宝","title_pic":"http://192.168.0.196/house//Uploads/20161230/58663530f1064.png","url":"http://192.168.0.196/house/web/weekly_classify.html?age=1&token=abf900a7e445451f6afba8b2b436ab53","zan_num":"10000"}]
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
         * amid : 3
         * createdtime : 2018-01-09
         * pl_num : 20000
         * sign : 教育大趋势
         * title : 国外五岁宝宝
         * title_pic : http://192.168.0.196/house//Uploads/20161230/58663530f1064.png
         * url : http://192.168.0.196/house/web/weekly_classify.html?age=1&token=abf900a7e445451f6afba8b2b436ab53
         * zan_num : 10000
         */

        private String amid;
        private String createdtime;
        private String pl_num;
        private String sign;
        private String title;
        private String title_pic;
        private String url;
        private String zan_num;

        public String getAmid() {
            return amid;
        }

        public void setAmid(String amid) {
            this.amid = amid;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
        }

        public String getPl_num() {
            return pl_num;
        }

        public void setPl_num(String pl_num) {
            this.pl_num = pl_num;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_pic() {
            return title_pic;
        }

        public void setTitle_pic(String title_pic) {
            this.title_pic = title_pic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getZan_num() {
            return zan_num;
        }

        public void setZan_num(String zan_num) {
            this.zan_num = zan_num;
        }
    }
}
