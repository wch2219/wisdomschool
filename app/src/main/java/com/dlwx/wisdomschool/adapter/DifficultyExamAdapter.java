package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/25/025.
 */

public class DifficultyExamAdapter extends BaseFastAdapter {
    private int [] pics = {R.mipmap.icon_nandua,R.mipmap.icon_nandub,R.mipmap.icon_nanduc,R.mipmap.icon_nandud,R.mipmap.icon_nandue};
    private String name;
    public DifficultyExamAdapter(Context ctx,String name) {
        super(ctx);
        this.name = name;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_difficultyexam, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText(name);
        Glide.with(ctx).load(pics[position]).into(vh.iv_pic);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public ImageView iv_pic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
        }

    }
}
