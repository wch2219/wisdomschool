package com.dlwx.wisdomschool.bean;

import java.util.List;

public class HomelistBean {


    /**
     * body : {"banner":[{"banner_id":"12","imgid":"http://39.107.74.235/school//Uploads/5a6ef378b6db5x250.png"},{"banner_id":"13","imgid":"http://39.107.74.235/school//Uploads/5a6ef3826e3c5x250.png"},{"banner_id":"14","imgid":"http://39.107.74.235/school//Uploads/5a6ef39134972x250.png"}],"list":[{"amid":"70","createdtime":"2018-01-30","pl_num":"0","sign":"智慧家校","title":"积极参加\u2014成人礼","title_pic":"http://39.107.74.235/school//Uploads/5a6fcbbca5574x250.jpg","url":"","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=70","video":"","zan_num":"0"},{"amid":"68","createdtime":"2018-01-30","pl_num":"0","sign":"智慧家校","title":"打造舒适的\u201c物理环境\u201d","title_pic":"http://39.107.74.235/school//Uploads/5a6fbf473d213x250.jpg","url":"","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=68","video":"","zan_num":"0"},{"amid":"67","createdtime":"2018-01-30","pl_num":"0","sign":"智慧家校","title":"做\u201c称职\u201d的父母","title_pic":"http://39.107.74.235/school//Uploads/5a6eee7138e1ex250.jpg","url":"","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=67","video":"","zan_num":"0"},{"amid":"66","createdtime":"2018-01-29","pl_num":"0","sign":"智慧家校","title":"有迹可循的动作发展","title_pic":"http://39.107.74.235/school//Uploads/5a6fb9ce13767x250.jpg","url":"","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=66","video":"","zan_num":"0"},{"amid":"64","createdtime":"2018-01-29","pl_num":"0","sign":"智慧家校","title":"认识0-1岁的孩子","title_pic":"http://39.107.74.235/school//Uploads/5a6ee54ea3c81x250.jpg","url":"","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=64","video":"","zan_num":"0"},{"amid":"62","createdtime":"2018-01-29","pl_num":"0","sign":"智慧家校","title":"怎样与孩子一起直面高考失利?","title_pic":"http://39.107.74.235/school//Uploads/5a6ecb3836b93x250.jpg","url":"","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=62","video":"","zan_num":"0"},{"amid":"59","createdtime":"2018-01-26","pl_num":"0","sign":"智慧家校","title":"建立和谐的亲子关系","title_pic":"http://39.107.74.235/school//Uploads/5a6ae785baa0ex250.jpg","url":"","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=59","video":"","zan_num":"0"},{"amid":"53","createdtime":"2018-01-26","pl_num":"0","sign":"智慧家校","title":"如何让别人与你合作","title_pic":"http://39.107.74.235/school//Uploads/5a6ae3a080b57x250.jpg","url":"http://39.107.74.235/school/web/weekly_classify.html?age=&token=a329886ae26e4d0966b625f23b810c3e","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=53","video":"","zan_num":"1"},{"amid":"51","createdtime":"2018-01-26","pl_num":"0","sign":"智慧家校","title":"尽量多地给对方说话的机会","title_pic":"http://39.107.74.235/school//Uploads/5a6ae02e3e9cex250.jpg","url":"http://39.107.74.235/school/web/weekly_classify.html?age=&token=a329886ae26e4d0966b625f23b810c3e","url_detail":"http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=51","video":"","zan_num":"0"}]}
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
        private List<BannerBean> banner;
        private List<ListBean> list;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class BannerBean {
            /**
             * banner_id : 12
             * imgid : http://39.107.74.235/school//Uploads/5a6ef378b6db5x250.png
             */

            private String banner_id;
            private String imgid;

            public String getBanner_id() {
                return banner_id;
            }

            public void setBanner_id(String banner_id) {
                this.banner_id = banner_id;
            }

            public String getImgid() {
                return imgid;
            }

            public void setImgid(String imgid) {
                this.imgid = imgid;
            }
        }

        public static class ListBean {
            /**
             * amid : 70
             * createdtime : 2018-01-30
             * pl_num : 0
             * sign : 智慧家校
             * title : 积极参加—成人礼
             * title_pic : http://39.107.74.235/school//Uploads/5a6fcbbca5574x250.jpg
             * url :
             * url_detail : http://39.107.74.235/school/web/weekly_age_details.html?token=&amid=70
             * video :
             * zan_num : 0
             */

            private String amid;
            private String createdtime;
            private String pl_num;
            private String sign;
            private String title;
            private String title_pic;
            private String url;
            private String url_detail;
            private String video;
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

            public String getUrl_detail() {
                return url_detail;
            }

            public void setUrl_detail(String url_detail) {
                this.url_detail = url_detail;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getZan_num() {
                return zan_num;
            }

            public void setZan_num(String zan_num) {
                this.zan_num = zan_num;
            }
        }
    }
}
