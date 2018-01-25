package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.VideoPlayActivity;
import com.dlwx.wisdomschool.bean.HomelistBean;
import com.dlwx.wisdomschool.utiles.LookPic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/24/024.
 */

public class HomeItemAdapter extends BaseRecrviewAdapter {
    private List<HomelistBean.BodyBean> body;
    private View parent;
    public HomeItemAdapter(Context ctx,List<HomelistBean.BodyBean> body,View parent) {
        super(ctx);
        this.body = body;
        this.parent = parent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_homeitem, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;
        final HomelistBean.BodyBean bodyBean = body.get(position);
//        Glide.with(ctx).load(bodyBean.get)
        String is_article = bodyBean.getIs_article();
        if ("1".equals(is_article)) {//是文章
            String video = bodyBean.getVideo();
            if (TextUtils.isEmpty(video)) {//视频为空  是图片
                Glide.with(ctx).load(bodyBean.getPic()).apply(new RequestOptions()
                        .placeholder(R.mipmap.icon_lttupian)
                        .error(R.mipmap.icon_lttupian).centerCrop()).into(vh.iv_pic);
                vh.iv_isvideo.setVisibility(View.GONE);
            }else{
                Glide.with(ctx).load(bodyBean.getVideo()).apply(new RequestOptions()
                        .placeholder(R.mipmap.icon_lttupian)
                        .error(R.mipmap.icon_lttupian).centerCrop()).into(vh.iv_pic);
                vh.iv_isvideo.setVisibility(View.VISIBLE);
            }
        }else{//是周刊
            Glide.with(ctx).load(bodyBean.getPic()).apply(new RequestOptions()
                    .placeholder(R.mipmap.icon_lttupian).centerCrop()
                    .error(R.mipmap.icon_lttupian)).into(vh.iv_pic);
            vh.iv_isvideo.setVisibility(View.GONE);
        }
        vh.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(bodyBean.getIs_article())) {//是文章
                    if (!TextUtils.isEmpty(bodyBean.getVideo())) {//视频为空  是图片
                        Intent intent = new Intent(ctx, VideoPlayActivity.class);
                        intent.putExtra("path",bodyBean.getVideo());
                        ctx.startActivity(intent);
                    }else{
                        List<Image> images = new ArrayList<>();
                        Image image = new Image();
                        image.setPath(bodyBean.getPic());
                        images.add(image);
                        LookPic.showPic(ctx, parent, images, 0);
                    }
                }else{
                    List<Image> images = new ArrayList<>();
                    Image image = new Image();
                    image.setPath(bodyBean.getPic());
                    images.add(image);
                    LookPic.showPic(ctx, parent, images, 0);
                }
            }
        });
        vh.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.setOnClick(position);
            }
        });
        vh.tv_content.setText(bodyBean.getTitle());
        vh.tv_date.setText(bodyBean.getCreatedtime());
        vh.tv_looknum.setText(bodyBean.getView_num());
    }

    @Override
    public int getItemCount() {
        return body.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView iv_head;
        public TextView tv_title;
        public ImageView iv_pic;
        public ImageView iv_isvideo;
        public TextView tv_content;
        public TextView tv_date;
        public TextView tv_looknum;
        public LinearLayout ll_looknum;
        public LinearLayout ll_left;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_head = (ImageView) rootView.findViewById(R.id.iv_head);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.iv_isvideo = (ImageView) rootView.findViewById(R.id.iv_isvideo);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.tv_looknum = (TextView) rootView.findViewById(R.id.tv_looknum);
            this.ll_looknum = (LinearLayout) rootView.findViewById(R.id.ll_looknum);
            this.ll_left = (LinearLayout) rootView.findViewById(R.id.ll_left);
        }

    }
}
