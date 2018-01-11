package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.PublishSaveTagBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25/025.
 */

public class PublishLableAdapter extends BaseRecrviewAdapter {
    private List<PublishSaveTagBean> saveTagBeans;
    public PublishLableAdapter(Context ctx, List<PublishSaveTagBean> saveTagBeans) {
        super(ctx);
        this.saveTagBeans = saveTagBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_publishlabe, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder)holder).tv_name.setText(saveTagBeans.get(position).getTagName());
        ((ViewHolder)holder).iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    deleteTagListener.delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return saveTagBeans.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public TextView tv_name;
        public ImageView iv_close;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
        }

    }
    public interface DeleteTagListener{
        void delete(int postion);
    }
    private DeleteTagListener deleteTagListener;

    public void setDeleteTagListener(DeleteTagListener deleteTagListener){
        this.deleteTagListener = deleteTagListener;
    }

}
