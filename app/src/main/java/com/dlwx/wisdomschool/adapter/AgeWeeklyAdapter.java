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
 * Created by Administrator on 2017/12/20/020.
 */

public class AgeWeeklyAdapter extends BaseFastAdapter {
    public AgeWeeklyAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_ageweek, null);
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
        public TextView tv_title;
        public TextView tv_data;
        public TextView tv_messnum;
        public TextView tv_praisenum;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_data = (TextView) rootView.findViewById(R.id.tv_data);
            this.tv_messnum = (TextView) rootView.findViewById(R.id.tv_messnum);
            this.tv_praisenum = (TextView) rootView.findViewById(R.id.tv_praisenum);
        }

    }
}
