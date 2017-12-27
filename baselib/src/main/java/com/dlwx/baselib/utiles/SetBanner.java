package com.dlwx.baselib.utiles;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * 设置轮播图
 */

public class SetBanner {
    public static void startBanner(Context ctx, Banner banner, List<String> list){
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(list);
        banner.setDelayTime(3000);
        banner.setBannerAnimation(Transformer.Accordion);
        banner.start();

    }
    /**
     * banner图的图片加载器
     */
    public static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);
        }
    }
}
