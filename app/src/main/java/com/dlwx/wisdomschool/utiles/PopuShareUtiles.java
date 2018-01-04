package com.dlwx.wisdomschool.utiles;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SharesAdapter;

/**
 * Created by Administrator on 2018/1/2/002.
 */

public class PopuShareUtiles extends PopupWindow implements View.OnClickListener{
    public PopuShareUtiles(Context context,View panent) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popu_share, null);
        final ViewHolder vh = new ViewHolder(view);
        this.setFocusable(true);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.Animation);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        String[] shares = context.getResources().getStringArray(R.array.shares);
        vh.gv_list.setAdapter(new SharesAdapter(context,shares));
        vh.tv_close.setOnClickListener(this);
        this.showAtLocation(panent, Gravity.BOTTOM,0,0);
        vh.rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float y = motionEvent.getY();
                LogUtiles.LogI(y+"");
                float y1 = vh.ll_root.getY();
                LogUtiles.LogI(y1+"height");
                if (y < y1) {
                    PopuShareUtiles.this.dismiss();
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View view) {
         switch (view.getId()){
                    case R.id.tv_close:
                        this.dismiss();
                        break;
                }
    }

    private class ViewHolder {
        public View rootView;
        public GridView gv_list;
        public TextView tv_close;
        public LinearLayout ll_root;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.gv_list = (GridView) rootView.findViewById(R.id.gv_list);
            this.tv_close = rootView.findViewById(R.id.tv_close);
            this.ll_root  = rootView.findViewById(R.id.ll_root);
        }

    }
}
