package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ClassFileActivity;
import com.dlwx.wisdomschool.bean.ClassListBean;
import com.ruffian.library.RTextView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15/015.
 */

public class MeCreateCLassAdapter extends BaseFastAdapter {
    private List<ClassListBean.BodyBean> body;
    public MeCreateCLassAdapter(Context ctx, List<ClassListBean.BodyBean> body) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_create_class, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final ClassListBean.BodyBean bodyBean = body.get(position);
        Glide.with(ctx).load(bodyBean.getClass_pic()).apply(new RequestOptions().error(R.mipmap.icon_zhucetouxiang)).into(vh.iv_pic);
        vh.tv_classname.setText(bodyBean.getClass_name());
        vh.tv_classnumber.setText(bodyBean.getClass_no());
        vh.tv_classmember.setText(bodyBean.getTotal_user());

        vh.tv_file.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(ctx, ClassFileActivity.class);
                intent.putExtra("classid",bodyBean.getCnid());
                ctx.startActivity(intent);
            }
        });
//        if (position == 2) {
//            vh.ll_apply.setVisibility(View.VISIBLE);
//        }else{
//            vh.ll_apply.setVisibility(View.GONE);
//        }
//        vh.tv_mess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ctx.startActivity(new Intent(ctx, ClassHistoryNewsActivitry.class));
//            }
//        });
        return convertView;
    }

   private class ViewHolder {
        public View rootView;
        public TextView tv_classnumber;
        public TextView tv_classmember;
        public TextView  tv_classname;
        public RTextView tv_file;
        public RTextView tv_mess;
        public RTextView tv_invite;
        public LinearLayout ll_apply;
        public ImageView iv_pic;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_classnumber = (TextView) rootView.findViewById(R.id.tv_classnumber);
            this.tv_classmember = (TextView) rootView.findViewById(R.id.tv_classmember);
            this.tv_file = (RTextView) rootView.findViewById(R.id.tv_file);
            this.tv_mess = (RTextView) rootView.findViewById(R.id.tv_mess);
            this.tv_classname =  rootView.findViewById(R.id.tv_classname);
            this.tv_invite = (RTextView) rootView.findViewById(R.id.tv_invite);
            this.ll_apply = rootView.findViewById(R.id.ll_apply);
            this.iv_pic = rootView.findViewById(R.id.iv_pic);
        }

    }
}
