package com.dlwx.wisdomschool.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.BottomNavigationViewHelper;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.fragments.ChatFragment;
import com.dlwx.wisdomschool.fragments.HomeFragment;
import com.dlwx.wisdomschool.fragments.MyFragment;
import com.dlwx.wisdomschool.fragments.RecordFragment;
import com.dlwx.wisdomschool.fragments.WorkFragment;
import com.dlwx.wisdomschool.interfac.SoftKeyBoard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,SoftKeyBoard.SoftKeyBoardListener{
    @BindView(R.id.bottom_navigation_container)
    BottomNavigationView bottomNavigationContainer;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
//        AmapUtils.initialization(ctx);
        fragments.add(new HomeFragment());
        fragments.add(new WorkFragment());
//        fragments.add(new ClassFragment());
        fragments.add(new RecordFragment());
        fragments.add(new ChatFragment());
        fragments.add(new MyFragment());
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationContainer);
        bottomNavigationContainer.setOnNavigationItemSelectedListener(this);
        changeFragment(0);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA
                }, 1002);
        }
    }

    @Override
    protected void initListener() {
        SoftKeyBoard.setSoftKeyBoardListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home://首页
                changeFragment(0);
                break;
            case R.id.item_work://作业
                changeFragment(1);
                break;
//                 case R.id.item_class://班级
//                changeFragment(2);
//                break;
                 case R.id.item_record://成长纪录
                changeFragment(2);
                break;
                 case R.id.item_chat://聊天
                changeFragment(3);
                break;
                 case R.id.item_my://我的
                changeFragment(4);
                break;
        }
        return true;
    }
    private Fragment fragment;
    private FragmentTransaction transaction;
    private Fragment lastFragment;

    private void changeFragment(int i) {

        transaction = getSupportFragmentManager().beginTransaction();
        // 上一个不为空 隐藏上一个
        if (lastFragment != null&& lastFragment != fragments.get(i)) {
            transaction.hide(lastFragment);
//            transaction.remove(lastFragment);
        }
        fragment = fragments.get(i);
        // fragment不能重复添加 // 添加过 显示 没有添加过 就隐藏
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_content, fragment);
        }
        transaction.commitAllowingStateLoss();
        lastFragment = fragment;
    }

    @Override
    public void showorhind(int i) {
        bottomNavigationContainer.setVisibility(i);
    }


}
