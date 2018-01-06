package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PicViewPAgeAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/1/6/006.
 */

public class LookPic {


    public static void showPic (Context ctx, View parent, List<Image> images,int pos){
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_showpic,null);
        final PopupWindow popu = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popu.setFocusable(true);
        popu.showAtLocation(parent, Gravity.CENTER,0,0);
        ViewPager iv_pic = view.findViewById(R.id.iv_pic);
        PicViewPAgeAdapter picViewPAgeAdapter = new PicViewPAgeAdapter(ctx, images);
        picViewPAgeAdapter.setGone(true);
        iv_pic.setAdapter(picViewPAgeAdapter);
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            int oldposition = image.getOldposition();
            if (oldposition == pos) {
                iv_pic.setCurrentItem(i);
            }
        }

        picViewPAgeAdapter.setPicOnclick(new PicViewPAgeAdapter.PicOnclick() {
            @Override
            public void close() {
               popu.dismiss();
            }
        });
    }
}
