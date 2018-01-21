package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.ActionDescBean;
import com.dlwx.wisdomschool.utiles.EmoSwichUtiles;
import com.dlwx.wisdomschool.utiles.LookPic;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/21/021.
 */

public class AddActionAdapter extends BaseRecrviewAdapter {
    private  ActionDescBean.BodyBean body;
    private final List<ActionDescBean.BodyBean.PinglunBean> pinglun;
    public  final int TYPE_HEADER = 1;
    public AddActionAdapter(Context ctx,  ActionDescBean.BodyBean body) {
        super(ctx);
        this.body = body;
        if (body != null) {

            pinglun = body.getPinglun();
        }else{
            pinglun = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == TYPE_HEADER){
           View headView = LayoutInflater.from(ctx).inflate(R.layout.head_addaction, parent,false);
            return new ViewHolderHead(headView);
        }else{

            View view = LayoutInflater.from(ctx).inflate(R.layout.item_recordlist, parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_HEADER) {//头布局
            ViewHolderHead vhHead = (ViewHolderHead) holder;
            Glide.with(ctx).load(body.getHeader_pic()).into(vhHead.iv_leftHead);
            vhHead.tv_name.setText(body.getClass_name() + "(" + body.getNickname() + ")");
            vhHead.tv_type.setText(body.getTheme_name());
            List<String> content_pic = body.getContent_pic();
            //突破片的数量
            if (content_pic.size() == 1) {

                vhHead.gv_piclistleft.setNumColumns(1);
            } else if (content_pic.size() == 2) {
                vhHead.gv_piclistleft.setNumColumns(2);
            } else {

                vhHead.gv_piclistleft.setNumColumns(3);
            }
            vhHead.gv_piclistleft.setAdapter(new RecordPicListAdapter(ctx, content_pic));
            if (TextUtils.isEmpty(body.getContent_voice())) {
                vhHead.ll_voiceleft.setVisibility(View.GONE);
            } else {
                vhHead.ll_voiceleft.setVisibility(View.VISIBLE);
            }
            /**
             * 图片点击
             */
            vhHead.gv_piclistleft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<String> imgs1 = body.getContent_pic();
                    List<Image> images = new ArrayList<>();
                    for (int i = 0; i < imgs1.size(); i++) {
                        Image image = new Image();
                        image.setPath(imgs1.get(i));
                        image.setOldposition(i);
                        images.add(image);
                    }
                    LookPic.showPic(ctx, parent, images, position);
                }
            });

