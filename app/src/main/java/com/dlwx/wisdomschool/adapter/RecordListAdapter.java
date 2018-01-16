package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.RecordListBean;
import com.dlwx.wisdomschool.utiles.EmoSwichUtiles;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 成长纪录列表
 */

public class RecordListAdapter extends BaseFastAdapter {
    private List<RecordListBean.BodyBean> body;
    public RecordListAdapter(Context ctx,List<RecordListBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_recordlist, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();

        }
        RecordListBean.BodyBean bodyBean = body.get(position);
        //头像
        Glide.with(ctx).load(bodyBean.getHeader_pic()).into(vh.iv_pic);
        //昵称
        vh.tv_name.setText(bodyBean.getNickname());
//        vh.tv_classname.setText(bodyBean.getC);
        //创建时间
        vh.tv_time.setText(bodyBean.getCreate_time());
        //突破片的数量
        if (bodyBean.getImgs().size() ==1) {

            vh.gv_list.setNumColumns(1);
        }else if (bodyBean.getImgs().size() == 2) {
            vh.gv_list.setNumColumns(2);
        }else{
            vh.gv_list.setNumColumns(3);
        }
        //判断是是视频还是图片
        if (TextUtils.isEmpty(bodyBean.getVideo())) {
            vh.gv_list.setVisibility(View.VISIBLE);
            vh.iv_video.setVisibility(View.GONE);
        }else{
            vh.gv_list.setVisibility(View.GONE);
            vh.iv_video.setVisibility(View.VISIBLE);
        }
        //展示图片列表
        List<String> imgs = bodyBean.getImgs();
        vh.gv_list.setAdapter(new RecordPicListAdapter(ctx,imgs));
        //语音
        if (TextUtils.isEmpty(bodyBean.getVoice())) {
            vh.rl_viceo.setVisibility(View.GONE);
        }else{
            vh.rl_viceo.setVisibility(View.VISIBLE);
        }
//        标签
        List<String> mVals = new ArrayList<>();
        for (int i = 0; i < bodyBean.getPerson_sign().size(); i++) {
            mVals.add((String) bodyBean.getPerson_sign().get(i));
        }
         for (int i = 0; i < bodyBean.getQuality_sign().size(); i++) {
            mVals.add((String) bodyBean.getQuality_sign().get(i));
        }
        vh.id_flowlayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(ctx).inflate(R.layout.tv_tag,
                        vh.id_flowlayout, false);
                tv.setText("#"+s+"#");
                return tv;
            }
        });
        //文字简介
        vh.tv_connect.setText(bodyBean.getRecord_bf());
        EmoSwichUtiles.toSwich(ctx,vh.tv_connect,bodyBean.getRecord_bf());
        //语音播放
        vh.rl_viceo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_classname;
        public TextView tv_time;
        public ImageView iv_share;
        public MyGridView gv_list;
        public ImageView iv_video;
        public TagFlowLayout id_flowlayout;
        public TextView tv_connect;
        public LinearLayout ll_list;
        public RelativeLayout rl_viceo;
        public TextView tv_viceotime;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_classname = (TextView) rootView.findViewById(R.id.tv_classname);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.iv_share = (ImageView) rootView.findViewById(R.id.iv_share);
            this.gv_list = (MyGridView) rootView.findViewById(R.id.gv_list);
            this.iv_video = (ImageView) rootView.findViewById(R.id.iv_video);
            this.id_flowlayout = (TagFlowLayout) rootView.findViewById(R.id.id_flowlayout);
            this.tv_connect = (TextView) rootView.findViewById(R.id.tv_connect);
            this.ll_list = (LinearLayout) rootView.findViewById(R.id.ll_list);
            this.rl_viceo = (RelativeLayout) rootView.findViewById(R.id.rl_viceo);
            this.tv_viceotime = (TextView) rootView.findViewById(R.id.tv_viceotime);
        }

    }
}
