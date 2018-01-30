package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.MyAllClassBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class RecordScreenAddAdapter extends BaseFastAdapter {
    private List<MyAllClassBean.BodyBean.JoinListBean> join_list;
    private int addNum;
    public RecordScreenAddAdapter(Context ctx,List<MyAllClassBean.BodyBean.JoinListBean> join_list,int addNum) {
        super(ctx);
        this.join_list = join_list;
        this.addNum = addNum;
    }

    @Override
    public int getCount() {
        return addNum;
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
        MyAllClassBean.BodyBean.JoinListBean joinListBean = join_list.get(position);
        Glide.with(ctx).load(joinListBean.getClass_pic()).into(vh.iv_pic);
        vh.tv_classnumber.setText(joinListBean.getClass_no());
        vh.tv_classnum.setText(joinListBean.getClass_name());
        vh.tv_classmember.setText(joinListBean.getTotal_user());
        vh.cb_check.setChecked(joinListBean.isCheck());
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_classnum;
        public TextView tv_classnumber;
        public TextView tv_classmember;
        public CheckBox cb_check;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_classnum = (TextView) rootView.findViewById(R.id.tv_classnum);
            this.tv_classnumber = (TextView) rootView.findViewById(R.id.tv_classnumber);
            this.tv_classmember = (TextView) rootView.findViewById(R.id.tv_classmember);
            this.cb_check = rootView.findViewById(R.id.cb_check);
        }

    }


}
