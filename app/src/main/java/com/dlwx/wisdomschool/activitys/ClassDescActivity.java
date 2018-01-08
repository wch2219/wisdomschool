package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassDescMemberAdapter;
import com.dlwx.wisdomschool.adapter.ClassDescTeachAdapter;
import com.dlwx.wisdomschool.bean.ClassDescBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 班级详情
 */
public class ClassDescActivity extends BaseActivity {
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_className)
    TextView tvClassName;
    @BindView(R.id.tv_scoolName)
    TextView tvScoolName;
    @BindView(R.id.tv_classmember)
    TextView tvClassmember;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.rl_classmess)
    RelativeLayout rlClassmess;
    @BindView(R.id.ll_sendnotifi)
    LinearLayout llSendnotifi;
    @BindView(R.id.ll_discussion)
    LinearLayout llDiscussion;
    @BindView(R.id.ll_survey)
    LinearLayout llSurvey;
    @BindView(R.id.ll_activity)
    LinearLayout llActivity;
    @BindView(R.id.btn_lookhis)
    Button btnLookhis;
    @BindView(R.id.ll_growup)
    LinearLayout llGrowup;
    @BindView(R.id.ll_file)
    LinearLayout llFile;
    @BindView(R.id.ll_membersinvite)
    LinearLayout llMembersinvite;
    @BindView(R.id.ll_inout)
    LinearLayout llInout;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.gv_listmeber)
    MyGridView gvListmeber;
    @BindView(R.id.tv_idname)
    TextView tvIdname;
    @BindView(R.id.ll_myclassid)
    LinearLayout llMyclassid;
    @BindView(R.id.ll_lookstudentgrade)
    LinearLayout llLookstudentgrade;
    @BindView(R.id.ll_patriarch)
    LinearLayout llPatriarch;
    @BindView(R.id.ll_teach)
    LinearLayout llTeach;
    @BindView(R.id.tv_teach1)
    LinearLayout tvTeach1;
    @BindView(R.id.rl_lookall)
    RelativeLayout rlLookall;
    @BindView(R.id.sw_check)
    Switch swCheck;
    @BindView(R.id.tv_allsize)
    TextView tv_allsize;
    private PopupWindow popupWindow;
    private ClassDescTeachAdapter classDescTeachAdapter;
    private ClassDescMemberAdapter classDescMemberAdapter;
    private int teacherOrPatriarch;
    private String classid;
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    private List<ClassDescBean.BodyBean.AddTeacherBean> add_teacher;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        classid = intent.getStringExtra("classid");
        setContentView(R.layout.activity_class_desc);
        ButterKnife.bind(this);
        teacherOrPatriarch = sp.getInt(SpUtiles.TeacherOrPatriarch, 1);
        gvListmeber.setFocusable(false);
        gvList.setFocusable(false);
        if (teacherOrPatriarch == 1) {//老师
            llPatriarch.setVisibility(View.GONE);
            llTeach.setVisibility(View.VISIBLE);
            tvTeach1.setVisibility(View.VISIBLE);
            rlLookall.setVisibility(View.VISIBLE);
        } else {//家长
            llPatriarch.setVisibility(View.VISIBLE);
            llTeach.setVisibility(View.GONE);
            tvTeach1.setVisibility(View.GONE);
            rlLookall.setVisibility(View.VISIBLE);
        }

        register();
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        toolBar.setBackgroundResource(R.color.blue);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_jiantoubaise);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getClassDesc();
    }

    private int HttpType;

    /**
     * 获取班级详情
     */
    private void getClassDesc() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classid", classid);
        mPreenter.fetch(map, true, HttpUrl.classdesc, HttpUrl.classdesc + classid + Token);

    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(teachOnItemClick);
        gvListmeber.setOnItemClickListener(memberOnItemClick);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {//班级详情
            classdesc(s, gson);
        }
    }

    private void classdesc(String s, Gson gson) {
        ClassDescBean classDescBean = gson.fromJson(s, ClassDescBean.class);
        if (classDescBean.getCode() == 200) {
            super.showData(s);
            ClassDescBean.BodyBean body = classDescBean.getBody();
            Glide.with(ctx).load(body.getClass_pic()).into(ivHead);//班徽
            tvClassName.setText(body.getClass_name());//班级名称
            tvScoolName.setText(body.getSchool_name());//学校名称
            tvClassmember.setText("班级号：" + body.getClass_no());//班级号
            Glide.with(ctx).load(body.getClass_qrcode()).into(ivQrcode);//班级二维码
            if (body.getIs_teacher_open() == 1) {//1任课老师消息互见，2不互见
                swCheck.setChecked(true);
            } else {
                swCheck.setChecked(false);
            }
            ClassDescBean.BodyBean.CreateTeacherBean create_teacher = body.getCreate_teacher();
            add_teacher = body.getAdd_teacher();
            classDescTeachAdapter = new ClassDescTeachAdapter(ctx, create_teacher, add_teacher);
            gvList.setAdapter(classDescTeachAdapter);
            add_user = body.getAdd_user();
            tv_allsize.setText("查看全部" + add_user.size() + "名成员");
            classDescMemberAdapter = new ClassDescMemberAdapter(ctx, add_user);
            gvListmeber.setAdapter(classDescMemberAdapter);

        } else {
            Toast.makeText(ctx, classDescBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 教师
     */
    private AdapterView.OnItemClickListener teachOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) {//我的
                startActivity(new Intent(ctx, PersionMessActivity.class));
            } else if (i == add_teacher.size() + 1) {//最后一个添加教师
                startActivity(new Intent(ctx, InviteTeachActivity.class).putExtra("classid", classid));

            } else {//其他成员的
                String jcid = add_teacher.get(i - 1).getJcid();
                skipMemberMess(jcid);
            }
        }
    };

    private void skipMemberMess(String jcid) {
        Intent intent = new Intent(ctx, MemberMessActivity.class);
        intent.putExtra("jcid", jcid);
        intent.putExtra("classid", classid);
        startActivity(intent);
    }

    ;
    /**
     * 成员
     */
    private AdapterView.OnItemClickListener memberOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (add_user.size() > 3) {
                if (i == 3) {
                    startActivity(new Intent(ctx, MyClassMemberActivity.class));
                } else {
                    String jcid = add_user.get(i).getJcid();
                    skipMemberMess(jcid);
                }
            } else if (add_user.size() > 0) {
                if (i == add_user.size()) {
                    startActivity(new Intent(ctx, MyClassMemberActivity.class));
                } else {
                    String jcid = add_user.get(i).getJcid();
                    skipMemberMess(jcid);
                }
            } else {
                startActivity(new Intent(ctx, MyClassMemberActivity.class));
            }
        }
    };

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.rl_classmess, R.id.ll_sendnotifi, R.id.ll_discussion, R.id.ll_survey,
            R.id.ll_activity, R.id.btn_lookhis, R.id.ll_growup, R.id.ll_file, R.id.ll_membersinvite,
            R.id.ll_inout, R.id.ll_share, R.id.iv_back, R.id.ll_myclassid, R.id.ll_lookstudentgrade, R.id.rl_lookall})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.rl_classmess://查看班级信息
                startActivity(new Intent(ctx, ClassMessageActivitry.class).putExtra("classid", classid));
                break;
            case R.id.ll_sendnotifi://发通知
                startActivity(new Intent(ctx, SendNotifiActivity.class));
                break;
            case R.id.ll_discussion://开讨论
                startActivity(new Intent(ctx, SendNotifiActivity.class));
                break;
            case R.id.ll_survey://做调查
                startActivity(new Intent(ctx, SendNotifiActivity.class));
                break;
            case R.id.ll_activity://办活动
                startActivity(new Intent(ctx, SendNotifiActivity.class));
                break;
            case R.id.btn_lookhis://查看历史
                startActivity(new Intent(ctx, ClassHistoryNewsActivitry.class));
                break;
            case R.id.ll_growup://智慧成长
                break;
            case R.id.ll_file://班级文件
                intent = new Intent(ctx, ClassFileActivity.class);
                intent.putExtra("classid", classid);
                startActivity(intent);
                break;
            case R.id.ll_membersinvite://成员邀请
                startActivity(new Intent(ctx, InviteMemberActivity.class));
                break;
            case R.id.ll_inout://入退班申请
                startActivity(new Intent(ctx, InOutClassActivity.class).putExtra("classid", classid));
                break;
