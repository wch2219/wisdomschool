package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.fragments.ClassFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 班级管理
 */
public class ClassManageActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.fl_classactivity)
    FrameLayout flClass;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_manage);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("班级管理");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_classactivity, new ClassFragment());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.rl_add)
    public void onViewClicked() {
        showGradePopu();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showGradePopu() {
        View popuView = LayoutInflater.from(ctx).inflate(R.layout.popu_grademanage, null);
        TextView tv_grademanage = popuView.findViewById(R.id.tv_grademanage);
        final PopupWindow popupWindow = new PopupWindow(popuView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f; //0.0-1.0
       getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f; //0.0-1.0
                getWindow().setAttributes(lp);
            }
        });
        popupWindow.showAsDropDown(rlAdd, -10, 10, Gravity.RIGHT);
        tv_grademanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, GradeManageActivity.class));
                popupWindow.dismiss();
            }
        });
    }
}
