package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class PreinstallPicAdapter extends BaseFastAdapter {
    public PreinstallPicAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_pic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

   private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
        }

    }
}
