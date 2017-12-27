package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/27/027.
 */

public class ChatGroupListAdapter extends BaseExpandableListAdapter {
    private Context ctx;

    public ChatGroupListAdapter(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int i) {
        return 3;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderGroup viewHolderGroup;
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_chatgrouplistgroup, null);
            viewHolderGroup = new ViewHolderGroup(view);
            view.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) view.getTag();
        }
        if (i == 0) {
            viewHolderGroup.tv_name.setText("我创建的群");
        } else {
            viewHolderGroup.tv_name.setText("我加入的群");
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderChild viewHolderChild;
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_chatgrouplistchild, null);
            viewHolderChild = new ViewHolderChild(view);
            view.setTag(viewHolderChild);
        }else{
            viewHolderChild = (ViewHolderChild) view.getTag();
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public static class ViewHolderGroup {
        public View rootView;
        public TextView tv_name;

        public ViewHolderGroup(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }

    public static class ViewHolderChild {
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
