package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.SysMessBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20/020.
 */

public class SysyNotiAdapter extends BaseFastAdapter {
    private List<SysMessBean.BodyBean> body;

    public SysyNotiAdapter(Context ctx, List<SysMessBean.BodyBean> body) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_sysnoti, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();

        }
        SysMessBean.BodyBean bodyBean = body.get(position);
        vh.tv_titrle.setText(bodyBean.getTitle());
        vh.tv_content.setText(bodyBean.getInfo());
        vh.tv_date.setText(bodyBean.getCreatedtime());
        return convertView;
    }

   private class ViewHolder {
        public View rootView;
        public TextView tv_titrle;
        public TextView tv_date;
        public TextView tv_content;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_titrle = (TextView) rootView.findViewById(R.id.tv_titrle);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
        }

    }
}
