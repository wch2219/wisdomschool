package com.dlwx.baselib.utiles;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24/024.
 */

public class ImgesLisetBean {

        private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public static class Image{
            private long date;
            private String path;
            private boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
}
