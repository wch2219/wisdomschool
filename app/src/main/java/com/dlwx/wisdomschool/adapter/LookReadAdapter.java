package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class LookReadAdapter extends BaseFastAdapter {
    public LookReadAdapter(Context ctx) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_lookread, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public CheckBox cb_check;
        public ImageView iv_pic;
        public ImageView iv_remind;
        public ImageView iv_send;
        public ImageView iv_phone;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.iv_remind = (ImageView) rootView.findViewById(R.id.iv_remind);
            this.iv_send = (ImageView) rootView.findViewById(R.id.iv_send);
            this.iv_phone = (ImageView) rootView.findViewById(R.id.iv_phone);
        }

    }
}
