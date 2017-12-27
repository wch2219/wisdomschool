package com.dlwx.baselib.utiles;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Window;
import android.view.WindowManager;

import com.dlwx.baselib.R;


/**
 * Created by Administrator on 2017/5/25/025.
 */

public class MyProgressLoading extends Dialog {


    public MyProgressLoading(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
//        lp.x = 100; // 新位置X坐标
//        lp.y = 100; // 新位置Y坐标
        lp.width = 300; // 宽度
        lp.height = 300; // 高度
        lp.alpha = 1.0f; // 透明度

        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_press);
    }
}
