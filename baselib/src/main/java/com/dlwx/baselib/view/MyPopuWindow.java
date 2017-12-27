package com.dlwx.baselib.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/7/19/019.
 */

public class MyPopuWindow extends PopupWindow {
    public MyPopuWindow(View context, int attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public MyPopuWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }



    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }
}
