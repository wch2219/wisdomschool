package com.dlwx.wisdomschool.activitys;

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
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassDescMemberAdapter;
import com.dlwx.wisdomschool.adapter.ClassDescTeachAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 加入的班级详情
 */
public class MyJoinClassDescActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_className)
    TextView tvClassName;
    @BindView(R.id.tv_scoolName)
    TextView tvScoolName;
    @BindView(R.id.tv_classmember)
    TextView tvClassmember;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.rl_classmess)
    RelativeLayout rlClassmess;
    @BindView(R.id.ll_groupup)
    LinearLayout llGroupup;
    @BindView(R.id.ll_classfile)
    LinearLayout llClassfile;
    @BindView(R.id.btn_lookhis)
    Button btnLookhis;
    @BindView(R.id.tv_idname)
    TextView tvIdname;
    @BindView(R.id.ll_myclassid)
    LinearLayout llMyclassid;
    @BindView(R.id.ll_lookstudentgrade)
    LinearLayout llLookstudentgrade;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.gv_listmeber)
    MyGridView gvListmeber;
    @BindView(R.id.rl_lookall)
    RelativeLayout rlLookall;
    private PopupWindow popupWindow;
    private ClassDescTeachAdapter classDescTeachAdapter;
    private ClassDescMemberAdapter classDescMemberAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_join_class_desc);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        toolBar.setBackgroundResource(R.color.blue);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_jiantoubaise);
//        classDescTeachAdapter = new ClassDescTeachAdapter(ctx);
//        gvList.setAdapter(classDescTeachAdapter);
//        classDescMemberAdapter = new ClassDescMemberAdapter(ctx);
//        gvListmeber.setAdapter(classDescMemberAdapter);
    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(teachOnItemClick);
        gvListmeber.setOnItemClickListener(memberOnItemClick);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.iv_back, R.id.ll_groupup, R.id.ll_classfile, R.id.btn_lookhis, R.id.ll_myclassid, R.id.ll_lookstudentgrade, R.id.rl_lookall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://退出班级
                showPopuBack();
                break;
            case R.id.ll_groupup://智慧成长
                break;
            case R.id.ll_classfile://班级文件
                startActivity(new Intent(ctx, ClassFileActivity.class));
                break;
            case R.id.btn_lookhis://查看历史
                startActivity(new Intent(ctx,ClassHistoryNewsActivitry.class));

                break;
            case R.id.ll_myclassid://我的班级身份
                showPopuClassId();
                break;
            case R.id.ll_lookstudentgrade://查看学生成绩
                startActivity(new Intent(ctx,LookStudentGradeActivity.class));
                break;
            case R.id.rl_lookall://查看全部成员

                break;
        }
    }
    /**
     * 教师
     */
    private AdapterView.OnItemClickListener teachOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //tag == 1是自己 0是其他成员或老师
            Intent intent = new Intent(ctx,MyJoinClassPersionMessActivity.class);
            if (i == 0) {
                intent.putExtra("tag",1);
            }else{
                intent.putExtra("tag",0);
            }
            startActivity(intent);

        }
    };

    /**
     *成员
     */
    private AdapterView.OnItemClickListener memberOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //tag == 1是自己 0是其他成员或老师
            Intent intent = new Intent(ctx,MyJoinClassPersionMessActivity.class);
            intent.putExtra("tag",0);
            startActivity(intent);
        }
    };
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
     * 申请退出班级
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
        popupWindow.showAtLocation(tvClassmember, Gravity.BOTTOM, 0, 0);
        viewHolderPopu.tv_backclass.setOnClickListener(this);
        viewHolderPopu.tv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_backclass://申请退出班级
                popupWindow.dismiss();
            startActivity(new Intent(ctx,ApplyBackClassActivity.class));
                break;
            case R.id.tv_current://选择当前身份
                popupWindow.dismiss();
                break;
            case R.id.tv_otherid://选择其他身份
                popupWindow.dismiss();
                startActivity(new Intent(ctx,SeleteOtherIdActivity.class));
                break;

            case R.id.tv_close://关闭弹窗
                popupWindow.dismiss();
                break;
        }
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
            this.tv_backclass.setText("申请退出班级");
            this.tv_dissolveclass.setVisibility(View.GONE);

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