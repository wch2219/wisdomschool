package com.dlwx.baselib.bean;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public  class Image{
    private long date;
    private String path;
    public String name;
    private int oldposition;
    private boolean check;
    private String size;
    private int filetype;//图片，2音视频，3txt 4 doc
    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFiletype() {
        return filetype;
    }

    public void setFiletype(int filetype) {
        this.filetype = filetype;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getOldposition() {
        return oldposition;
    }

    public void setOldposition(int oldposition) {
        this.oldposition = oldposition;
    }
}