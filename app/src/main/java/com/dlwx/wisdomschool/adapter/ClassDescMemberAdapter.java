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

public class ClassDescMemberAdapter extends BaseFastAdapter {
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    public ClassDescMemberAdapter(Context ctx,List<ClassDescBean.BodyBean.AddUserBean> add_user) {
        super(ctx);
        this.add_user = add_user;
    }

    @Override
    public int getCount() {
            if (add_user.size()>3) {
                return 4;
            }else if (add_user.size()>0) {

                return add_user.size()+1;
            }else{
                return 1;
            }
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

        if (add_user.size() > 3) {
                if (position == 3) {
                    ClassDescBean.BodyBean.AddUserBean addUserBean = add_user.get(position);
                    Glide.with(ctx).load(R.mipmap.icon_bjxxtianji).into(vh.iv_pic);
                    vh.tv_name.setText("添加成员");
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.garytext));
                }else{
                    ClassDescBean.BodyBean.AddUserBean addUserBean = add_user.get(position);
                    Glide.with(ctx).load(addUserBean.getHeader_pic()).into(vh.iv_pic);
                    vh.tv_name.setText(addUserBean.getJoin_role());
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
                }
            }else if (add_user.size() == 0) {
                if (position == 0) {
                    Glide.with(ctx).load(R.mipmap.icon_bjxxtianji).into(vh.iv_pic);
                    vh.tv_name.setText("添加成员");
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.garytext));
                }
            }
            else{
                if (position == add_user.size()) {
                    Glide.with(ctx).load(R.mipmap.icon_bjxxtianji).into(vh.iv_pic);
                    vh.tv_name.setText("添加成员");
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.garytext));
                }else{
                    ClassDescBean.BodyBean.AddUserBean addUserBean = add_user.get(position);
                    Glide.with(ctx).load(addUserBean.getHeader_pic()).into(vh.iv_pic);
                    vh.tv_name.setText(addUserBean.getJoin_role());
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
                }
            }
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
