package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/18/018.
 */

public class ClassGradeAdapter extends BaseExpandableListAdapter {
    private Context ctx;

    public ClassGradeAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int i) {
        return 2;
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
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolderGroup vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_gradeclass, null);
            vh = new ViewHolderGroup(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolderGroup) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderChild vhchild;
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_gradeclasscjild, null);
            vhchild = new ViewHolderChild(view);
            view.setTag(vhchild);
        }else{
            vhchild = (ViewHolderChild) view.getTag();
        }
        if (i1 == 0) {
            vhchild.tv_name.setText("成绩排名");
            Glide.with(ctx).load(R.mipmap.icon_cjfxpaiming).into(vhchild.iv_pic);
        }else{
            vhchild.tv_name.setText("成绩分析");
            Glide.with(ctx).load(R.mipmap.icon_cjfxfenxi).into(vhchild.iv_pic);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    private class ViewHolderGroup {
        public View rootView;
        public TextView tv_name;
        public LinearLayout ll_create;

        public ViewHolderGroup(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.ll_create = (LinearLayout) rootView.findViewById(R.id.ll_create);
        }

    }

    private class ViewHolderChild {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;

        public ViewHolderChild(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
