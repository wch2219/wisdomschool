package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.SpUtiles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人信息
 */
public class PersionMessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_schname)
    TextView tvSchname;
    @BindView(R.id.ll_schname)
    LinearLayout llSchname;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_idtype)
    TextView tv_idtype;
    @BindView(R.id.ll_idtype)
    LinearLayout llIdtype;
    @BindView(R.id.ll_changepwd)
    LinearLayout llChangepwd;
    @BindView(R.id.iv_idtype)
    ImageView iv_idtype;
    private int teacherOrPatriarch;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_persion_mess);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的信息");
        initTabBar(toolBar);
        teacherOrPatriarch = sp.getInt(SpUtiles.TeacherOrPatriarch, 0);
        if (teacherOrPatriarch == 0) {//老师
            iv_idtype.setVisibility(View.GONE);
        }else{//家长
            tvName.setText("高飞爸爸");
            llSchname.setVisibility(View.GONE);
            tv_idtype.setText("家长");
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_head, R.id.ll_name, R.id.ll_schname, R.id.ll_code, R.id.ll_phone, R.id.ll_idtype, R.id.ll_changepwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head://头像
                break;
            case R.id.ll_name://称呼
                startActivity(new Intent(ctx,ChangeNamedActivity.class));
                break;
            case R.id.ll_schname://所在学校
                startActivity(new Intent(ctx,SchoolAddressActivity.class));
                break;
            case R.id.ll_code://二维码
                startActivity(new Intent(ctx,ExclusiveCodeActivity.class));
                break;
            case R.id.ll_phone://我的手机
                startActivity(new Intent(ctx,ChangePhoneActivity.class));
                break;
            case R.id.ll_idtype://身份类型
                if (teacherOrPatriarch == 1) {
                    startActivity(new Intent(ctx,ChangeIdTypeActivity.class));
                }
                break;
            case R.id.ll_changepwd://修改密码
                startActivity(new Intent(ctx,ForgetPwdActivity.class));
                break;
        }
    }
}
