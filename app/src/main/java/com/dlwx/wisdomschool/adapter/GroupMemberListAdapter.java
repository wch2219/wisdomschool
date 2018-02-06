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
import com.dlwx.wisdomschool.bean.GroupMemberListBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2/002.
 */

public class GroupMemberListAdapter extends BaseFastAdapter {
    private List<GroupMemberListBean.BodyBean> body;
    private String userId;
    public GroupMemberListAdapter(Context ctx,List<GroupMemberListBean.BodyBean> body,String userId) {
        super(ctx);
        this.body = body;
        this.userId = userId;
    }

    @Override
    public int getCount() {
        String userid = body.get(0).getUserid();
        if (userid.equals(userId)) {//你是群主

            return body.size()+2;
        }else {

            return body.size();
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_group_member, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        String userid = body.get(0).getUserid();

        if (userid.equals(userId)) {//你是群主
            if (position == body.size()+1) {//删除成员
                Glide.with(ctx).load(R.mipmap.icon_bjxxtianjian).into(vh.iv_pic);
                vh.tv_name.setVisibility(View.GONE);
                vh.iv_delete.setVisibility(View.GONE);
            }else if(position == body.size()){//添加成员
                Glide.with(ctx).load(R.mipmap.icon_bjxxtianji).into(vh.iv_pic);
                vh.tv_name.setVisibility(View.GONE);
                vh.iv_delete.setVisibility(View.GONE);
            }else{
                if (isDelete && position != 0) {
                    vh.iv_delete.setVisibility(View.VISIBLE);
                }else{
                    vh.iv_delete.setVisibility(View.GONE);
                }
                GroupMemberListBean.BodyBean bodyBean = body.get(position);
                Glide.with(ctx).load(bodyBean.getHeader_pic()).into(vh.iv_pic);
                vh.tv_name.setVisibility(View.VISIBLE);
                vh.tv_name.setText(bodyBean.getNickname());
            }
        }else{
            GroupMemberListBean.BodyBean bodyBean = body.get(position);
            Glide.with(ctx).load(bodyBean.getHeader_pic()).into(vh.iv_pic);
            vh.tv_name.setText(bodyBean.getNickname());
            vh.iv_delete.setVisibility(View.GONE);
        }

        vh.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.delete(position);
            }
        });

        return convertView;
    }
    private boolean isDelete;
    public void setShowDelete(boolean isDelete){
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }
    private DeleteListener deleteListener;
    public interface DeleteListener{
        void delete(int position);
    }
    public void setDeleteListener(DeleteListener deleteListener){
        this.deleteListener = deleteListener;
    }
    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public ImageView iv_delete;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.iv_delete = rootView.findViewById(R.id.iv_delete);
        }

    }
}
