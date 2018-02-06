package com.dlwx.wisdomschool.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2/002.
 */

public class GroupMemberListBean implements Serializable{

    /**
     * body : [{"eguid":"22","groupid":"39930725203972","header_pic":"http://39.107.74.235/school//Uploads/5a72702bc3db2x250.jpg","nickname":"小明","userid":"14"}]
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

    public static class BodyBean implements Serializable{
        /**
         * eguid : 22
         * groupid : 39930725203972
         * header_pic : http://39.107.74.235/school//Uploads/5a72702bc3db2x250.jpg
         * nickname : 小明
         * userid : 14
         */

        private String eguid;
        private String groupid;
        private String header_pic;
        private String nickname;
        private String userid;

        public String getEguid() {
            return eguid;
        }

        public void setEguid(String eguid) {
            this.eguid = eguid;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
