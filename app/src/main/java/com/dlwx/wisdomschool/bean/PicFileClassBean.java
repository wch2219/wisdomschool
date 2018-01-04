package com.dlwx.wisdomschool.bean;

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

    public static class Body{

    private String title;
    private List<PathBean> pathBeans;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PathBean> getPathBeans() {
        return pathBeans;
    }

    public void setPathBeans(List<PathBean> pathBeans) {
        this.pathBeans = pathBeans;
    }

    public static class PathBean{
            private String path;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
