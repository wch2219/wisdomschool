package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
 * 注册选择身份
 */
public class Register1Activity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_teacher)
    RelativeLayout rlTeacher;
    @BindView(R.id.rl_patriarch)
    RelativeLayout rlPatriarch;
    @BindView(R.id.tv_getteachcode)
    TextView tv_getteachcode;
    private ViewHolder vhpopu;
    private PopupWindow popu;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register1);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.register);
        initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.rl_teacher, R.id.rl_patriarch, R.id.tv_getteachcode})
    public void onViewClicked(View view) {
        Intent intent = new Intent(ctx, RegisterActivity.class);
        switch (view.getId()) {
            case R.id.rl_teacher:
//                getTeachCode();
                intent.putExtra("role", "1");
                startActivity(intent);
                finish();
                break;
            case R.id.rl_patriarch:
                intent.putExtra("role", "2");
                startActivity(intent);
                finish();
                break;
            case R.id.tv_getteachcode:
                    startActivity(new Intent(ctx,GetTeachCodeActivity.class));
                break;
        }
    }

    /**
     * 获取老师邀请码
     */
    private void getTeachCode() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_teach_code, null);
        vhpopu = new ViewHolder(view);
        popu = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        popu.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        popu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        popu.showAtLocation(tvTitle, Gravity.CENTER, 0, 0);
        vhpopu.btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
                    case R.id.btn_submit:
                        String code = vhpopu.et_code.getText().toString().trim();
                        if (TextUtils.isEmpty(code)) {
                            vibrator.vibrate(50);
                            Toast.makeText(ctx, "邀请码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Map<String,String> map = new HashMap<>();
                        map.put("code",code);
                        mPreenter.fetch(map,true, HttpUrl.check_teacher_code,"");


                        break;
                }
    }

    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
            finish();
            Intent intent = new Intent(ctx, RegisterActivity.class);
            intent.putExtra("role", "1");
            startActivity(intent);
            finish();
            popu.dismiss();
        }
        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();

    }

    private class ViewHolder {
        public View rootView;
        public EditText et_code;
        public Button btn_submit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_code = (EditText) rootView.findViewById(R.id.et_code);
            this.btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        }

    }
}
