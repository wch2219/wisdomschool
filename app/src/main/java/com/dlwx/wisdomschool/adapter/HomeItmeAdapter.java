package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AddActionActivity;
import com.dlwx.wisdomschool.activitys.LookReadActivity;
import com.dlwx.wisdomschool.bean.HomeListBean;
import com.dlwx.wisdomschool.utiles.EmoSwichUtiles;
import com.dlwx.wisdomschool.utiles.LookPic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15/015.
 */

public class HomeItmeAdapter extends BaseRecrviewAdapter {
    private List<HomeListBean.BodyBean> body;
    private int LeftViewType = 2;
    private int RightViewType = 1;

    public HomeItmeAdapter(Context ctx, List<HomeListBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LeftViewType) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.item_home_left
                    , parent, false);
            return new ViewHolderleft(view);
        } else if (viewType == RightViewType) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.item_home_right
                    , parent, false);
            return new ViewHolderRight(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final HomeListBean.BodyBean bodyBean = body.get(position);

        if (holder instanceof ViewHolderleft) {
            final ViewHolderleft holderleft = (ViewHolderleft) holder;
            Glide.with(ctx).load(bodyBean.getHeader_pic()).into(holderleft.iv_leftHead);
            holderleft.tv_leftclassname.setText(bodyBean.getClass_name());
            holderleft.tv_title.setText(bodyBean.getTheme_name());

            int theme = bodyBean.getTheme();
            switch (theme) {
                case 1://班级通知
                    holderleft.tv_joinaction.setVisibility(View.GONE);
                    holderleft.ll_yue.setVisibility(View.GONE);
                    holderleft.ll_url.setVisibility(View.GONE);
                    Glide.with(ctx).load(R.mipmap.icon_noti).into(holderleft.iv_typeleft);
                    break;
                case 2://群组讨论
                    holderleft.tv_joinaction.setVisibility(View.GONE);
                    holderleft.ll_yue.setVisibility(View.GONE);
                    holderleft.ll_url.setVisibility(View.GONE);
                    Glide.with(ctx).load(R.mipmap.icon_diss).into(holderleft.iv_typeleft);
                    break;
                case 3://调查问卷
                    holderleft.tv_joinaction.setVisibility(View.GONE);
                    holderleft.ll_yue.setVisibility(View.GONE);
                    holderleft.ll_url.setVisibility(View.GONE);
                    Glide.with(ctx).load(R.mipmap.icon_diaochawenjuan).into(holderleft.iv_typeleft);
                    break;
                case 4://视频实时纪录
                    holderleft.tv_joinaction.setVisibility(View.GONE);
                    holderleft.ll_yue.setVisibility(View.GONE);
                    holderleft.ll_url.setVisibility(View.GONE);
                    Glide.with(ctx).load(R.mipmap.icon_video).into(holderleft.iv_typeleft);
                    break;
                case 5://活动收集
                    holderleft.tv_joinaction.setVisibility(View.VISIBLE);
                    holderleft.ll_yue.setVisibility(View.GONE);
                    holderleft.ll_url.setVisibility(View.GONE);
                    Glide.with(ctx).load(R.mipmap.icon_actionshouji).into(holderleft.iv_typeleft);
                    break;
                case 6://布置作业
                    holderleft.tv_joinaction.setVisibility(View.GONE);
                    holderleft.ll_url.setVisibility(View.GONE);
                    Glide.with(ctx).load(R.mipmap.icon_zuoye).into(holderleft.iv_typeleft);
                    break;
                case 10://周刊
                    holderleft.tv_joinaction.setVisibility(View.GONE);
                    holderleft.ll_yue.setVisibility(View.GONE);
                    holderleft.ll_url.setVisibility(View.GONE);
                    Glide.with(ctx).load(R.mipmap.icon_zhoukan).into(holderleft.iv_typeleft);
                    break;
                case 7://文章
                    holderleft.tv_joinaction.setVisibility(View.GONE);
                    holderleft.ll_yue.setVisibility(View.VISIBLE);
                    holderleft.ll_url.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(R.mipmap.icon_wenzhang).into(holderleft.iv_typeleft);
                    break;

            }
            EmoSwichUtiles.toSwich(ctx, holderleft.contentleft, bodyBean.getContent());

            final List<String> content_pic = bodyBean.getContent_pic();
            //突破片的数量
            if (content_pic.size() == 1) {

                holderleft.gv_piclistleft.setNumColumns(1);
            } else if (content_pic.size() == 2) {
                holderleft.gv_piclistleft.setNumColumns(2);
            } else {

                holderleft.gv_piclistleft.setNumColumns(3);
            }
            holderleft.gv_piclistleft.setAdapter(new RecordPicListAdapter(ctx, content_pic));

            if (TextUtils.isEmpty(bodyBean.getContent_voice())) {
                holderleft.ll_voiceleft.setVisibility(View.GONE);
            } else {
                holderleft.ll_voiceleft.setVisibility(View.VISIBLE);
//                String time = VoicetranscribeAndPlayUtiles.durationTime(bodyBean.getContent_voice());
//                holderleft.tv_sec.setText(time);
            }
            /**
             * 阅读状态
             */
            String parent_isyue = bodyBean.getParent_isyue();
            if ("1".equals(parent_isyue)) {//已阅
                holderleft.tv_yueType.setText("已阅");
            }else{
                holderleft.tv_yueType.setText("请点");
            }
            holderleft.tv_leftdate.setText(bodyBean.getCreatedtime());
            /*参与活动*/
            holderleft.tv_joinaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, AddActionActivity.class);
                    intent.putExtra("id", bodyBean.getId());
                    intent.putExtra("issend",bodyBean.getIs_send());
                    ctx.startActivity(intent);
                }
            });
            /**
             * 图片点击
             */
            holderleft.gv_piclistleft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<String> imgs1 = content_pic;
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
            holderleft.ll_yue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String yueType = holderleft.tv_yueType.getText().toString().trim();
                    if (yueType.equals("点击")) {
                        holderleft.tv_yueType.setText("已阅");
                        itemOnClickListener.clickYue(bodyBean.getId());
                    }else{
                        holderleft.tv_yueType.setText("已阅");
                    }

                }
            });
            /**
             * 条目点击
             */
            holderleft.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.setOnClick(position);
                }
            });

        } else if (holder instanceof ViewHolderRight) {

            ViewHolderRight holderRight = (ViewHolderRight) holder;
            Glide.with(ctx).load(bodyBean.getHeader_pic()).into(holderRight.iv_righttitpic);
            holderRight.tv_rightname.setText(bodyBean.getClass_name());
            holderRight.tv_title.setText(bodyBean.getTheme_name());
            int theme = bodyBean.getTheme();
            switch (theme) {
                case 1://班级通知
                    Glide.with(ctx).load(R.mipmap.icon_noti).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.GONE);
                    holderRight.ll_url.setVisibility(View.GONE);
                    break;
                case 2://群组讨论
                    Glide.with(ctx).load(R.mipmap.icon_diss).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.GONE);
                    holderRight.ll_url.setVisibility(View.GONE);
                    break;
                case 3://调查问卷
                    Glide.with(ctx).load(R.mipmap.icon_diaochawenjuan).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.GONE);
                    holderRight.ll_url.setVisibility(View.GONE);
                    break;
                case 4://视频实时纪录
                    Glide.with(ctx).load(R.mipmap.icon_video).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.GONE);
                    holderRight.ll_url.setVisibility(View.GONE);
                    break;
                case 5://活动收集
                    Glide.with(ctx).load(R.mipmap.icon_actionshouji).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.GONE);
                    holderRight.ll_url.setVisibility(View.GONE);
                    break;
                case 6://布置作业
                    Glide.with(ctx).load(R.mipmap.icon_zuoye).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.GONE);
                    holderRight.ll_url.setVisibility(View.GONE);
                    break;
                case 7://文章
                    holderRight.ll_url.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(R.mipmap.icon_wenzhang).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.VISIBLE);
                    break;
                case 10://周刊
                    Glide.with(ctx).load(R.mipmap.icon_zhoukan).into(holderRight.iv_type);
                    holderRight.ll_looknumandyueright.setVisibility(View.GONE);
                    holderRight.ll_url.setVisibility(View.GONE);
                    break;
            }

            EmoSwichUtiles.toSwich(ctx, holderRight.tv_content, bodyBean.getContent());
            holderRight.tv_yuenum.setText(bodyBean.getTeacher_isyue());
            final List<String> content_pic = bodyBean.getContent_pic();
            //突破片的数量
            if (content_pic.size() == 1) {

                holderRight.gv_piclist.setNumColumns(1);
            } else if (content_pic.size() == 2) {
                holderRight.gv_piclist.setNumColumns(2);
            } else {

                holderRight.gv_piclist.setNumColumns(3);
            }
            holderRight.gv_piclist.setAdapter(new RecordPicListAdapter(ctx, content_pic));

            if (TextUtils.isEmpty(bodyBean.getContent_voice())) {
                holderRight.ll_voice.setVisibility(View.GONE);
            } else {
                holderRight.ll_voice.setVisibility(View.VISIBLE);
//                String time = VoicetranscribeAndPlayUtiles.durationTime(bodyBean.getContent_voice());
//                holderRight.tv_sec.setText(time);
            }

            holderRight.tv_rightdate.setText(bodyBean.getCreatedtime());
            /**
             * 图片点击
             */
            holderRight.gv_piclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<String> imgs1 = content_pic;
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
             * 眼睛 查看
             *
             */
            holderRight.ll_lookdicusright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, LookReadActivity.class);
                    intent.putExtra("id", bodyBean.getId());
                    ctx.startActivity(intent);
                }
            });

            /**
             * 条目点击
             */
            holderRight.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.setOnClick(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        HomeListBean.BodyBean bodyBean = body.get(position);
        int is_send = bodyBean.getIs_send();
        return is_send == LeftViewType ? LeftViewType : RightViewType;
//        return 1;
    }
    public interface ItemOnClickListener {
        void clickYue(String id);

    }
    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    @Override
    public int getItemCount() {
        return body.size();
    }

    private class ViewHolderleft extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView iv_leftHead;
        public TextView tv_leftclassname;
        public TextView tv_title;
        public ImageView iv_typeleft;
        public TextView contentleft;
        public MyGridView gv_piclistleft;
        public ImageView iv_videoleft;
        public RelativeLayout rl_voide;
        public ImageView iv_viecoplaytype;
        public TextView tv_sec;
        public LinearLayout ll_voiceleft;
        public TextView tv_leftdate;
        public TextView tv_joinaction;
        public LinearLayout ll_yue;
        public TextView tv_yueType;
        public LinearLayout ll_url;
        public ViewHolderleft(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_leftHead = (ImageView) rootView.findViewById(R.id.iv_leftHead);
            this.tv_leftclassname = (TextView) rootView.findViewById(R.id.tv_leftclassname);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_joinaction = (TextView) rootView.findViewById(R.id.tv_joinaction);
            this.iv_typeleft = (ImageView) rootView.findViewById(R.id.iv_typeleft);
            this.contentleft = (TextView) rootView.findViewById(R.id.contentleft);
            this.gv_piclistleft = (MyGridView) rootView.findViewById(R.id.gv_piclistleft);
            this.iv_videoleft = (ImageView) rootView.findViewById(R.id.iv_videoleft);
            this.rl_voide = (RelativeLayout) rootView.findViewById(R.id.rl_voide);
            this.iv_viecoplaytype = (ImageView) rootView.findViewById(R.id.iv_viecoplaytype);
            this.tv_sec = (TextView) rootView.findViewById(R.id.tv_sec);
            this.ll_voiceleft = (LinearLayout) rootView.findViewById(R.id.ll_voiceleft);
            this.tv_leftdate = (TextView) rootView.findViewById(R.id.tv_leftdate);
            this.ll_yue = (LinearLayout) rootView.findViewById(R.id.ll_yue);
            this.ll_url = (LinearLayout) rootView.findViewById(R.id.ll_url);
            this.tv_yueType = (TextView) rootView.findViewById(R.id.tv_yueType);

        }

    }

    private class ViewHolderRight extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_rightname;
        public TextView tv_title;
        public TextView tv_content;
        public MyGridView gv_piclist;
        public ImageView iv_video;
        public ImageView iv_type;
        public RelativeLayout rl_voide;
        public ImageView iv_viecoplaytype;
        public TextView tv_sec;
        public LinearLayout ll_voice;
        public TextView tv_rightdate;
        public ImageView iv_look;
        public LinearLayout ll_looknumandyueright;
        public ImageView iv_righttitpic;
        public LinearLayout ll_right;
        public LinearLayout ll_lookdicusright;
        public LinearLayout ll_url;
        public TextView tv_yuenum;
        public ViewHolderRight(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv_rightname = (TextView) rootView.findViewById(R.id.tv_rightname);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.gv_piclist = (MyGridView) rootView.findViewById(R.id.gv_piclist);
            this.iv_video = (ImageView) rootView.findViewById(R.id.iv_video);
            this.iv_type = (ImageView) rootView.findViewById(R.id.iv_type);
            this.rl_voide = (RelativeLayout) rootView.findViewById(R.id.rl_voide);
            this.iv_viecoplaytype = (ImageView) rootView.findViewById(R.id.iv_viecoplaytype);
            this.tv_sec = (TextView) rootView.findViewById(R.id.tv_sec);
            this.ll_voice = (LinearLayout) rootView.findViewById(R.id.ll_voice);
            this.tv_rightdate = (TextView) rootView.findViewById(R.id.tv_rightdate);
            this.ll_looknumandyueright = (LinearLayout) rootView.findViewById(R.id.ll_looknumandyueright);
            this.iv_righttitpic = (ImageView) rootView.findViewById(R.id.iv_righttitpic);
            this.ll_right = (LinearLayout) rootView.findViewById(R.id.ll_right);
            this.ll_lookdicusright = (LinearLayout) rootView.findViewById(R.id.ll_lookdicusright);
            this.ll_url = (LinearLayout) rootView.findViewById(R.id.ll_url);
            this.tv_yuenum = rootView.findViewById(R.id.tv_yuenum);
        }

    }
}
