package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.dlwx.wisdomschool.R;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/12/012.
 */

public class EmoSwichUtiles {
    /**
     *
     * @param ctx 上下文
     * @param tv 显示的TextView控件
     * @param source 原始的字符串
     */
        public static void toSwich(Context ctx, TextView tv,String source){
            SpannableString spannString= new SpannableString(source);
            //表情正则
            Pattern compile = Pattern.compile("ee_\\d{1,2}");
            Matcher matcher = compile.matcher(source);
            while (matcher.find()) {
                //当前匹配到的开始位置和结束位置
                int start = matcher.start();
                int end = matcher.end();
                //获得与当前字符名字相同的资源文件id
                int drawable = getResId(matcher.group(),R.drawable.class);
                //获得控件的字体大小
                int size = (int) tv.getTextSize();
                //把资源文件转换为Bitmap
                Resources r = ctx.getResources();
                InputStream is = r.openRawResource(drawable);
                BitmapDrawable bmpDraw = new BitmapDrawable(is);
                Bitmap bit = bmpDraw.getBitmap();
                //压缩bitmap
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bit, size, size, true);
                ImageSpan span = new ImageSpan(ctx, scaleBitmap);
                spannString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //显示
            tv.setText(spannString);
        }

    private static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
