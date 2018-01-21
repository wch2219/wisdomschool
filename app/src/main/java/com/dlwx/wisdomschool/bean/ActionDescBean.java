package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/20/020.
 */

public class ActionDescBean {

    /**
     * body : {"class_name":"阿坝","classid":"8","content":"就咯喔ee_11","content_pic":["http://192.168.0.199/school//Uploads/5a62de7e7e1f9x250.png","http://192.168.0.199/school//Uploads/5a62de7ede34cx250.png","http://192.168.0.199/school//Uploads/5a62de7f4eeaax250.jpg","http://192.168.0.199/school//Uploads/5a62de80293f1x250.jpg"],"content_voice":"http://192.168.0.199/school//Uploads/Videos/5a62dfbb7b803.amr","createdtime":"2018.01.20 14:20","header_pic":"http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg","id":"9","is_del":"2","is_end":2,"nickname":"只是","pinglun":[{"content_img":["http://192.168.0.199/school//Uploads/5a6338c01dcd6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c0ce3b6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c192268x250.jpg","http://192.168.0.199/school//Uploads/5a6338c258370x250.jpg","http://192.168.0.199/school//Uploads/5a6338c3040d9x250.jpg"],"header_pic":"http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg","icid":"18","irid":"9","is_dianzan":1,"nickname":"只是","pinglun":[],"pl_content":"快来默默ee_11ee_13ee_14","pl_time":"2018.01.20 20:42","userid":"3","zan_num":"1"},{"content_img":["http://192.168.0.199/school//Uploads/5a6338c01dcd6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c0ce3b6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c192268x250.jpg","http://192.168.0.199/school//Uploads/5a6338c258370x250.jpg","http://192.168.0.199/school//Uploads/5a6338c3040d9x250.jpg"],"header_pic":"http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg","icid":"17","irid":"9","is_dianzan":2,"nickname":"只是","pinglun":[{"nickname":"只是","pingjia":"咯磨牙ee_18ee_12ee_7ee_14","star_num":"0"}],"pl_content":"快来默默ee_11ee_13ee_14","pl_time":"2018.01.20 20:40","userid":"3","zan_num":"1"}],"theme":"5","theme_endtime":"","theme_name":"活动收集","user_read":0,"userid":"3"}
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
         * class_name : 阿坝
         * classid : 8
         * content : 就咯喔ee_11
         * content_pic : ["http://192.168.0.199/school//Uploads/5a62de7e7e1f9x250.png","http://192.168.0.199/school//Uploads/5a62de7ede34cx250.png","http://192.168.0.199/school//Uploads/5a62de7f4eeaax250.jpg","http://192.168.0.199/school//Uploads/5a62de80293f1x250.jpg"]
         * content_voice : http://192.168.0.199/school//Uploads/Videos/5a62dfbb7b803.amr
         * createdtime : 2018.01.20 14:20
         * header_pic : http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg
         * id : 9
         * is_del : 2
         * is_end : 2
         * nickname : 只是
         * pinglun : [{"content_img":["http://192.168.0.199/school//Uploads/5a6338c01dcd6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c0ce3b6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c192268x250.jpg","http://192.168.0.199/school//Uploads/5a6338c258370x250.jpg","http://192.168.0.199/school//Uploads/5a6338c3040d9x250.jpg"],"header_pic":"http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg","icid":"18","irid":"9","is_dianzan":1,"nickname":"只是","pinglun":[],"pl_content":"快来默默ee_11ee_13ee_14","pl_time":"2018.01.20 20:42","userid":"3","zan_num":"1"},{"content_img":["http://192.168.0.199/school//Uploads/5a6338c01dcd6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c0ce3b6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c192268x250.jpg","http://192.168.0.199/school//Uploads/5a6338c258370x250.jpg","http://192.168.0.199/school//Uploads/5a6338c3040d9x250.jpg"],"header_pic":"http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg","icid":"17","irid":"9","is_dianzan":2,"nickname":"只是","pinglun":[{"nickname":"只是","pingjia":"咯磨牙ee_18ee_12ee_7ee_14","star_num":"0"}],"pl_content":"快来默默ee_11ee_13ee_14","pl_time":"2018.01.20 20:40","userid":"3","zan_num":"1"}]
         * theme : 5
         * theme_endtime :
         * theme_name : 活动收集
         * user_read : 0
         * userid : 3
         */

