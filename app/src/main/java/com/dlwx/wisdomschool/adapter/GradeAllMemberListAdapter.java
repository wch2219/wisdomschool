package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.ClassDescBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26/026.
 */

public class GradeAllMemberListAdapter extends BaseFastAdapter {
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    public GradeAllMemberListAdapter(Context ctx,List<ClassDescBean.BodyBean.AddUserBean> add_user) {
        super(ctx);
        this.add_user = add_user;
    }

    @Override
    public int getCount() {
        return add_user.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_all_member, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText(add_user.get(position).getJoin_role());
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
