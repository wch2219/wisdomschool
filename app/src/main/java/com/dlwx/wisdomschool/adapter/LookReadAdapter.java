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
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.hyphenate.easeui.EaseConstant;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class LookReadAdapter extends BaseFastAdapter {
    private List<LookReadBean.BodyBean.AllInfoBean> all_info;
    private List<LookReadBean.BodyBean.Unread_info> unread_infos;
    private int type;
    public LookReadAdapter(Context ctx,List<LookReadBean.BodyBean.AllInfoBean> all_info) {
        super(ctx);
        this.all_info = all_info;
        type = 1;
    }
    public LookReadAdapter(Context ctx,List<LookReadBean.BodyBean.Unread_info> unread_infos,int type) {
        super(ctx);
        this.unread_infos = unread_infos;
        this.type = type;
    }
    @Override
    public int getCount() {
        if (type == 1) {

            return all_info.size();
        }else{
            return unread_infos.size();
        }
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
        if (type == 1) {
            final LookReadBean.BodyBean.AllInfoBean allInfoBean = all_info.get(position);
            Glide.with(ctx).load(allInfoBean.getHeader_pic()).apply(new RequestOptions().placeholder(R.mipmap.icon_zhucetouxiang).error(R.mipmap.icon_zhucetouxiang)).into(vh.iv_pic);
            vh.tv_name.setText(allInfoBean.getJoin_role());

            int is_yue = allInfoBean.getIs_yue();
            if (is_yue == 1) {
                vh.iv_remind.setVisibility(View.GONE);
            }else{
                vh.iv_remind.setVisibility(View.VISIBLE);
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
                    intent.putExtra("title",allInfoBean.getJoin_role());
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, allInfoBean.getUserid());
                    intent.putExtra(SpUtiles.OtherHeadPic,allInfoBean.getHeader_pic());
                    intent.putExtra(SpUtiles.OtherNickName,allInfoBean.getJoin_role());
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    ctx.startActivity(intent);
                }
            });
            vh.iv_remind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo
                    sendNotifitiListener.send(allInfoBean.getUserid());
                }
            });
        }else{
            final LookReadBean.BodyBean.Unread_info unread_info = unread_infos.get(position);
            Glide.with(ctx).load(unread_info.getHeader_pic()).apply(new RequestOptions().placeholder(R.mipmap.icon_zhucetouxiang).error(R.mipmap.icon_zhucetouxiang)).into(vh.iv_pic);
            vh.tv_name.setText(unread_info.getJoin_role());

            int is_yue = unread_info.getIs_yue();
            vh.iv_remind.setVisibility(View.VISIBLE);

            vh.iv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + unread_info.getTelephone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);
                }
            });
            vh.iv_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, ChatActivity.class);
                    intent.putExtra("title",unread_info.getJoin_role());
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, unread_info.getUserid());
                    intent.putExtra(SpUtiles.OtherHeadPic,unread_info.getHeader_pic());
                    intent.putExtra(SpUtiles.OtherNickName,unread_info.getJoin_role());
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    ctx.startActivity(intent);
                }
            });
            vh.iv_remind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo
                    sendNotifitiListener.send(unread_info.getUserid());
                }
            });
        }


        return convertView;
    }
    public interface SendNotifitiListener{
        void send(String parent_id);
    }
    private SendNotifitiListener sendNotifitiListener;

    public void setSendNotifitiListener(SendNotifitiListener sendNotifitiListener) {
        this.sendNotifitiListener = sendNotifitiListener;
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
