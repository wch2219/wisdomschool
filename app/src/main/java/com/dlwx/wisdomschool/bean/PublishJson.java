package com.dlwx.wisdomschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class PublishJson {

    private List<BodyBean> body;

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * name : 品德发展
         * colors : #D73B48
         * url : 你可以上传孩子自己的事情自己做、做家务、志愿者的照片
         这些都属于品德发展的表现哦
         */

        private String name;
        private String colors;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColors() {
            return colors;
        }

        public void setColors(String colors) {
            this.colors = colors;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
