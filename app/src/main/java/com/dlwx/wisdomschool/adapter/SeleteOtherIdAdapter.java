package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/26/026.
 */

public class SeleteOtherIdAdapter extends BaseFastAdapter {
    public SeleteOtherIdAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_seleteid, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            vh.tv_name.setBackgroundResource(R.color.blue);
        }else{
            vh.tv_name.setBackgroundResource(R.color.gary);
        }
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
