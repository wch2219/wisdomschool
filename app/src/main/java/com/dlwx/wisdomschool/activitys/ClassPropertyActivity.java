package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.CreateClassSuccessBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SeleteClassDiaUtiles;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 班级属性
 */
public class ClassPropertyActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_schoolname)
    TextView tvSchoolname;
    @BindView(R.id.ll_school)
    LinearLayout llSchool;
    @BindView(R.id.ll_class)
    LinearLayout llClass;
    @BindView(R.id.tv_classname)
    TextView tv_classname;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    private int fileid;
    private String classname;
    private String filepath;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        classname = intent.getStringExtra("classname");
        filepath = intent.getStringExtra("filepath");
        fileid = intent.getIntExtra("fileid", -1);
        setContentView(R.layout.activity_class_property);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级属性");
        initTabBar(toolBar);
        Glide.with(ctx).load(filepath).into(ivPic);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_school, R.id.ll_class, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_school:
                startActivityForResult(new Intent(ctx, SchoolAddressActivity.class), 11);
                break;
            case R.id.ll_class:
                showSeleteClassDia();
                break;
            case R.id.btn_complete:

                complete();
                break;
        }
    }

    private void complete() {
        String schoolallname = tvSchoolname.getText().toString().trim();
        if (TextUtils.isEmpty(schoolallname)) {
            Toast.makeText(ctx, "请先选择所属学校", Toast.LENGTH_SHORT).show();
            return;
        }
        String classallname = tv_classname.getText().toString().trim();
        if (TextUtils.isEmpty(classallname)) {
            Toast.makeText(ctx, "请先选择真实班级", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> map =  new HashMap<>();
        map.put("token",Token);
        map.put("class_name",classname);
        map.put("class_pic",fileid+"");
        map.put("school_name",schoolallname);
        map.put("true_classname",classallname);
        mPreenter.fetch(map,false, HttpUrl.createclass,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        CreateClassSuccessBean createClassSuccessBean = gson.fromJson(s, CreateClassSuccessBean.class);
        if (createClassSuccessBean.getCode() == 200) {
            finish();
            Intent intent = new Intent(ctx, CreateClassCompleteActivity.class);
            intent.putExtra("classcode",createClassSuccessBean.getBody());
            intent.putExtra("classname",classname);
            intent.putExtra("filepath",filepath);
            startActivity(intent);
            finish();
        }
        Toast.makeText(ctx, createClassSuccessBean.getResult(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示选择班级的窗体W
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

                tv_classname.setText(s);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11:
                if (data != null) {

                    String schoolname = data.getStringExtra("schoolname");
                    tvSchoolname.setText(schoolname);
                }
                break;
        }
    }
}
