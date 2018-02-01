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
import com.dlwx.wisdomschool.bean.GroupList;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18/018.
 */

public class GroupChatListAdapter extends BaseFastAdapter {
    private List<GroupList.BodyBean> body;
    public GroupChatListAdapter(Context ctx,List<GroupList.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolderChild viewHolderChild;
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_chatgrouplistchild, null);
            viewHolderChild = new ViewHolderChild(view);
            view.setTag(viewHolderChild);
        }else{
            viewHolderChild = (ViewHolderChild) view.getTag();
        }

        GroupList.BodyBean bodyBean = body.get(position);
        viewHolderChild.tv_name.setText(bodyBean.getGroup_name());
            viewHolderChild.tv_num.setText("("+bodyBean.getGroupid()+")");
        Glide.with(ctx).load(bodyBean.getImgurl()).into(viewHolderChild.iv_pic);

        return view;
    }
   private class ViewHolderChild {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_num;

        public ViewHolderChild(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
        }

    }
}