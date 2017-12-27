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
 * Created by Administrator on 2017/12/22/022.
 */

public class ClassDescMemberAdapter extends BaseFastAdapter {

    public ClassDescMemberAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_pic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (position == 2) {
            Glide.with(ctx).load(R.mipmap.icon_bjxxtianji).into(vh.iv_pic);
            vh.tv_name.setText("添加成员");
            vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.garytext));
        }else{
            Glide.with(ctx).load(R.mipmap.ss33).into(vh.iv_pic);
            vh.tv_name.setText("高飞");
            vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
        }
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = rootView.findViewById(R.id.tv_name);
        }

    }
}
