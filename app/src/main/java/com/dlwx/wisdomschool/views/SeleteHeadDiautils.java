package com.dlwx.wisdomschool.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.utiles.UploadPicUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.PreinstallPicActivity;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class SeleteHeadDiautils extends AlertDialog.Builder implements View.OnClickListener{
    private Context ctx;
    private AlertDialog show;

    public SeleteHeadDiautils(Context context) {
        super(context);
        this.ctx = context;
        this.setCancelable(true);
    }

    public SeleteHeadDiautils(Context context, int themeResId) {
        super(context, themeResId);
    }


    public void init() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_seletehead, null);
        ViewHolder vh = new ViewHolder(view);
        this.setView(view);
        vh.ll_preinstall.setOnClickListener(this);
        vh.ll_camera.setOnClickListener(this);
        show = this.show();
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
                    case R.id.ll_preinstall://预设

                        ctx.startActivity(new Intent(ctx,PreinstallPicActivity.class));
                    show.dismiss();
                        break;
                     case R.id.ll_camera://相机相册
                         UploadPicUtiles.album(ctx);
                         show.dismiss();
                        break;

                }
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_close;
        public LinearLayout ll_preinstall;
        public LinearLayout ll_camera;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.ll_preinstall = (LinearLayout) rootView.findViewById(R.id.ll_preinstall);
            this.ll_camera = (LinearLayout) rootView.findViewById(R.id.ll_camera);


        }

    }
}
