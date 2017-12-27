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
 * Created by Administrator on 2017/12/20/020.
 */

public class SharesAdapter extends BaseFastAdapter {
    private  String[] shares;
    private int[] pics = {
            R.mipmap.icon_fxpengyouquan,
            R.mipmap.icon_fxweixin,
            R.mipmap.icon_fxqq,
            R.mipmap.icon_fxkongjian,
            R.mipmap.icon_fxweibo,
            R.mipmap.icon_fxlianjie,
    };
    public SharesAdapter(Context ctx, String[] shares) {
        super(ctx);
        this.shares = shares;
    }

    @Override
    public int getCount() {
        return shares.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_shares, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText(shares[position]);
        Glide.with(ctx).load(pics[position]).into(vh.iv_pic);
        return convertView;
    }

   private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
