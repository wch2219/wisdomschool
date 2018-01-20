package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.ActionTitleBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class SendNotiSeleteAdapter extends BaseFastAdapter {
    private List<ActionTitleBean.BodyBean> actionTitles;
    public SendNotiSeleteAdapter(Context ctx,List<ActionTitleBean.BodyBean> actionTitles) {
        super(ctx);
        this.actionTitles = actionTitles;
    }

    @Override
    public int getCount() {
        return actionTitles.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_seletelist, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv_name.setText(actionTitles.get(position).getActivity_type());
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
