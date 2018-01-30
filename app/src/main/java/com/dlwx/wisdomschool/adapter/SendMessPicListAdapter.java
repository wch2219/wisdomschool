package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.wisdomschool.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/11/011.
 */

public class SendMessPicListAdapter extends BaseRecrviewAdapter {
    private ArrayList<String> imagesStr;
    public SendMessPicListAdapter(Context ctx,ArrayList<String> images) {
        super(ctx);
        this.imagesStr = images;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(ctx).inflate(R.layout.item_sendmesspic, null);
        return new ViewHolder(convertView);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Glide.with(ctx).load(imagesStr.get(position)).apply(new RequestOptions().centerCrop()).into( ((ViewHolder)holder).iv_pic);
        ((ViewHolder)holder).iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.setOnClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return imagesStr.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView iv_pic;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
        }

    }
}
