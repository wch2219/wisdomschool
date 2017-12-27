package com.dlwx.wisdomschool.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dlwx.wisdomschool.R;
import com.tencent.mm.opensdk.utils.Log;

/**
 * 自定义成绩表
 */

public class GradeTabView extends View {
    private Paint vPaint = new Paint();
    private Paint hPaint = new Paint();

    private int w;
    private int h;
    public GradeTabView(Context context) {
        super(context);
    }

    public GradeTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradeTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        Log.i("wch",h+"");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        vPaint.setColor(getResources().getColor(R.color.garytext));
        hPaint.setColor(getResources().getColor(R.color.garytext));

        vPaint.setTextSize(25);
        hPaint.setTextSize(25);
        int vi = (h-20) / 6;
        int hi = w / 7;
        int vh;
        int text= 100;
        float startx = 60;
        float eny = w;

        for (int j = 1; j <=7 ; j++) {
            vh = vi*j;
            canvas.drawText(text+"",0,vh,vPaint);
            text = text-20;

        }
        for (int j = 1; j < 7; j++) {

            canvas.drawText(j+"",hi*j+55,h,vPaint);
        }

    }
}
