package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/19/019.
 */

public class SetTimeAdapter extends BaseFastAdapter {
    private String [] strs;
    public SetTimeAdapter(Context ctx,String [] strs) {
        super(ctx);
        this.strs = strs;
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_time, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_time.setText(strs[position]);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public CheckBox tv_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_time = (CheckBox) rootView.findViewById(R.id.tv_time);
        }
    }
}
