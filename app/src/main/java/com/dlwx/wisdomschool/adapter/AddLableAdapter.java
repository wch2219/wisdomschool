package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.TagListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25/025.
 */

public class AddLableAdapter extends BaseRecrviewAdapter {
    private List<TagListBean.BodyBean> body;
    public AddLableAdapter(Context ctx,List<TagListBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_addlable, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagListBean.BodyBean bodyBean = body.get(position);
        ((ViewHolder)holder).tv_name.setText(bodyBean.getSigname());
    }

    @Override
    public int getItemCount() {
        return body.size();
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
