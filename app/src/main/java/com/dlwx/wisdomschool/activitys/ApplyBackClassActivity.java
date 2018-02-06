package com.dlwx.wisdomschool.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.ClassDescBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 申请退出班级
 */
public class ApplyBackClassActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_membernum)
    TextView tvMembernum;
    @BindView(R.id.tv_classname)
    TextView tv_classname;
    @BindView(R.id.rb_cause1)
    RadioButton rbCause1;
    @BindView(R.id.rb_cause2)
    RadioButton rbCause2;
    @BindView(R.id.rb_cause3)
    RadioButton rbCause3;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.et_content)
    EditText et_content;
    private ClassDescBean.BodyBean body;

    @Override
    protected void initView() {
        body = (ClassDescBean.BodyBean) getIntent().getSerializableExtra("body");
        setContentView(R.layout.activity_apply_back_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("申请退班");
        initTabBar(toolBar);
        Glide.with(ctx).load(body.getClass_pic()).into(ivPic);
        tv_classname.setText(body.getClass_name());
        tvClassnum.setText(body.getClass_no());
        tvMembernum.setText(body.getAdd_user().size() + "");
    }

    @Override
    protected void initListener() {
        rgGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
         content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(ctx, "理由不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (content.length()<=6) {
            Toast.makeText(ctx, "理由字数不能少于6个", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classid", body.getCnid());
        map.put("reason", this.content);
        mPreenter.fetch(map,false, HttpUrl.TuiClass,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
            finish();
        }
        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
    }

    private String content = "";

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_cause1:
                content = rbCause1.getText().toString().trim();
                et_content.setText(content);
                break;
            case R.id.rb_cause2:
                content = rbCause2.getText().toString().trim();
                et_content.setText(content);
                break;
            case R.id.rb_cause3:
                content = rbCause3.getText().toString().trim();
                et_content.setText(content);
                break;

        }
    }
}
