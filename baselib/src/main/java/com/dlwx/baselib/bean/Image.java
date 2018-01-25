package com.dlwx.baselib.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public  class Image implements Parcelable ,Comparable<Image>{
    private long date;
    private String path;
    public String name;
    private int oldposition;
    private boolean check;
    private String size;
    private int filetype;//图片，2音视频，3txt 4 doc
    private long duration;
    public Image(){}
    public Image(Parcel in) {
        date = in.readLong();
        path = in.readString();
        name = in.readString();
        oldposition = in.readInt();
        check = in.readByte() != 0;
        size = in.readString();
        filetype = in.readInt();
        duration = in.readLong();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date);
        dest.writeString(path);
        dest.writeString(name);
        dest.writeInt(oldposition);
        dest.writeByte((byte) (check ? 1 : 0));
        dest.writeString(size);
        dest.writeInt(filetype);
        dest.writeLong(duration);
    }

    @Override
    public int compareTo(@NonNull Image o) {
        int num = Integer.valueOf((int) this.date).compareTo((int)o.date);//先比较年龄
        if (num == 0) {
            return this.name.compareTo(o.name);//如果年龄相同，再比较姓名（姓名按Unicode编码升序排序）
        }
        return num;
    }
}