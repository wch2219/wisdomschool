package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/15/015.
 */

public class HomeTitleAdapter extends BaseRecrviewAdapter {
    private String[] strs;

    public HomeTitleAdapter(Context ctx, String[] strs) {
        super(ctx);
        this.strs = strs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_hometitle, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder)holder).tv_name.setText(strs[position]);
        ((ViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.setOnClick(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return strs.length;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