//            case R.id.iv_inviteteach://教师邀请
//
//                break;
//            case R.id.iv_invite://成员
//
//                break;
            case R.id.ll_share://成员分享
                startActivity(new Intent(ctx, MemberShareActivity.class).putExtra("adduser", (Serializable) add_user));
                break;
            case R.id.iv_back://退群解散
                showPopuBack();
                break;
            case R.id.ll_myclassid://我的班级身份
                showPopuClassId();
                break;
            case R.id.ll_lookstudentgrade://查看学生成绩
                startActivity(new Intent(ctx, LookStudentGradeActivity.class));
                break;
            case R.id.rl_lookall:
                register();
                intent = new Intent(ctx, LookAllMemberActivity.class);
                intent.putExtra("adduser", (Serializable) add_user);
                intent.putExtra("classid", classid);
                startActivity(intent);
                break;

        }
    }

    /**
     * 选择您在班级中的身份
     */
    private void showPopuClassId() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_affclassid, null);
        ViewHolderClassId viewHolderPopu = new ViewHolderClassId(view);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        viewHolderPopu.tv_current.setOnClickListener(this);
        viewHolderPopu.tv_otherid.setOnClickListener(this);
        viewHolderPopu.tv_close.setOnClickListener(this);
        popupWindow.showAtLocation(tvClassmember, Gravity.BOTTOM, 0, 0);

    }

    /**
     * 退群，解散群
     */
    private void showPopuBack() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_backflock, null);
        ViewHolderPopu viewHolderPopu = new ViewHolderPopu(view);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        popupWindow.showAtLocation(tv_number, Gravity.BOTTOM, 0, 0);
        viewHolderPopu.tv_backclass.setOnClickListener(this);
        viewHolderPopu.tv_dissolveclass.setOnClickListener(this);
        viewHolderPopu.tv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_backclass://退出班级
                popupWindow.dismiss();
                showDiaTishi();
                break;
            case R.id.tv_dissolveclass://解散班级
                popupWindow.dismiss();
                showDiaDissolveTishi();
                break;
            case R.id.tv_current://选择当前身份
                popupWindow.dismiss();
                break;
            case R.id.tv_otherid://选择其他身份
                popupWindow.dismiss();
                startActivity(new Intent(ctx, SeleteOtherIdActivity.class));
                break;
            case R.id.tv_close:
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 解散班级提示
     */
    private void showDiaDissolveTishi() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_dissolve, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        final AlertDialog show = builder.show();
        Button btn_aff = view.findViewById(R.id.btn_aff);
        TextView tv_close = view.findViewById(R.id.tv_close);
        btn_aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
                Intent intent = new Intent(ctx, IDApproveActivity.class);
                intent.putExtra("flag","解散班级");
                intent.putExtra("classid",classid);
                startActivity(intent);

            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
    }

    /**
     * 退出班级提示
     */
    private void showDiaTishi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_tishi, null);
        builder.setView(view);
        final AlertDialog show = builder.show();
        Button btn_aff = view.findViewById(R.id.btn_aff);
        btn_aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
    }

    private class ViewHolderPopu {
        public View rootView;
        public TextView tv_backclass;
        public TextView tv_dissolveclass;
        public TextView tv_close;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_backclass = (TextView) rootView.findViewById(R.id.tv_backclass);
            this.tv_dissolveclass = (TextView) rootView.findViewById(R.id.tv_dissolveclass);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
        }

    }

    public static class ViewHolderClassId {
        public View rootView;
        public TextView tv_current;
        public TextView tv_otherid;
        public TextView tv_close;

        public ViewHolderClassId(View rootView) {
            this.rootView = rootView;
            this.tv_current = (TextView) rootView.findViewById(R.id.tv_current);
            this.tv_otherid = (TextView) rootView.findViewById(R.id.tv_otherid);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
        }

    }
}