            EmoSwichUtiles.toSwich(ctx, vhHead.contentleft, body.getContent());

        }else {

            final ViewHolder vh = (ViewHolder) holder;
            vh.ll_start.setVisibility(View.VISIBLE);
            vh.id_flowlayout.setVisibility(View.GONE);
            final ActionDescBean.BodyBean.PinglunBean pinglunBean = pinglun.get(position-1);
            //头像
            Glide.with(ctx).load(pinglunBean.getHeader_pic()).into(vh.iv_pic);
            //昵称
            vh.tv_name.setText(pinglunBean.getNickname());
            vh.tv_time.setText(pinglunBean.getPl_time());
            //突破片的数量
            if (pinglunBean.getContent_img().size() == 1) {

                vh.gv_list.setNumColumns(1);
            } else if (pinglunBean.getContent_img().size() == 2) {
                vh.gv_list.setNumColumns(2);
            } else {
                vh.gv_list.setNumColumns(3);
            }
//        //判断是是视频还是图片
//        if (TextUtils.isEmpty(pinglunBean.getVideo())) {
//            vh.gv_list.setVisibility(View.VISIBLE);
//            vh.rl_voide.setVisibility(View.GONE);
//        }else{
//            vh.gv_list.setVisibility(View.GONE);
//            vh.rl_voide.setVisibility(View.VISIBLE);
//            Glide.with(ctx).asBitmap().load(bodyBean.getVideo()).apply(new RequestOptions().centerCrop()).into(vh.iv_video);
//        }
            //展示图片列表
            List<String> imgs = pinglunBean.getContent_img();
            vh.gv_list.setAdapter(new RecordPicListAdapter(ctx, imgs));
            //语音
//        if (TextUtils.isEmpty(bodyBean.getVoice())) {
//            vh.rl_viceo.setVisibility(View.GONE);
//
//        }else{
//            vh.rl_viceo.setVisibility(View.VISIBLE);
////            String time = VoicetranscribeAndPlayUtiles.durationTime(bodyBean.getVoice());
////            vh.tv_viceotime.setText(time);
//        }

            //文字简介
            EmoSwichUtiles.toSwich(ctx, vh.tv_connect, pinglunBean.getPl_content());
            //赞
        final int is_dianzan = pinglunBean.getIs_dianzan();
        if (is_dianzan == 2) {
            Glide.with(ctx).load(R.mipmap.icon_czjlzana).into(vh.iv_praise);
        }else{
            Glide.with(ctx).load(R.mipmap.icon_czjlzan).into(vh.iv_praise);
        }
        vh.tv_praisenum.setText("赞("+pinglunBean.getZan_num()+")");
        //评论列表
            final List<ActionDescBean.BodyBean.PinglunBean.PinglunContetBean> pinglun = pinglunBean.getPinglun();
            if (pinglun != null) {
            vh.lv_comment.setAdapter(new ActionCommentListAdapter(ctx,pinglun));
        }
            /**
             * 视频播放
             */
//        vh.rl_voide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ctx, VideoPlayActivity.class);
//                intent.putExtra("path", bodyBean.getVideo());
//                ctx.startActivity(intent);
//            }
//        });
            //语音播放
//        vh.rl_viceo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                vh.iv_playing.setImageResource(R.drawable.anim_viceo_play);
//                VoicetranscribeAndPlayUtiles.play(vh.iv_playing,bodyBean.getVoice());
//            }
//        });
            /**
             * 图片点击
             */
            vh.gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<String> imgs1 = pinglunBean.getContent_img();
                    List<Image> images = new ArrayList<>();
                    for (int i = 0; i < imgs1.size(); i++) {
                        Image image = new Image();
                        image.setPath(imgs1.get(i));
                        image.setOldposition(i);
                        images.add(image);
                    }
                    LookPic.showPic(ctx, parent, images, position);
                }
            });
/**
 * 点击赞
 */
            vh.ll_praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (pinglunBean.getIs_dianzan() == 2) {
                        Glide.with(ctx).load(R.mipmap.icon_czjlzan).into(vh.iv_praise);
                        windowListener.praise(pinglunBean.getIcid(),pinglunBean.getIrid());
                        pinglunBean.setIs_dianzan(1);
                        pinglunBean.setZan_num(pinglunBean.getZan_num()+1);
                        vh.tv_praisenum.setText("赞("+(pinglunBean.getZan_num())+")");
                    }else{
                        pinglunBean.setIs_dianzan(2);
                        pinglunBean.setZan_num(pinglunBean.getZan_num()-1);
                        Glide.with(ctx).load(R.mipmap.icon_czjlzana).into(vh.iv_praise);
                        windowListener.praise(pinglunBean.getIcid(),pinglunBean.getIrid());
                        vh.tv_praisenum.setText("赞("+(pinglunBean.getZan_num())+")");
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
                  windowListener.send(pinglunBean.getIcid(), pinglunBean.getIrid(), position);

                }
            });
            vh.ll_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    windowListener.pingstar(pinglunBean.getIcid(), pinglunBean.getIrid(), position);
                }
            });
//            vh.lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    RecordListBean.BodyBean.PinglunHuifu pinglunHuifu = pinglun_huifu.get(position);
//
//                    windowListener.show();
//                    if (TextUtils.isEmpty(pinglunHuifu.getHfid())) {
//                        if (pinglunHuifu.getMy_id().equals(pinglunHuifu.getPlid())) {
//
//                            return;
//                        }
//                        windowListener.send(bodyBean.getId(),pinglunHuifu.getPlid());
//                    }else{
//                        if (pinglunHuifu.getMy_id().equals(pinglunHuifu.getHfid())) {
//
//                            return;
//                        }
//                        windowListener.send(bodyBean.getId(),pinglunHuifu.getHfid());
//                    }
//                }
//            });
            /**
             * 点击弹出分享
             */
        vh.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowListener.share(position-1,body.getId(),"");
            }
        });
