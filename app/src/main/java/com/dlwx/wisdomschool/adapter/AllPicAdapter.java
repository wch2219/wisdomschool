package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.interfac.Picseltete;
import com.dlwx.wisdomschool.utiles.GlideuploadUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class AllPicAdapter extends BaseFastAdapter {
    private List<Image> images;

    public AllPicAdapter(Context ctx, List<Image> images) {
        super(ctx);
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size() + 1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_allpic, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            vh.rl_pic.setVisibility(View.GONE);
            vh.rl_paizhao.setVisibility(View.VISIBLE);
        } else {
            vh.rl_pic.setVisibility(View.VISIBLE);
            vh.rl_paizhao.setVisibility(View.GONE);
            GlideuploadUtils.glideUPload(ctx, images.get(position - 1).getPath()).into(vh.iv_pic);
            vh.cb_check.setChecked(images.get(position - 1).isCheck());
        }



        vh.cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = vh.cb_check.isChecked();
                images.get(position-1).setCheck(checked);
                Picseltete.sletePicInterf.checkback(position-1, checked);
            }
        });
        return convertView;
    }



    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public CheckBox cb_check;
        public RelativeLayout rl_pic;
        public RelativeLayout rl_paizhao;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
            this.rl_pic = (RelativeLayout) rootView.findViewById(R.id.rl_pic);
            this.rl_paizhao = (RelativeLayout) rootView.findViewById(R.id.rl_paizhao);
        }

    }
}
