package com.dlwx.baselib.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


/**
 * @作者 wch
 * @create at 2017/1/12 0012 下午 4:24
 * @name 父类RecycleView
 */
public abstract class BaseRecrviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public Context ctx;
    public BaseRecrviewAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();
    public void wch(Object o){
        Log.i("wch",o+"");
    }
    public void toast(Object o, View parent){

    }
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public abstract void setOnClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
