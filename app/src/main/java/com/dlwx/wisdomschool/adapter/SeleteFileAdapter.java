package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22/022.
 */

public class SeleteFileAdapter extends BaseFastAdapter {
     private List<Image> images;
    public SeleteFileAdapter(Context ctx,List<Image> images) {
        super(ctx);
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_seletefile, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        Image image = images.get(position);
        int filetype = image.getFiletype();
        if (filetype == 1) {//图片
            Glide.with(ctx).load(image.getPath()).into(vh.iv_pic);
        }else if (filetype == 2) {//音频
            Glide.with(ctx).load(R.mipmap.icon_viceo).into(vh.iv_pic);

        }else if (filetype == 3) {//txt
            Glide.with(ctx).load(R.mipmap.icon_txt).into(vh.iv_pic);
        }else{
            Glide.with(ctx).load(R.mipmap.icon_word).into(vh.iv_pic);
        }
        String[] split = image.getPath().split("/");
        vh.tv_name.setText("-"+split[split.length-1]);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
