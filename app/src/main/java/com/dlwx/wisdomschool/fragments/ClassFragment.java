package com.dlwx.wisdomschool.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ClassDescActivity;
import com.dlwx.wisdomschool.activitys.MyJoinClassDescActivity;
import com.dlwx.wisdomschool.utiles.SpUtiles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 班级
 */

public class ClassFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.fl_class)
    FrameLayout flClass;
    @BindView(R.id.ll_teach)
    LinearLayout llTeach;
    @BindView(R.id.btn_addclasspatriarch)
    Button btnAddclasspatriarch;
    @BindView(R.id.rl_entrypatriarch)
    RelativeLayout rlEntrypatriarch;
    @BindView(R.id.lv_listpatriarch)
    ListView lvListpatriarch;
    @BindView(R.id.btn_createpatriarch)
    Button btnCreatepatriarch;
    @BindView(R.id.ll_noentrypatriarch)
    LinearLayout llNoentrypatriarch;
    @BindView(R.id.ll_patriarch)
    LinearLayout llPatriarch;
    Unbinder unbinder;
    private List<Fragment> fragments = new ArrayList<>();
    private int teacherOrPatriarch;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_class;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
        teacherOrPatriarch = sp.getInt(SpUtiles.TeacherOrPatriarch, 1);
        if (teacherOrPatriarch == 1) {
            fragments.add(new CreateClassFragment());
            fragments.add(new AddClassFragment());
            changeFragment(0);
            llPatriarch.setVisibility(View.GONE);
            llTeach.setVisibility(View.VISIBLE);
        } else {
            llTeach.setVisibility(View.GONE);
            llPatriarch.setVisibility(View.VISIBLE);
            rlEntrypatriarch.setVisibility(View.GONE);
            llNoentrypatriarch.setVisibility(View.VISIBLE);
//            MeAddCLassAdapter meaddCLassAdapter = new MeAddCLassAdapter(ctx);
//            lvListpatriarch.setAdapter(meaddCLassAdapter);
//            lvListpatriarch.setOnItemClickListener(this);
//            ivAdd.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }



    private Fragment fragment;
    private FragmentTransaction transaction;
    private Fragment lastFragment;

    private void changeFragment(int i) {

        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // 上一个不为空 隐藏上一个
        if (lastFragment != null && lastFragment != fragments.get(i)) {
            transaction.hide(lastFragment);
//            transaction.remove(lastFragment);
        }

        fragment = fragments.get(i);
        // fragment不能重复添加 // 添加过 显示 没有添加过 就隐藏
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_class, fragment);
        }
        transaction.commitAllowingStateLoss();
        lastFragment = fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.tv_create, R.id.tv_add, R.id.btn_addclasspatriarch, R.id.btn_createpatriarch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_create://我创建的班级(老师)
                tvCreate.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvAdd.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗

                changeFragment(0);
                break;
            case R.id.tv_add://我加入的班级（老师）
                tvCreate.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                tvAdd.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                changeFragment(1);
                break;
            case R.id.btn_addclasspatriarch://加入班级（家长）
                break;
            case R.id.btn_createpatriarch://加入班级（家长）
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 2) {//可发消息
            startActivity(new Intent(ctx, ClassDescActivity.class));
        }else{//不可发消息
            startActivity(new Intent(ctx, MyJoinClassDescActivity.class));
        }
    }
}
