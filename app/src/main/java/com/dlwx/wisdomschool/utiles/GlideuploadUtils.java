package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class GlideuploadUtils {
    public static RequestBuilder<Drawable>  glideUPload(Context ctx,Object path){
        RequestBuilder<Drawable> load = Glide.with(ctx).load(path);

        return load;
    }
    public static void glideCleanCache(Context ctx){
        Glide.get(ctx).clearDiskCache();
    }
}
