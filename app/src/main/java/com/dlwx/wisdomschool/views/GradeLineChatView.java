package com.dlwx.wisdomschool.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public class GradeLineChatView extends View {
    private int practicalW;//控件的宽
    private int practicalH;//控件的高
    private String[] verticalstrs = {"20","40","60","80","100"};//竖向的分数
    private int [] grade = {20,80,50,70,80,69,99};
    private int Horizontalcount = 7;//横向的考试次数
    private int verticalnum = 6;//100分是的竖向分布
    private int startX = 80;//坐标系x开始位置
    private int startY = practicalH-50;//坐标系y开始位置
    private int VerticalTextMarginLeft = 20;//竖向距离左侧的边距
    private Paint TPaint = new Paint();//文字的画笔
    private Paint linePaint = new Paint();//线横竖灰色线的画笔
    private Paint greenPaint = new Paint();
    private Paint greenbgPaint = new Paint();
    private Paint whitePaint = new Paint();
    public GradeLineChatView(Context context) {
        super(context);
    }

    public GradeLineChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradeLineChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.practicalW = w;
        this.practicalH = h;
        startY = practicalH-50;
    }
    public void setContent(int [] grade){
        this.Horizontalcount = grade.length;
        this.grade = grade;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //绘制竖向的分数
        int meanH = (practicalH-150)/ (verticalstrs.length);//把高度平均分成一百份，
        TPaint.setColor(Color.GRAY);//设置字体画笔颜色
        TPaint.setAntiAlias(true);
        TPaint.setTextSize(25);
        int Ti = practicalH;
        for (int i = 0; i <=verticalstrs.length+1; i++) {
           // Ti = Ti-(meanH*i);
            if (i <= 1) {
                continue;
            }
            if (i == verticalstrs.length+1) {
                canvas.drawText(verticalstrs[i-2],VerticalTextMarginLeft,Ti-(meanH*i)+30,TPaint);
            }else{
                canvas.drawText(verticalstrs[i-2],VerticalTextMarginLeft+15,Ti-(meanH*i)+30,TPaint);
            }
        }

        //绘制横向的考试次数
            int degreeI = 80;
            int meanW = (practicalW -130)/Horizontalcount;
        for (int i = 1; i <=Horizontalcount ; i++) {
                degreeI = degreeI +meanW;
                canvas.drawText(i+"",degreeI-5,practicalH,TPaint);
        }
        linePaint.setAntiAlias(true);
        linePaint.setColor(getResources().getColor(R.color.gary));
        linePaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(startX,100);
        path.lineTo(practicalW-50,100);
        path.lineTo(practicalW-50,startY);
        path.lineTo(startX,practicalH-50);
        path.lineTo(startX,100);
        canvas.drawPath(path,linePaint);

        linePaint.setStyle(Paint.Style.FILL);
        int meanY = (practicalH - 100 - 50) / (verticalnum-1);

        for (int i = 1; i < verticalnum; i++) {
                canvas.drawLine(startX,meanY*i+100,practicalW-50,meanY*i+100,linePaint);
        }
        int meanX = (practicalW-130)/Horizontalcount;
        for (int i = 1; i < verticalnum; i++) {
            canvas.drawLine(meanX*i+80,100,meanX*i+80,practicalH-50,linePaint);
        }

        //绘制折线图

        greenPaint.setColor(getResources().getColor(R.color.linegreen));
        greenPaint.setAntiAlias(true);
        Path greenPath = new Path();
        Path greenbgPath = new Path();
        greenPaint.setStrokeWidth(3);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPath.moveTo(startX,startY);
        greenbgPath.moveTo(startX,startY);
        int gradeH = practicalH - 150;//分数的范围
        float onegradeH = gradeH / 100f;//一分所占的高度

        Log.i("wch",gradeH+";"+onegradeH);
        for (int i = 1; i <= Horizontalcount; i++) {
            int gra = grade[i - 1];

            greenbgPaint.setColor(getResources().getColor(R.color.transgreen));
            greenbgPath.lineTo(meanX * i + 80, (practicalH - 50) - (onegradeH * gra));
            if (i == Horizontalcount) {
                greenbgPath.lineTo(practicalW - 50, practicalH - 50);
                greenbgPath.lineTo(startX, startY);
                canvas.drawPath(greenbgPath, greenbgPaint);
            }
        }
        whitePaint.setColor(Color.WHITE);
        for (int i = 1; i <= Horizontalcount; i++) {
            int gra = grade[i - 1];
            greenPath.lineTo(meanX*i+80,(practicalH-50)-(onegradeH*gra));
            greenPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(meanX*i+80,(practicalH-50)-(onegradeH*gra),10,greenPaint);
            greenPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(meanX*i+80,(practicalH-50)-(onegradeH*gra),8,whitePaint);

            canvas.drawCircle(meanX*i+80,(practicalH-50)-(onegradeH*gra),5,greenPaint);

        }
        greenPaint.setColor(getResources().getColor(R.color.linegreen));
        greenPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(greenPath,greenPaint);
        whitePaint.setColor(Color.WHITE);
        for (int i = 1; i <= Horizontalcount; i++) {
            int gra = grade[i - 1];
            greenPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(meanX*i+80,(practicalH-50)-(onegradeH*gra),10,greenPaint);
            greenPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(meanX*i+80,(practicalH-50)-(onegradeH*gra),8,whitePaint);

            canvas.drawCircle(meanX*i+80,(practicalH-50)-(onegradeH*gra),5,greenPaint);

        }

    }
}
