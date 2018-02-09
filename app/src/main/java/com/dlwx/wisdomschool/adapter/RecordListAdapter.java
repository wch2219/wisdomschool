package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.RecordDescActivity;
import com.dlwx.wisdomschool.activitys.VideoPlayActivity;
import com.dlwx.wisdomschool.bean.RecordListBean;
import com.dlwx.wisdomschool.utiles.EmoSwichUtiles;
import com.dlwx.wisdomschool.utiles.LookPic;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;
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
    private View parentView;
    public RecordListAdapter(Context ctx,List<RecordListBean.BodyBean> body,View parentView) {
        super(ctx);
        this.body = body;
        this.parentView = parentView;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_recordlist, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();

        }
        final RecordListBean.BodyBean bodyBean = body.get(position);
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
            vh.rl_voide.setVisibility(View.GONE);
        }else{
            vh.gv_list.setVisibility(View.GONE);
            vh.rl_voide.setVisibility(View.VISIBLE);
            if (bodyBean == null) {

            }
            if (bodyBean.getImgs().size() == 0) {

                Glide.with(ctx).asBitmap().load(R.mipmap.icon_lttupian).apply(new RequestOptions().centerCrop()).into(vh.iv_video);
            }else{

                Glide.with(ctx).asBitmap().load(bodyBean.getImgs().get(0)).apply(new RequestOptions().centerCrop()).into(vh.iv_video);
            }
        }
        //展示图片列表
        List<String> imgs = bodyBean.getImgs();
        vh.gv_list.setAdapter(new RecordPicListAdapter(ctx,imgs));
        //语音
        if (TextUtils.isEmpty(bodyBean.getVoice())) {
            vh.rl_viceo.setVisibility(View.GONE);

        }else{
            vh.rl_viceo.setVisibility(View.VISIBLE);
//            String time = VoicetranscribeAndPlayUtiles.durationTime(bodyBean.getVoice());
            vh.tv_viceotime.setText(bodyBean.getVoice_seconds()+"''");
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

        //赞
        final int is_praise = bodyBean.getPraise().getIs_praise();
        if (is_praise == 0) {
            Glide.with(ctx).load(R.mipmap.icon_czjlzana).into(vh.iv_praise);
        }else{
            Glide.with(ctx).load(R.mipmap.icon_czjlzan).into(vh.iv_praise);
        }
        vh.tv_praisenum.setText("赞("+bodyBean.getPraise().getNum()+")");
        //评论列表
        final List<RecordListBean.BodyBean.PinglunHuifu> pinglun_huifu = bodyBean.getPinglun_huifu();
        if (pinglun_huifu != null) {
            vh.lv_comment.setAdapter(new RecordCommentListAdapter(ctx,pinglun_huifu));
        }
        EmoSwichUtiles.toSwich(ctx,vh.tv_connect,bodyBean.getRecord_bf());
        /**
         * 视频播放
         */
        vh.rl_voide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ctx, VideoPlayActivity.class);
                intent.putExtra("path", bodyBean.getVideo());
                ctx.startActivity(intent);
            }
        });
        //语音播放
        vh.rl_viceo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.iv_playing.setImageResource(R.drawable.anim_viceo_play);
                VoicetranscribeAndPlayUtiles.play(vh.iv_playing,bodyBean.getVoice());
            }
        });
        /**
         * 图片点击
         */
        vh.gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> imgs1 = bodyBean.getImgs();
                List<Image> images = new ArrayList<>();
                for (int i = 0; i < imgs1.size(); i++) {
                    Image image = new Image();
                    image.setPath(imgs1.get(i));
                    image.setOldposition(i);
                    images.add(image);
                }
                LookPic.showPic(ctx,parentView,images,position);
            }
        });
        /**
         * 点击赞
         */
            vh.ll_praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is_praise == 0) {
                        Glide.with(ctx).load(R.mipmap.icon_czjlzan).into(vh.iv_praise);
                        windowListener.praise(bodyBean.getId(),"1");
                        vh.tv_praisenum.setText("赞("+(bodyBean.getPraise().getNum()+1)+")");
                    }else{
                        Glide.with(ctx).load(R.mipmap.icon_czjlzana).into(vh.iv_praise);
                        windowListener.praise(bodyBean.getId(),"2");
                        vh.tv_praisenum.setText("赞("+(bodyBean.getPraise().getNum()-1)+")");
                    }
                }
            });
        /**
         * 点击评价
         */
            vh.ll_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    windowListener.show();
                    windowListener.send(bodyBean.getId(),null);
                }
            });
            vh.lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RecordListBean.BodyBean.PinglunHuifu pinglunHuifu = pinglun_huifu.get(position);

                    windowListener.show();
                    if (TextUtils.isEmpty(pinglunHuifu.getHfid())) {
                        if (pinglunHuifu.getMy_id().equals(pinglunHuifu.getPlid())) {

                            return;
                        }
                        windowListener.send(bodyBean.getId(),pinglunHuifu.getPlid());
                    }else{
                        if (pinglunHuifu.getMy_id().equals(pinglunHuifu.getHfid())) {

                            return;
                        }
                        windowListener.send(bodyBean.getId(),pinglunHuifu.getHfid());
                    }
                }
            });
        /**
         * 点击弹出分享
         */
//            vh.ll_share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    windowListener.share(position,bodyBean.getId(),bodyBean.getShare_url());
//                }
//            });
            vh.ll_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, RecordDescActivity.class);
                    intent.putExtra("bodyBean",bodyBean);
                    ctx.startActivity(intent);
                }
            });
            vh.tv_connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, RecordDescActivity.class);
                    intent.putExtra("bodyBean",bodyBean);
                    ctx.startActivity(intent);
                }
            });
        return convertView;
    }
    public interface ShowInputWindowListener{
        void show();
        void send(String recorid,String otherId);
        void praise(String recorid,String type);
        void share(int postion,String recorid,String shareurl);
    }
    public ShowInputWindowListener windowListener;

    public void setWindowListener(ShowInputWindowListener windowListener) {
        this.windowListener = windowListener;
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
        public LinearLayout ll_praise;
        public ImageView iv_praise;
        public LinearLayout ll_comment;//评论
        public MyListView lv_comment;
        public TextView tv_praisenum;
        public RelativeLayout rl_voide;
        public ImageView iv_playing;
        public LinearLayout ll_share;
        public LinearLayout ll_title;
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
            this.ll_praise = rootView.findViewById(R.id.ll_praise);
            this.iv_praise = rootView.findViewById(R.id.iv_praise);
            this.ll_comment = rootView.findViewById(R.id.ll_comment);
            this.lv_comment = rootView.findViewById(R.id.lv_comment);
            this.tv_praisenum = rootView.findViewById(R.id.tv_praisenum);
            this.rl_voide = rootView.findViewById(R.id.rl_voide);
            this.iv_playing = rootView.findViewById(R.id.iv_playing);
            this.ll_share = rootView.findViewById(R.id.ll_share);
            this.ll_title = rootView.findViewById(R.id.ll_title);
        }

    }
}