        private String class_name;
        private String classid;
        private String content;
        private String content_voice;
        private String createdtime;
        private String header_pic;
        private String id;
        private String is_del;
        private int is_end;
        private String nickname;
        private String theme;
        private String theme_endtime;
        private String theme_name;
        private int user_read;
        private String userid;
        private List<String> content_pic;
        private List<PinglunBean> pinglun;

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent_voice() {
            return content_voice;
        }

        public void setContent_voice(String content_voice) {
            this.content_voice = content_voice;
        }

        public String getCreatedtime() {
            return createdtime;
        }

        public void setCreatedtime(String createdtime) {
            this.createdtime = createdtime;
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

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public int getIs_end() {
            return is_end;
        }

        public void setIs_end(int is_end) {
            this.is_end = is_end;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getTheme_endtime() {
            return theme_endtime;
        }

        public void setTheme_endtime(String theme_endtime) {
            this.theme_endtime = theme_endtime;
        }

        public String getTheme_name() {
            return theme_name;
        }

        public void setTheme_name(String theme_name) {
            this.theme_name = theme_name;
        }

        public int getUser_read() {
            return user_read;
        }

        public void setUser_read(int user_read) {
            this.user_read = user_read;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public List<String> getContent_pic() {
            return content_pic;
        }

        public void setContent_pic(List<String> content_pic) {
            this.content_pic = content_pic;
        }

        public List<PinglunBean> getPinglun() {
            return pinglun;
        }

        public void setPinglun(List<PinglunBean> pinglun) {
            this.pinglun = pinglun;
        }

        public static class PinglunBean {
            /**
             * content_img : ["http://192.168.0.199/school//Uploads/5a6338c01dcd6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c0ce3b6x250.jpg","http://192.168.0.199/school//Uploads/5a6338c192268x250.jpg","http://192.168.0.199/school//Uploads/5a6338c258370x250.jpg","http://192.168.0.199/school//Uploads/5a6338c3040d9x250.jpg"]
             * header_pic : http://192.168.0.199/school//Uploads/5a571b612756cx250.jpg
             * icid : 18
             * irid : 9
             * is_dianzan : 1
             * nickname : 只是
             * pinglun : []
             * pl_content : 快来默默ee_11ee_13ee_14
             * pl_time : 2018.01.20 20:42
             * userid : 3
             * zan_num : 1
             */

            private String header_pic;
            private String icid;
            private String irid;
            private int is_dianzan;
            private String nickname;
            private String pl_content;
            private String pl_time;
            private String userid;
            private int zan_num;
            private List<String> content_img;
            private List<PinglunContetBean> pinglun;

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getIcid() {
                return icid;
            }

            public void setIcid(String icid) {
                this.icid = icid;
            }

            public String getIrid() {
                return irid;
            }

            public void setIrid(String irid) {
                this.irid = irid;
            }

            public int getIs_dianzan() {
                return is_dianzan;
            }

            public void setIs_dianzan(int is_dianzan) {
                this.is_dianzan = is_dianzan;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPl_content() {
                return pl_content;
            }

            public void setPl_content(String pl_content) {
                this.pl_content = pl_content;
            }

            public String getPl_time() {
                return pl_time;
            }

            public void setPl_time(String pl_time) {
                this.pl_time = pl_time;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public int getZan_num() {
                return zan_num;
            }

            public void setZan_num(int zan_num) {
                this.zan_num = zan_num;
            }

            public List<String> getContent_img() {
                return content_img;
            }

            public void setContent_img(List<String> content_img) {
                this.content_img = content_img;
            }

            public List<PinglunContetBean> getPinglun() {
                return pinglun;
            }

            public void setPinglun(List<PinglunContetBean> pinglun) {
                this.pinglun = pinglun;
            }

            public static class PinglunContetBean{
                    private String nickname;
                    private String pingjia;
                    private int star_num;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getPingjia() {
                    return pingjia;
                }

                public void setPingjia(String pingjia) {
                    this.pingjia = pingjia;
                }

                public int getStar_num() {
                    return star_num;
                }

                public void setStar_num(int star_num) {
                    this.star_num = star_num;
                }
            }
        }
    }
}
