package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.SlideCancelLoadPic;
import com.dlwx.wisdomschool.views.PicImageView;

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
        RequestManager requestManager = SlideCancelLoadPic.loadPic(ctx);
        requestManager.load(imgs.get(position)).apply(new RequestOptions().
                centerCrop().placeholder(R.mipmap.icon_lttupian).diskCacheStrategy(DiskCacheStrategy.ALL)).into(vh.iv_pic);
        ViewGroup.LayoutParams layoutParams = vh.iv_pic.getLayoutParams();
        if (imgs.size() == 1) {
            layoutParams.height = 500;
        }else if (imgs.size() == 2) {
            layoutParams.height = 300;
        } else{
            layoutParams.height = 200;
        }
        vh.iv_pic.setLayoutParams(layoutParams);
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public PicImageView iv_pic;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (PicImageView) rootView.findViewById(R.id.iv_pic);
        }

    }
}
