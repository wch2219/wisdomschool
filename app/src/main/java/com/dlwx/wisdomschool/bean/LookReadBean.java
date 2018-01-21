package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/21/021.
 */

public class LookReadBean {

    /**
     * code : 200
     * result : 获取成功
     * body : {"percentage":"100%","read_num":1,"unread_num":0,"all_info":[{"join_role":"哈哈爸爸","userid":"5","header_pic":"http://192.168.0.199/school/assets/default_user_photo.jpg","telephone":"13782254956","is_yue":1}]}
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
         * percentage : 100%
         * read_num : 1
         * unread_num : 0
         * all_info : [{"join_role":"哈哈爸爸","userid":"5","header_pic":"http://192.168.0.199/school/assets/default_user_photo.jpg","telephone":"13782254956","is_yue":1}]
         */

        private String percentage;
        private int read_num;
        private int unread_num;
        private List<AllInfoBean> all_info;

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public int getRead_num() {
            return read_num;
        }

        public void setRead_num(int read_num) {
            this.read_num = read_num;
        }

        public int getUnread_num() {
            return unread_num;
        }

        public void setUnread_num(int unread_num) {
            this.unread_num = unread_num;
        }

        public List<AllInfoBean> getAll_info() {
            return all_info;
        }

        public void setAll_info(List<AllInfoBean> all_info) {
            this.all_info = all_info;
        }

        public static class AllInfoBean {
            /**
             * join_role : 哈哈爸爸
             * userid : 5
             * header_pic : http://192.168.0.199/school/assets/default_user_photo.jpg
             * telephone : 13782254956
             * is_yue : 1
             */

            private String join_role;
            private String userid;
            private String header_pic;
            private String telephone;
            private int is_yue;

            public String getJoin_role() {
                return join_role;
            }

            public void setJoin_role(String join_role) {
                this.join_role = join_role;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getHeader_pic() {
                return header_pic;
            }

            public void setHeader_pic(String header_pic) {
                this.header_pic = header_pic;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public int getIs_yue() {
                return is_yue;
            }

            public void setIs_yue(int is_yue) {
                this.is_yue = is_yue;
            }
        }
    }
}
