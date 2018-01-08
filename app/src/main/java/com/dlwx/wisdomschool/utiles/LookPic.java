package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.BigPicandDownAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/1/6/006.
 */

public class LookPic {

    public static void showPic (final Context ctx, final View parent, final List<Image> images, final int pos){
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_showpic,null);
        final PopupWindow popu = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popu.setFocusable(true);
        popu.showAtLocation(parent, Gravity.CENTER,0,0);
        ViewPager iv_pic = view.findViewById(R.id.iv_pic);
        ImageView iv_down = (ImageView) view.findViewById(R.id.iv_down);
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
                String path = images.get(downpos).getPath();
                Glide.with(ctx).asFile().load(path).into(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        Toast toast = Toast.makeText(ctx, "图片保存在:" + resource, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        // 最后通知图库更新
                        ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(resource)));
                    }
                });
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
}
