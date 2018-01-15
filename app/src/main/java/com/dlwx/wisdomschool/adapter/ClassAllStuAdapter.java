package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.GreadRankBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19/019.
 */

public class ClassAllStuAdapter extends BaseFastAdapter {
    private List<GreadRankBean.BodyBean> body;
    public ClassAllStuAdapter(Context ctx,List<GreadRankBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_allstu, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();

        }
            vh.tv_name.setText((position+1)+"、"+body.get(position).getUser_nickname());
        vh.tv_grade.setText(body.get(position).getScore()+"分");
        return convertView;
    }
    private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_grade;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_grade = (TextView) rootView.findViewById(R.id.tv_grade);
        }

    }
}
