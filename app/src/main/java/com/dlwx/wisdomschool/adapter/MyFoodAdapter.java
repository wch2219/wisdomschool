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
 * Created by Administrator on 2017/12/19/019.
 */

public class MyFoodAdapter extends BaseFastAdapter {
    private  String[] foodarr;
    private int[] pics;
    public MyFoodAdapter(Context ctx, String[] foodarr,int[] pics) {
        super(ctx);
        this.foodarr = foodarr;
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return foodarr.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_my_food, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();

        }
        vh.iv_pic.setImageResource(pics[position]);
        vh.tv_name.setText(foodarr[position]);
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
