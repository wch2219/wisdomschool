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

public class ClassDescTeachAdapter extends BaseFastAdapter {
    private ClassDescBean.BodyBean.CreateTeacherBean create_teacher;
    private List<ClassDescBean.BodyBean.AddTeacherBean> add_teacher;

    public ClassDescTeachAdapter(Context ctx, ClassDescBean.BodyBean.CreateTeacherBean create_teacher,
                                 List<ClassDescBean.BodyBean.AddTeacherBean> add_teacher
    ) {
        super(ctx);
        this.create_teacher = create_teacher;
        this.add_teacher = add_teacher;
    }

    @Override
    public int getCount() {
        if (create_teacher != null) {
            if (add_teacher != null) {
                if (add_teacher.size() > 2) {
                    return 4;
                }
            }

            return add_teacher.size() + 2;

        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_pic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (create_teacher != null) {
            if (add_teacher.size() > 2) {
                if (position == 3) {
                    Glide.with(ctx).load(R.mipmap.icon_bjxxtianji).into(vh.iv_pic);
                    vh.tv_name.setText("添加教师");
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.garytext));
                } else if (position == 0) {
                    Glide.with(ctx).load(create_teacher.getHeader_pic()).into(vh.iv_pic);
                    vh.tv_name.setText(create_teacher.getNickname());
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
                } else {
                    ClassDescBean.BodyBean.AddTeacherBean addTeacherBean = add_teacher.get(position);
                    Glide.with(ctx).load(addTeacherBean.getHeader_pic()).into(vh.iv_pic);
                    vh.tv_name.setText(addTeacherBean.getJoin_role());
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
                }
            }else{
                if (position == add_teacher.size() + 1) {
                    Glide.with(ctx).load(R.mipmap.icon_bjxxtianji).into(vh.iv_pic);
                    vh.tv_name.setText("添加教师");
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.garytext));
                } else if (position == 0) {
                    Glide.with(ctx).load(create_teacher.getHeader_pic()).into(vh.iv_pic);
                    vh.tv_name.setText(create_teacher.getNickname());
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
                } else {
                    ClassDescBean.BodyBean.AddTeacherBean addTeacherBean = add_teacher.get(position);
                    Glide.with(ctx).load(addTeacherBean.getHeader_pic()).into(vh.iv_pic);
                    vh.tv_name.setText(addTeacherBean.getJoin_role());
                    vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
                }
            }
        } else {
            Glide.with(ctx).load(create_teacher.getHeader_pic()).into(vh.iv_pic);
            vh.tv_name.setText(create_teacher.getNickname());
            vh.tv_name.setTextColor(ctx.getResources().getColor(R.color.black));
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
