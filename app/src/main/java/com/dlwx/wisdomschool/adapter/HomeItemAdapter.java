package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.HomelistBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24/024.
 */

public class HomeItemAdapter extends BaseRecrviewAdapter {
    private List<HomelistBean.BodyBean.ListBean> list;
    private View parent;

    public HomeItemAdapter(Context ctx, List<HomelistBean.BodyBean.ListBean> list, View parent) {
        super(ctx);
        this.list = list;
        this.parent = parent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_homeitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;
        HomelistBean.BodyBean.ListBean listBean = list.get(position);
        Glide.with(ctx).load(listBean.getTitle_pic()).apply(new RequestOptions().centerCrop()).into(vh.iv_pic);
        vh.tv_title.setText(listBean.getTitle());
        vh.tv_type.setText(listBean.getSign());
        vh.tv_messnum.setText(listBean.getPl_num());
        vh.tv_praisenum.setText(listBean.getZan_num());
        vh.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.setOnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_title;
        public TextView tv_type;
        public TextView tv_messnum;
        public TextView tv_praisenum;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.tv_messnum = (TextView) rootView.findViewById(R.id.tv_messnum);
            this.tv_praisenum = (TextView) rootView.findViewById(R.id.tv_praisenum);
        }

    }
}
