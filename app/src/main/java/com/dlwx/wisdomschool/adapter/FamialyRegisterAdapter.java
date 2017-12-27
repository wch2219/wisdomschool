package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/22/022.
 */

public class FamialyRegisterAdapter extends BaseFastAdapter {
    public FamialyRegisterAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_famialyregister, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.gv_list.setAdapter(new FamialyRegisterItemAdpter(ctx));
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_title;
        public MyGridView gv_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.gv_list = (MyGridView) rootView.findViewById(R.id.gv_list);
        }

    }
}
