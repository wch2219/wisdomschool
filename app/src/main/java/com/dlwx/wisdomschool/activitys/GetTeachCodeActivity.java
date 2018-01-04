package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 获取教师邀请码
 */
public class GetTeachCodeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_schoolname)
    EditText etSchoolname;
    @BindView(R.id.btn_apply)
    Button btnApply;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_get_teach_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("申请教师邀请码");
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
    String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "请输入联系人电话", Toast.LENGTH_SHORT).show();
            return;
        }
    String schoolname = etSchoolname.getText().toString().trim();
        if (TextUtils.isEmpty(schoolname)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "请输入学校名称", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("tel",phone);
        map.put("school",schoolname);
        mPreenter.fetch(map,false, HttpUrl.get_teacher_code,"");

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
}
