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
 * Created by Administrator on 2017/12/23/023.
 */

public class RecordScreenPrivateAdapter extends BaseFastAdapter {
    public RecordScreenPrivateAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_recordscreen, null);
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
        public TextView tv_classnum;
        public TextView tv_classnumber;
        public TextView tv_classmember;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_classnum = (TextView) rootView.findViewById(R.id.tv_classnum);
            this.tv_classnumber = (TextView) rootView.findViewById(R.id.tv_classnumber);
            this.tv_classmember = (TextView) rootView.findViewById(R.id.tv_classmember);
        }

    }
}
