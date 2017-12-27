package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/26/026.
 */

public class InputPhoneInviteAdapter extends BaseFastAdapter {
    private int size;
    public InputPhoneInviteAdapter(Context ctx,int size) {
        super(ctx);
        this.size = size;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_inputphoneinvite, null);
            vh =  new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public void setSize(int size) {
        this.size = size;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public View rootView;
        public EditText et_phone;
        public ImageView iv_close;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_phone = (EditText) rootView.findViewById(R.id.et_phone);
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
        }

    }
}
