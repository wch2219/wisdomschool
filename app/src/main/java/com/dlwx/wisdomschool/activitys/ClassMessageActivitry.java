package com.dlwx.wisdomschool.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.UploadPicUtiles;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.ClassInfoBean;
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SeleteClassDiaUtiles;
import com.dlwx.wisdomschool.utiles.UpPicUtiles;
import com.dlwx.wisdomschool.views.SeleteHeadDiautils;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 班级信息
 */
public class ClassMessageActivitry extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_classname)
    TextView tvClassname;
    @BindView(R.id.ll_classname)
    LinearLayout llClassname;
    @BindView(R.id.tv_school)
    TextView tvSchool;
    @BindView(R.id.ll_schoolname)
    LinearLayout llSchoolname;
    @BindView(R.id.tv_realclass)
    TextView tvRealclass;
    @BindView(R.id.ll_realclass)
    LinearLayout llRealclass;
    @BindView(R.id.swich)
    Switch swich;
    private String classid;

    @Override
    protected void initView() {
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_class_message_activitry);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("班级信息");
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classid", classid);
        mPreenter.fetch(map, true, HttpUrl.get_classinfo, HttpUrl.get_classinfo + Token + classid);
    }

    @Override
    protected void initListener() {
        swich.setOnCheckedChangeListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_head, R.id.ll_classname, R.id.ll_schoolname, R.id.ll_realclass})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_head://班徽
                showSeleteHead();
                break;
            case R.id.ll_classname://班级名称
                intent = new Intent(ctx, ChangeClassNameActivity.class);
                intent.putExtra("classname",tvClassname.getText().toString());
                startActivityForResult(intent, 10);
                break;
            case R.id.ll_schoolname://所属学校
                intent = new Intent(ctx, SchoolAddressActivity.class);
                startActivityForResult(intent, 11);
                break;
            case R.id.ll_realclass://真实班级
                showSeleteClassDia();
                break;
        }
    }

    /**
     * 选择班徽
     */
    private void showSeleteHead() {
        SeleteHeadDiautils headDiautils = new SeleteHeadDiautils(ctx);
        headDiautils.init();

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        //TODO
        Map<String,String>  map = new HashMap<>();
        map.put("token", Token);
        if (b) {
            map.put("is_tel_find", "1");

        }else{
            map.put("is_tel_find", "2");
        }
        setClassMess(map);
    }

    /**
     * 显示选择班级的窗体
     */
    private void showSeleteClassDia() {
        SeleteClassDiaUtiles seleteClassDiaUtiles = new SeleteClassDiaUtiles(ctx);
        seleteClassDiaUtiles.init(ctx);
        seleteClassDiaUtiles.setSeleteResult(seleteResult);
    }

    private SeleteClassDiaUtiles.SeleteResult seleteResult = new SeleteClassDiaUtiles.SeleteResult() {
        @Override
        public void setResult(String s) {
            if (!TextUtils.isEmpty(s)) {
                tvRealclass.setText(s);
              Map<String,String>  map = new HashMap<>();
                map.put("token", Token);
                map.put("true_classname", s);
                setClassMess(map);
            }
        }
    };

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            classmess(s, gson);
        } else {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void classmess(String s, Gson gson) {
        ClassInfoBean classInfoBean = gson.fromJson(s, ClassInfoBean.class);
        if (classInfoBean.getCode() == 200) {
            ClassInfoBean.BodyBean body = classInfoBean.getBody();
            Glide.with(ctx).load(body.getClass_pic()).into(ivHead);
            tvClassname.setText(body.getClass_name());
            tvSchool.setText(body.getSchool_name());
            tvRealclass.setText(body.getTrue_classname());
            if (body.getIs_tel_find() == 1) {// 	1允许通过手机号找班级，2不允许
                swich.setChecked(true);
            } else {
                swich.setChecked(false);
            }
        } else {
            Toast.makeText(ctx, classInfoBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Map<String, String> map;
        switch (requestCode) {
            case 2://相册
                if (data == null) {
                    return;
                }
                UploadPicUtiles.startZoomPic((Activity) ctx, data, 300, 300, 1, 1);
                break;
            case 5://裁剪成功后
                File filePath = UploadPicUtiles.getFilePath(data, ctx);
                UpPicUtiles.setBackInterface(new UpPicUtiles.BackInterface() {
                    @Override
                    public void success(Response<String> response) {
                        wch("返回" + response.body());
                        String body = response.body();
                        Gson gson = new Gson();
                        UpPicBean upPicBean = gson.fromJson(body, UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            String fileurl = upPicBean.getBody().getFile();
                            Glide.with(ctx).load(fileurl).into(ivHead);
                            Map<String, String> map = new HashMap<>();
                            map.put("token", Token);
                            map.put("header_pic", upPicBean.getBody().getFileid() + "");
                            setClassMess(map);
                        }
                        Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                    }
                });
                UpPicUtiles.start(ctx, filePath);
                break;

            case 10://班级名称
                if (data == null) {
                    return;
                }

                String classname = data.getStringExtra("classname");
                map = new HashMap<>();
                map.put("token", Token);
                map.put("class_name", classname);
                setClassMess(map);
                break;
            case 11://学校名称
                if (data == null) {
                    return;
                }
                String schoolname = data.getStringExtra("schoolname");
                map = new HashMap<>();
                map.put("token", Token);
                map.put("school_name", schoolname);
                setClassMess(map);
                break;
        }
    }

    private void setClassMess(Map<String, String> map) {
        HttpType = 2;
        map.put("classid",classid);
        mPreenter.fetch(map, false, HttpUrl.set_classinfo, "");
    }

}
