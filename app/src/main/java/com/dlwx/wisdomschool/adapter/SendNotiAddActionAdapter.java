package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/26/026.
 */

public class SendNotiAddActionAdapter extends BaseFastAdapter {
    public SendNotiAddActionAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_sendnotiaddaction, null);
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
        public EditText et_title;
        public ImageView iv_close;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.et_title = (EditText) rootView.findViewById(R.id.et_title);
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
        }

    }
}
