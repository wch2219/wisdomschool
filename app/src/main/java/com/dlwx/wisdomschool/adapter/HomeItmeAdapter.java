package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.DisCussActivity;
import com.dlwx.wisdomschool.activitys.LookReadActivity;

/**
 * Created by Administrator on 2017/12/15/015.
 */

public class HomeItmeAdapter extends BaseRecrviewAdapter {
    public HomeItmeAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_homeitem
                , null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

            ((ViewHolder) holder).ll_right.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).ll_left.setVisibility(View.GONE);
            ((ViewHolder) holder).ll_rightdiscuss.setVisibility(View.GONE);
        }else if (position == 1) {
            ((ViewHolder) holder).ll_right.setVisibility(View.GONE);
            ((ViewHolder) holder).ll_left.setVisibility(View.GONE);
            ((ViewHolder) holder).ll_rightdiscuss.setVisibility(View.VISIBLE);
        } else {
            ((ViewHolder) holder).ll_right.setVisibility(View.GONE);
            ((ViewHolder) holder).ll_left.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).ll_rightdiscuss.setVisibility(View.GONE);
        }
        ((ViewHolder) holder).iv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, LookReadActivity.class));
            }
        });
        ((ViewHolder) holder).iv_messdiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx,DisCussActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_leftdate;
        public LinearLayout ll_left;
        public TextView tv_right;
        public ImageView iv_rightpic;
        public TextView tv_rightdate;
        public ImageView iv_look;
        public ImageView iv_righttitpic;
        public LinearLayout ll_right;
        public TextView tv_rightdiscuss;
        public TextView tv_rightdatediscuss;
        public ImageView iv_lookdicuss;
        public ImageView iv_messdiscuss;
        public ImageView iv_righttitpicdiscuss;
        public LinearLayout ll_rightdiscuss;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_leftdate = (TextView) rootView.findViewById(R.id.tv_leftdate);
            this.ll_left = (LinearLayout) rootView.findViewById(R.id.ll_left);
            this.tv_right = (TextView) rootView.findViewById(R.id.tv_right);
            this.iv_rightpic = (ImageView) rootView.findViewById(R.id.iv_rightpic);
            this.tv_rightdate = (TextView) rootView.findViewById(R.id.tv_rightdate);
            this.iv_look = (ImageView) rootView.findViewById(R.id.iv_look);
            this.iv_righttitpic = (ImageView) rootView.findViewById(R.id.iv_righttitpic);
            this.ll_right = (LinearLayout) rootView.findViewById(R.id.ll_right);
            this.tv_rightdiscuss = (TextView) rootView.findViewById(R.id.tv_rightdiscuss);
            this.tv_rightdatediscuss = (TextView) rootView.findViewById(R.id.tv_rightdatediscuss);
            this.iv_lookdicuss = (ImageView) rootView.findViewById(R.id.iv_lookdicuss);
            this.iv_messdiscuss = (ImageView) rootView.findViewById(R.id.iv_messdiscuss);
            this.iv_righttitpicdiscuss = (ImageView) rootView.findViewById(R.id.iv_righttitpicdiscuss);
            this.ll_rightdiscuss = (LinearLayout) rootView.findViewById(R.id.ll_rightdiscuss);
        }

    }
}
