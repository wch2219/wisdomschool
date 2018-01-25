package com.dlwx.wisdomschool.bean;

/**
 * Created by Administrator on 2018/1/25/025.
 */

public class CardBean {


    /**
     * body : {"age":1,"dui_num":"2","enid":"2","nickname":"哈哈","total_score":20,"userid":"5","vip":"1","zhengshu_cardno":"7514524"}
     * code : 200
     * result : 操作成功
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
         * age : 1
         * dui_num : 2
         * enid : 2
         * nickname : 哈哈
         * total_score : 20
         * userid : 5
         * vip : 1
         * zhengshu_cardno : 7514524
         */

        private int age;
        private String dui_num;
        private String enid;
        private String nickname;
        private int total_score;
        private String userid;
        private String vip;
        private String zhengshu_cardno;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDui_num() {
            return dui_num;
        }

        public void setDui_num(String dui_num) {
            this.dui_num = dui_num;
        }

        public String getEnid() {
            return enid;
        }

        public void setEnid(String enid) {
            this.enid = enid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getTotal_score() {
            return total_score;
        }

        public void setTotal_score(int total_score) {
            this.total_score = total_score;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getZhengshu_cardno() {
            return zhengshu_cardno;
        }

        public void setZhengshu_cardno(String zhengshu_cardno) {
            this.zhengshu_cardno = zhengshu_cardno;
        }
    }
}
