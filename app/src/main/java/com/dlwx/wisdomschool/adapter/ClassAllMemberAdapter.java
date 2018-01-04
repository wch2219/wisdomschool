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
import com.dlwx.wisdomschool.bean.ClassDescBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22/022.
 */

public class ClassAllMemberAdapter extends BaseFastAdapter {
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    public ClassAllMemberAdapter(Context ctx, List<ClassDescBean.BodyBean.AddUserBean> add_user) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_pic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        ClassDescBean.BodyBean.AddUserBean addUserBean = add_user.get(position);
        Glide.with(ctx).load(addUserBean.getHeader_pic()).into(vh.iv_pic);
        vh.tv_name.setText(addUserBean.getJoin_role());
        vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
        return convertView;
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
