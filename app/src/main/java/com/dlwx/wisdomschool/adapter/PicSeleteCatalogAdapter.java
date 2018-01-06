package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;
import com.dlwx.baselib.bean.PicFileClassBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class PicSeleteCatalogAdapter extends BaseFastAdapter {
    private List<PicFileClassBean.Body> body;
    private List<Image> images;
    public PicSeleteCatalogAdapter(Context ctx, List<PicFileClassBean.Body> body,List<Image> images) {
        super(ctx);
        this.body = body;
        this.images = images;
    }

    @Override
    public int getCount() {
        return body.size()+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_picselete, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh= (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            Glide.with(ctx).load(images.get(position).getPath()).into(vh.iv_pic);
            vh.tv_title.setText("所有照片");
            vh.tv_num.setText("("+images.size()+")");
        }else{
            PicFileClassBean.Body body = this.body.get(position-1);
            Glide.with(ctx).load(body.getPathBeans().get(0).getPath()).into(vh.iv_pic);
            vh.tv_title.setText(body.getTitle());
            vh.tv_num.setText("("+body.getPathBeans().size()+")");
        }
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_title;
        public TextView tv_num;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
        }

    }
}
