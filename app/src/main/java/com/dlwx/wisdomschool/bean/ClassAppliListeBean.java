package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3/003.
 */

public class ClassAppliListeBean {

    /**
     * code : 200
     * result : 获取成功
     * body : [{"jcid":"5","userid":"2","join_role":"高飞妈妈","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg"},{"jcid":"8","userid":"1","join_role":"体育老师","header_pic":"http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg"}]
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
         * jcid : 5
         * userid : 2
         * join_role : 高飞妈妈
         * header_pic : http://192.168.0.199/school./assets/admin/layout/imgphoto2.jpg
         */

        private String jcid;
        private String userid;
        private String join_role;
        private String header_pic;
        private boolean check;
        public String getJcid() {
            return jcid;
        }

        public void setJcid(String jcid) {
            this.jcid = jcid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getJoin_role() {
            return join_role;
        }

        public void setJoin_role(String join_role) {
            this.join_role = join_role;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }
    }
}
