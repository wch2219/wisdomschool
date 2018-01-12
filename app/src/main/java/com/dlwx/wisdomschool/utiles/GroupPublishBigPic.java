package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.BigPicandDownAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12/012.
 */

public class GroupPublishBigPic {

    public static void showPic (final Context ctx, final View parent, final List<Image> images, final int pos){
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_showpic,null);
        final PopupWindow popu = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popu.setFocusable(true);
        popu.showAtLocation(parent, Gravity.CENTER,0,0);
        final ViewPager iv_pic = view.findViewById(R.id.iv_pic);
        ImageView iv_down = (ImageView) view.findViewById(R.id.iv_down);
        Glide.with(ctx).load(R.mipmap.icon_szshanchu).into(iv_down);
        final TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        final BigPicandDownAdapter picViewPAgeAdapter = new BigPicandDownAdapter(ctx, images);
        picViewPAgeAdapter.setGone(true);
        iv_pic.setAdapter(picViewPAgeAdapter);
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            int oldposition = image.getOldposition();
            if (oldposition == pos) {
                iv_pic.setCurrentItem(i);
                tv_num.setText(i+1+"/"+images.size());
            }
        }
        iv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                images.remove(downpos);
                picViewPAgeAdapter.notifyDataSetChanged();
                deletteListener.delete(downpos);
                if (images.size() == 0) {
                    popu.dismiss();
                }
            }
        });
        picViewPAgeAdapter.setPicOnclick(new BigPicandDownAdapter.PicOnclick() {
            @Override
            public void close() {
                popu.dismiss();
            }
        });
        iv_pic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                downpos = position;
                tv_num.setText(position+1+"/"+images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private static int downpos;

    public interface DeletteListener{
        void delete(int postion);
    }
    private static DeletteListener deletteListener;

    public static void setDeletteListener(DeletteListener deletteListener1){
        deletteListener = deletteListener1;
    }
}
