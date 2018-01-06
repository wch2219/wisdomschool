package com.dlwx.baselib.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class PicFileClassBean {
    private List<Body> body;

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }

    public static class Body {

        private String title;
        private List<Image> pathBeans;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Image> getPathBeans() {
            return pathBeans;
        }

        public void setPathBeans(List<Image> pathBeans) {
            this.pathBeans = pathBeans;
        }

    }
}
