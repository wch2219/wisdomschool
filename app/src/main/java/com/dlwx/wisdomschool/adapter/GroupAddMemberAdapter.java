package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.AllClassMemberBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2/002.
 */

public class GroupAddMemberAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private List<AllClassMemberBean.BodyBean> body;
    public GroupAddMemberAdapter(Context ctx,List<AllClassMemberBean.BodyBean> body) {
        super();
        this.ctx = ctx;
        this.body = body;
    }

    @Override
    public int getGroupCount() {
        return body.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return body.get(groupPosition).getAdd_user().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_addmember, null);
            viewHolderGroup = new ViewHolderGroup(convertView);
            convertView.setTag(viewHolderGroup);
        }else{
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        final AllClassMemberBean.BodyBean bodyBean = body.get(groupPosition);
        Glide.with(ctx).load(bodyBean.getClass_pic()).into(viewHolderGroup.iv_pic);
        viewHolderGroup.tv_classname.setText(bodyBean.getClass_name());
        viewHolderGroup.tv_num.setText("班级成员："+bodyBean.getTotal_user());
        viewHolderGroup.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                for (int i = 0; i < body.size(); i++) {
                    if (groupPosition == i) {
                        bodyBean.setCheck(isChecked);
                    }else{
                        bodyBean.setCheck(!isChecked);
                    }
                }
                for (int i = 0; i < bodyBean.getAdd_user().size(); i++) {
                    AllClassMemberBean.BodyBean.AddUserBean addUserBean = bodyBean.getAdd_user().get(i);
                    addUserBean.setCheck(isChecked);
                }
               notifyDataSetChanged();
            }
        });
        if (bodyBean.isCheck()) {
            viewHolderGroup.cb_check.setChecked(true);
        }else{
            viewHolderGroup.cb_check.setChecked(false);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_addmeber_child, null);
            viewHolderChild = new ViewHolderChild(convertView);
            convertView.setTag(viewHolderChild);
        }else{
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }
        AllClassMemberBean.BodyBean bodyBean = body.get(groupPosition);
        AllClassMemberBean.BodyBean.AddUserBean addUserBean = bodyBean.getAdd_user().get(childPosition);
        Glide.with(ctx).load(addUserBean.getHeader_pic()).into(viewHolderChild.iv_pic);
        viewHolderChild.tv_name.setText(addUserBean.getJoin_role());
        if (bodyBean.isCheck()) {
            viewHolderChild.cb_check.setChecked(true);
            addUserBean.setCheck(true);
        }else{
            if (addUserBean.isCheck()) {
                viewHolderChild.cb_check.setChecked(true);
            }else{
                viewHolderChild.cb_check.setChecked(false);
            }
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHolderChild {
        public View rootView;
        public CheckBox cb_check;
        public ImageView iv_pic;
        public TextView tv_name;

        public ViewHolderChild(View rootView) {
            this.rootView = rootView;
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }
    }
   private class ViewHolderGroup {
        public View rootView;
        public CheckBox cb_check;
        public ImageView iv_pic;
        public TextView tv_classname;
        public TextView tv_num;

        public ViewHolderGroup(View rootView) {
            this.rootView = rootView;
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_classname = (TextView) rootView.findViewById(R.id.tv_classname);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
        }

    }
}
