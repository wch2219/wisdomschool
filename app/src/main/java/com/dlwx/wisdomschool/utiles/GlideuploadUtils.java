package com.dlwx.wisdomschool.utiles;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class GlideuploadUtils {
    public static DrawableRequestBuilder<Object> glideUPload(Context ctx,Object path){
        DrawableRequestBuilder<Object> error = Glide.with(ctx).load(path)
                // 占位图
                .placeholder(R.mipmap.icon_load)
                // 加载错误图
                .error(R.mipmap.error);
        return error;
    }
    public static void glideCleanCache(Context ctx){
        Glide.get(ctx).clearDiskCache();
    }
}
