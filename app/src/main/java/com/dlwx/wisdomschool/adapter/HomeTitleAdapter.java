package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/15/015.
 */

public class HomeTitleAdapter extends BaseFastAdapter {
    private String[] strs;
    private  int [] pics;
    public HomeTitleAdapter(Context ctx, String[] strs, int [] pics) {
        super(ctx);
        this.strs = strs;
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {

            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_hometitle, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();

        }
        vh.tv_name.setText(strs[position]);
        vh.iv_pic.setImageResource(pics[position]);
        return convertView;
    }


    private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public ImageView iv_pic;

        public ViewHolder(View rootView) {

            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.iv_pic = rootView.findViewById(R.id.iv_pic);
        }

    }
}
