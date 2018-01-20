package com.dlwx.wisdomschool.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/1/17/017.
 */

public class PolaroidResizeLinearLayout extends LinearLayout{
    public PolaroidResizeLinearLayout(Context context) {
        super(context);
    }

    public PolaroidResizeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PolaroidResizeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //大小改变的监听
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mListener != null)
        {
            mListener.OnResizeRelative(w, h, oldw, oldh);
        }
    }

    // 监听接口
    private OnResizeRelativeListener mListener;

    public interface OnResizeRelativeListener
    {
        void OnResizeRelative(int w, int h, int oldw, int oldh);
    }

    public void setOnResizeRelativeListener(OnResizeRelativeListener l)
    {
        mListener = l;
    }
}
