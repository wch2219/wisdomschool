package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class BigPicandDownAdapter extends PagerAdapter {
    private Context ctx;
    private List<Image> images;

    public BigPicandDownAdapter(Context ctx, List<Image> images) {
        this.ctx = ctx;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = null;
        final ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_picpageanddown, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        Image image = images.get(position);
        Glide.with(ctx).load(image.getPath()).into(vh.iv_pic);
        vh.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picOnclick.close();
            }
        });
        container.addView(view);
        return view;
    }
    private int currentItem;
    public void setCurrentItem(int currentItem){
        this.currentItem = currentItem;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private PicOnclick picOnclick;
    private boolean isgone;

    public void setGone(boolean b) {
        this.isgone = b;
    }

    public interface PicOnclick {
        void close();
    }

    public void setPicOnclick(PicOnclick picOnclick) {
        this.picOnclick = picOnclick;
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
