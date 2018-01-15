package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.GradeYearBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class GradeRankitemAdapter extends BaseFastAdapter {
    private List<GradeYearBean.BodyBean> body;
    public GradeRankitemAdapter(Context ctx, List<GradeYearBean.BodyBean> body ) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_graderank, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        GradeYearBean.BodyBean bodyBean = body.get(position);
        vh.tv_name.setText("第"+bodyBean.getSort()+"次");
        vh.tv_data.setText(bodyBean.getDate());
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_data;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_data = (TextView) rootView.findViewById(R.id.tv_data);
        }

    }
}
