package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ChatActivity;
import com.dlwx.wisdomschool.bean.LookReadBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class LookReadAdapter extends BaseFastAdapter {
    private List<LookReadBean.BodyBean.AllInfoBean> all_info;
    public LookReadAdapter(Context ctx,List<LookReadBean.BodyBean.AllInfoBean> all_info) {
        super(ctx);
        this.all_info = all_info;
    }

    @Override
    public int getCount() {
        return all_info.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_lookread, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final LookReadBean.BodyBean.AllInfoBean allInfoBean = all_info.get(position);
        Glide.with(ctx).load(allInfoBean.getHeader_pic()).apply(new RequestOptions().placeholder(R.mipmap.icon_zhucetouxiang).error(R.mipmap.icon_zhucetouxiang)).into(vh.iv_pic);
        vh.tv_name.setText(allInfoBean.getJoin_role());

        int is_yue = allInfoBean.getIs_yue();
        if ("1".equals(is_yue)) {
            Glide.with(ctx).load(R.mipmap.icon_yanjing).into(vh.iv_unlook);
        }else{
            Glide.with(ctx).load(R.mipmap.icon_baiban).into(vh.iv_unlook);
        }

        vh.iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + allInfoBean.getTelephone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });
        vh.iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ChatActivity.class);
                ctx.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_unlook;
        public ImageView iv_pic;
        public ImageView iv_remind;
        public ImageView iv_send;
        public ImageView iv_phone;
        public TextView tv_name;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_unlook = (ImageView) rootView.findViewById(R.id.iv_unlook);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.iv_remind = (ImageView) rootView.findViewById(R.id.iv_remind);
            this.iv_send = (ImageView) rootView.findViewById(R.id.iv_send);
            this.iv_phone = (ImageView) rootView.findViewById(R.id.iv_phone);
            this.tv_name = rootView.findViewById(R.id.tv_name);
        }

    }
}
