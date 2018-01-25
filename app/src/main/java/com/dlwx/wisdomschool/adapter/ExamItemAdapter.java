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
import com.dlwx.wisdomschool.bean.TestSelteBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25/025.
 */

public class ExamItemAdapter extends BaseFastAdapter {
    private  List<TestSelteBean> selteBeans;
    private int [] seleteitem = {0,1,2,3};
    public ExamItemAdapter(Context ctx, List<TestSelteBean> selteBeans) {
        super(ctx);
        this.selteBeans = selteBeans;
    }

    @Override
    public int getCount() {
        return selteBeans.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_exam_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        TestSelteBean selteBean = selteBeans.get(position);
        vh.tv_name.setText(selteBean.getName());
        if (selteBean.isIsselete()) {
            Glide.with(ctx).load(R.mipmap.icon_kaoshixuan).into(vh.iv_pic);
        }else{
            Glide.with(ctx).load(R.mipmap.icon_kaoshiwei).into(vh.iv_pic);
        }
        return convertView;
    }

    private int pos = -1;

    public void setposition(int i) {
        this.pos = i;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = rootView.findViewById(R.id.tv_name);
        }

    }
}
