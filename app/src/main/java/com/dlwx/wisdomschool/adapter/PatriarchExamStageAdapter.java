package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/19/019.
 */

public class PatriarchExamStageAdapter extends BaseFastAdapter {
    public PatriarchExamStageAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 22;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_allsubject, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();

        }
        if (position <=19) {
            vh.tv_name.setText(position+"岁阶段考试");
        }else if (position == 20){
            vh.tv_name.setText("离婚考试");
        }else{
            vh.tv_name.setText("结婚考试");
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
