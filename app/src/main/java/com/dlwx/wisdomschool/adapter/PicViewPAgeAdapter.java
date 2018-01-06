package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.interfac.Picseltete;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class PicViewPAgeAdapter extends PagerAdapter {
    private Context ctx;
    private List<Image> images;
    public PicViewPAgeAdapter(Context ctx, List<Image> images) {
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
            view = LayoutInflater.from(ctx).inflate(R.layout.item_picpage, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }
        if (isgone) {
            vh.cb_check.setVisibility(View.GONE);
        }else{
            vh.cb_check.setVisibility(View.VISIBLE);
        }
        Image image = images.get(position);
        vh.cb_check.setChecked(image.isCheck());
        Glide.with(ctx).load(image.getPath()).into(vh.iv_pic);
        vh.cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = vh.cb_check.isChecked();
                images.get(position).setCheck(checked);
                Picseltete.sletePicInterf.checkback(position, checked);
            }
        });
        vh.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picOnclick.close();
            }
        });
        container.addView(view);
        return view;
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

    public interface PicOnclick{
        void close();
    }
    public void setPicOnclick(PicOnclick picOnclick){
        this.picOnclick = picOnclick;
    }
   private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public CheckBox cb_check;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
        }

    }
}
