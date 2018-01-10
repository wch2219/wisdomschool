package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9/009.
 */

public class RecordPicListAdapter extends BaseFastAdapter {
    private  List<String> imgs;
    public RecordPicListAdapter(Context ctx, List<String> imgs) {
        super(ctx);
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_item_record_pic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams layoutParams = vh.iv_pic.getLayoutParams();
        if (imgs.size() == 1) {
            layoutParams.height = 200;
        }else if (imgs.size() == 2) {
            layoutParams.height = 1500;
        } else{
            layoutParams.height = 100;
        }
        vh.iv_pic.setLayoutParams(layoutParams);
        Glide.with(ctx).load(imgs.get(position)).into(vh.iv_pic);

        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
        }

    }
}
