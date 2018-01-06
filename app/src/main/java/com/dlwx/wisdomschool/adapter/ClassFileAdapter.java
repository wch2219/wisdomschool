package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.ClassFileBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22/022.
 */

public class ClassFileAdapter extends BaseFastAdapter {
    private List<ClassFileBean.BodyBean.ListBean> list;
    public ClassFileAdapter(Context ctx,List<ClassFileBean.BodyBean.ListBean> list) {
        super(ctx);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_classfile_group, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        ClassFileBean.BodyBean.ListBean listBean = list.get(position);
        int type = listBean.getType();
        if (type == 1) {//图片
            vh.ll_file.setVisibility(View.VISIBLE);
            vh.tv_classname.setVisibility(View.GONE);
            Glide.with(ctx).load(listBean.getFile_pic()).into(vh.iv_pic);
            vh.tv_filename.setText(listBean.getName());
            vh.tv_filesize.setText(listBean.getSize()+"KB");
            vh.tv_date.setText(listBean.getTime());
            vh.tv_filename.setText(listBean.getName());
        }else if (type == 2) {//音频
            vh.ll_file.setVisibility(View.VISIBLE);
            vh.tv_classname.setVisibility(View.GONE);
            Glide.with(ctx).load(R.mipmap.icon_viceo).into(vh.iv_pic);
            vh.tv_filesize.setText(listBean.getSize()+"KB");
            vh.tv_date.setText(listBean.getTime());
            vh.tv_filename.setText(listBean.getName());
        }else if (type == 3) {//txt
            vh.ll_file.setVisibility(View.VISIBLE);
            vh.tv_classname.setVisibility(View.GONE);
            Glide.with(ctx).load(R.mipmap.icon_txt).into(vh.iv_pic);
            vh.tv_filesize.setText(listBean.getSize()+"KB");
            vh.tv_date.setText(listBean.getTime());
            vh.tv_filename.setText(listBean.getName());
        }else if (type == 4) {//doc
            vh.ll_file.setVisibility(View.VISIBLE);
            vh.tv_classname.setVisibility(View.GONE);
            Glide.with(ctx).load(R.mipmap.icon_word).into(vh.iv_pic);
            vh.tv_filesize.setText(listBean.getSize()+"KB");
            vh.tv_date.setText(listBean.getTime());
            vh.tv_filename.setText(listBean.getName());
        } else {//文件夹
            vh.ll_file.setVisibility(View.GONE);
            vh.tv_classname.setVisibility(View.VISIBLE);
            Glide.with(ctx).load(R.mipmap.icon_cjbjwenjian).into(vh.iv_pic);
            vh.tv_classname.setText(listBean.getName());
        }
        return convertView;
    }
    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_classname;
        public TextView tv_filename;
        public TextView tv_filesize;
        public TextView tv_date;
        public LinearLayout ll_file;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_classname = (TextView) rootView.findViewById(R.id.tv_classname);
            this.tv_filename = (TextView) rootView.findViewById(R.id.tv_filename);
            this.tv_filesize = (TextView) rootView.findViewById(R.id.tv_filesize);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.ll_file = (LinearLayout) rootView.findViewById(R.id.ll_file);
        }

    }
}
