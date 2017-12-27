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
 * Created by Administrator on 2017/12/18/018.
 */

public class ApplyListAdapter extends BaseFastAdapter {
    public ApplyListAdapter(Context ctx) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_apply, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        if (pos == position) {
            Glide.with(ctx).load(R.mipmap.ss33).into(vh.iv_check);
        }else{
            Glide.with(ctx).load(R.mipmap.ic_launcher).into(vh.iv_check);
        }
        return convertView;
    }
    private int pos;
    public void setCheck(int i) {
        this.pos = i;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_check;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_lose;
        public TextView tv_aggress;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_check = (ImageView) rootView.findViewById(R.id.iv_check);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_lose = (TextView) rootView.findViewById(R.id.tv_lose);
            this.tv_aggress = (TextView) rootView.findViewById(R.id.tv_aggress);
        }

    }
}
