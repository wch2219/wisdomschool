package com.dlwx.wisdomschool.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ClassManageActivity;
import com.dlwx.wisdomschool.activitys.FeedbackActivity;
import com.dlwx.wisdomschool.activitys.LoginInActivity;
import com.dlwx.wisdomschool.activitys.PatriarchExamActivity;
import com.dlwx.wisdomschool.activitys.PersionMessActivity;
import com.dlwx.wisdomschool.activitys.SetWorkTimeActivity;
import com.dlwx.wisdomschool.activitys.SettActivity;
import com.dlwx.wisdomschool.activitys.SysNotifitionActivity;
import com.dlwx.wisdomschool.activitys.TeacherInvitaCodeActivity;
import com.dlwx.wisdomschool.activitys.VideoExplainActivity;
import com.dlwx.wisdomschool.activitys.WishDomBagActivity;
import com.dlwx.wisdomschool.adapter.MyFoodAdapter;
import com.dlwx.wisdomschool.base.MyApplication;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.hyphenate.chat.EMClient;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的
 */

public class MyFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.lv_list)
    ListView lvList;
    Unbinder unbinder;
    private ViewHolder headvh;
    private AlertDialog diaShow;
    private String[] foodarr;
    private int teacherOrPatriarch;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        View headView = LayoutInflater.from(ctx).inflate(R.layout.my_head, null);
        headvh = new ViewHolder(headView);
        lvList.addHeaderView(headView);
    }

    private int[] pics;

    @Override
    protected void initDate() {
        teacherOrPatriarch = sp.getInt(SpUtiles.TeacherOrPatriarch, 1);
        if (teacherOrPatriarch == 1) {//老师
            foodarr = getResources().getStringArray(R.array.my_food);
            headvh.tv_schoolname.setText(sp.getString(SpUtiles.SchoolName,""));
            headvh.tv_name.setText("老师");
            pics = new int[]{

                    R.mipmap.icon_wdwdshijian
                    , R.mipmap.icon_wdwdxitong
                    , R.mipmap.icon_wdwdfankui
                    , R.mipmap.icon_wdwdjsyqm

                    , R.mipmap.icon_wdwdbanben
                    , R.mipmap.icon_wdwdshezhi
                    , R.mipmap.icon_wdwdtuichu
            };
        } else {//家长
            foodarr = getResources().getStringArray(R.array.my_food_patriarch);
            headvh.tv_schoolname.setText("");
            headvh.tv_name.setText("家长");
            pics = new int[]{
                    R.mipmap.icon_wdwdkaoshi,
                    R.mipmap.icon_wdwdxitong,
                    R.mipmap.icon_wdwdfankui,
                    R.mipmap.icon_wdwdbanben,
                    R.mipmap.icon_wdwdshezhi,
                    R.mipmap.icon_wdwdtuichu,
            };
        }
        lvList.setHeaderDividersEnabled(true);
        lvList.setAdapter(new MyFoodAdapter(ctx, foodarr, pics));
    }

    @Override
    public void onResume() {
        String nickName = sp.getString(SpUtiles.Nickname, "");
        String headpic = sp.getString(SpUtiles.Header_pic, "");
        headvh.tv_name.setText(nickName);
        String extencode = sp.getString(SpUtiles.Exten_code, "");
        Glide.with(ctx).load(headpic).apply(new RequestOptions().error(R.mipmap.icon_zhucetouxiang)).into(headvh.iv_head);
        Glide.with(ctx).load(extencode).apply(new RequestOptions().error(R.mipmap.icon_wdwdewm)).into(headvh.iv_code);
        super.onResume();
    }

    @Override
    protected void initListener() {
        headvh.my_mess.setOnClickListener(this);
        headvh.ll_favorite.setOnClickListener(this);
        headvh.ll_wishbag.setOnClickListener(this);
        headvh.ll_video.setOnClickListener(this);

        lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.my_mess://个人信息
                startActivity(new Intent(ctx, PersionMessActivity.class));
                break;
            case R.id.ll_favorite://我的收藏 》》》》我的班级
                startActivity(new Intent(ctx, ClassManageActivity.class));
//                startActivity(new Intent(ctx, MyFacoriteActivity.class));
                break;
            case R.id.ll_wishbag://智慧书包
                startActivity(new Intent(ctx, WishDomBagActivity.class));
                break;
            case R.id.ll_video://视频讲解
                startActivity(new Intent(ctx, VideoExplainActivity.class));
                break;
            case R.id.tv_close:
                diaShow.dismiss();
                break;
            case R.id.tv_aff://退出登录
                diaShow.dismiss();
                EMClient.getInstance().logout(true);//退出环信登录
                getActivity().finish();
                sp.edit().putString(SpUtiles.Token, "").commit();
                sp.edit().putString(SpUtiles.Nickname, "").commit();
                sp.edit().putString(SpUtiles.Header_pic, "").commit();
                sp.edit().putString(SpUtiles.Userid, "").commit();
                sp.edit().putString(SpUtiles.Telephone, "").commit();
                sp.edit().putInt(SpUtiles.TeacherOrPatriarch, 0).commit();
                MyApplication.Token = "";
                startActivity(new Intent(ctx, LoginInActivity.class));
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (teacherOrPatriarch == 1) {
            switch (i) {

                case 1://设置工作时间
                    startActivity(new Intent(ctx, SetWorkTimeActivity.class));
                    break;

                case 2://系统通知S
                    startActivity(new Intent(ctx, SysNotifitionActivity.class));
                    break;
                case 3://反馈建议
                    startActivity(new Intent(ctx, FeedbackActivity.class));
                    break;
                case 4://生成教师邀请码
                    startActivity(new Intent(ctx, TeacherInvitaCodeActivity.class));
                    break;
//                case 5://申请学校培训
//                    startActivity(new Intent(ctx, SchoolTrainActivity.class));
//                    break;
                case 5://版本信息
                    Beta.checkUpgrade();
                    break;
                case 6://设置
                    startActivity(new Intent(ctx, SettActivity.class));
                    break;
                case 7://退出登录

                    showDia();
                    break;
            }
        } else {//家长
            switch (i) {

                case 1://家长考试
                    startActivity(new Intent(ctx, PatriarchExamActivity.class));
                    break;
                case 2://系统通知
                    startActivity(new Intent(ctx, SysNotifitionActivity.class));
                    break;
                case 3://反馈建议
                    startActivity(new Intent(ctx, FeedbackActivity.class));
                    break;
                case 4://版本信息
                    break;
                case 5://设置+++++++++
                    startActivity(new Intent(ctx, SettActivity.class));
                    break;
                case 6://退出登录
                    showDia();
                    break;
            }
        }
    }

    /**
     * 退出登录
     */
    private void showDia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_back, null);
        builder.setView(view);
        ViewHolderDia vh = new ViewHolderDia(view);
        vh.tv_close.setOnClickListener(this);
        vh.tv_aff.setOnClickListener(this);
        diaShow = builder.show();
    }


    private class ViewHolderDia {
        public View rootView;
        public TextView tv_close;
        public TextView tv_aff;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_aff = (TextView) rootView.findViewById(R.id.tv_aff);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class ViewHolder {
        public View rootView;
        public ImageView iv_head;
        public TextView tv_name;
        public TextView tv_schoolname;
        public ImageView iv_code;
        public RelativeLayout my_mess;
        public LinearLayout ll_favorite;
        public LinearLayout ll_wishbag;
        public LinearLayout ll_video;


        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_head = (ImageView) rootView.findViewById(R.id.iv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_schoolname = (TextView) rootView.findViewById(R.id.tv_schoolname);
            this.iv_code = (ImageView) rootView.findViewById(R.id.iv_code);
            this.my_mess = (RelativeLayout) rootView.findViewById(R.id.my_mess);
            this.ll_favorite = (LinearLayout) rootView.findViewById(R.id.ll_favorite);
            this.ll_wishbag = (LinearLayout) rootView.findViewById(R.id.ll_wishbag);
            this.ll_video = (LinearLayout) rootView.findViewById(R.id.ll_video);
        }

    }
}
