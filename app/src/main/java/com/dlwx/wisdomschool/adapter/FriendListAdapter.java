package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.wisdomschool.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29/029.
 */

public class FriendListAdapter extends BaseFastAdapter {
    private List<String> usernames;
    public FriendListAdapter(Context ctx,List<String> usernames) {
        super(ctx);
        this.usernames = usernames;
    }

    @Override
    public int getCount() {
        return usernames.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_frendlist, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        String s = usernames.get(position);
        vh.tv_name.setText(s);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public CircleImageView iv_head;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_head = (CircleImageView) rootView.findViewById(R.id.iv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