//        vh.ll_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ctx, RecordDescActivity.class);
//                intent.putExtra("bodyBean",pinglunBean);
//                ctx.startActivity(intent);
//            }
//        });
//        vh.tv_connect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ctx, RecordDescActivity.class);
//                intent.putExtra("bodyBean",pinglunBean);
//                ctx.startActivity(intent);
//            }
//        });
        }
    }

    @Override
    public int getItemCount() {
        return pinglun.size()+1;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
       else {
            return super.getItemViewType(position);
        }
    }
    public interface ShowInputWindowListener{
        void show();
        void send(String icid,String irid,int pos);
        void praise(String icid,String irid);
        void pingstar(String icid,String irid,int pos);
        void share(int postion,String recorid,String shareurl);
    }
    public ShowInputWindowListener windowListener;

    public void setWindowListener(ShowInputWindowListener windowListener) {
        this.windowListener = windowListener;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_classname;
        public LinearLayout ll_title;
        public TextView tv_time;
        public ImageView iv_share;
        public LinearLayout ll_share;
        public MyGridView gv_list;
        public ImageView iv_video;
        public RelativeLayout rl_voide;
        public ImageView iv_playing;
        public TextView tv_viceotime;
        public RelativeLayout rl_viceo;
        public TagFlowLayout id_flowlayout;
        public TextView tv_connect;
        public ImageView iv_praise;
        public TextView tv_praisenum;
        public LinearLayout ll_praise;
        public LinearLayout ll_comment;
        public MyListView lv_comment;
        public LinearLayout ll_list;
        public LinearLayout ll_start;
        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_classname = (TextView) rootView.findViewById(R.id.tv_classname);
            this.ll_title = (LinearLayout) rootView.findViewById(R.id.ll_title);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.iv_share = (ImageView) rootView.findViewById(R.id.iv_share);
            this.ll_share = (LinearLayout) rootView.findViewById(R.id.ll_share);
            this.gv_list = (MyGridView) rootView.findViewById(R.id.gv_list);
            this.iv_video = (ImageView) rootView.findViewById(R.id.iv_video);
            this.rl_voide = (RelativeLayout) rootView.findViewById(R.id.rl_voide);
            this.iv_playing = (ImageView) rootView.findViewById(R.id.iv_playing);
            this.tv_viceotime = (TextView) rootView.findViewById(R.id.tv_viceotime);
            this.rl_viceo = (RelativeLayout) rootView.findViewById(R.id.rl_viceo);
            this.id_flowlayout = (TagFlowLayout) rootView.findViewById(R.id.id_flowlayout);
            this.tv_connect = (TextView) rootView.findViewById(R.id.tv_connect);
            this.iv_praise = (ImageView) rootView.findViewById(R.id.iv_praise);
            this.tv_praisenum = (TextView) rootView.findViewById(R.id.tv_praisenum);
            this.ll_praise = (LinearLayout) rootView.findViewById(R.id.ll_praise);
            this.ll_comment = (LinearLayout) rootView.findViewById(R.id.ll_comment);
            this.lv_comment = (MyListView) rootView.findViewById(R.id.lv_comment);
            this.ll_list = (LinearLayout) rootView.findViewById(R.id.ll_list);
            this.ll_start = rootView.findViewById(R.id.ll_start);
        }

    }

    private class ViewHolderHead extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView iv_leftHead;
        public TextView tv_name;
        public TextView tv_type;
        public ImageView iv_typeleft;
        public TextView contentleft;
        public MyGridView gv_piclistleft;
        public ImageView iv_videoleft;
        public RelativeLayout rl_voide;
        public ImageView iv_viecoplaytype;
        public TextView tv_sec;
        public LinearLayout ll_voiceleft;
        public TextView tv_endime;

        public ViewHolderHead(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_leftHead = (ImageView) rootView.findViewById(R.id.iv_leftHead);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.iv_typeleft = (ImageView) rootView.findViewById(R.id.iv_typeleft);
            this.contentleft = (TextView) rootView.findViewById(R.id.contentleft);
            this.gv_piclistleft = (MyGridView) rootView.findViewById(R.id.gv_piclistleft);
            this.iv_videoleft = (ImageView) rootView.findViewById(R.id.iv_videoleft);
            this.rl_voide = (RelativeLayout) rootView.findViewById(R.id.rl_voide);
            this.iv_viecoplaytype = (ImageView) rootView.findViewById(R.id.iv_viecoplaytype);
            this.tv_sec = (TextView) rootView.findViewById(R.id.tv_sec);
            this.ll_voiceleft = (LinearLayout) rootView.findViewById(R.id.ll_voiceleft);
            this.tv_endime = (TextView) rootView.findViewById(R.id.tv_endime);
        }

    }
}

