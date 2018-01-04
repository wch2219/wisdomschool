package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 班级内权限
 */
public class ClassJurisdictionActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rb_general)
    RadioButton rbGeneral;
    @BindView(R.id.rb_cansend)
    RadioButton rbCansend;
    @BindView(R.id.rb_teach)
    RadioButton rbTeach;
    @BindView(R.id.rb_transferclass)
    RadioButton rbTransferclass;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    private String jcid;
    private String classid;

    @Override
    protected void initView() {
        jcid = getIntent().getStringExtra("jcid");
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_class_jurisdiction);
        ButterKnife.bind(this);
        register();
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级内权限");
        initTabBar(toolBar);
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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_general://普通
                changeJursid("1");
                break;
            case R.id.rb_cansend://可发送消息
                changeJursid("2");
                break;
            case R.id.rb_teach://任课老师

                showDiaDissolveTishi("确认更改任课老师？",getResources().getString(R.string.classjurisdiction2),"3");
                break;
            case R.id.rb_transferclass://转让班级
                showDiaDissolveTishi("确定要解散班级吗？","转让后您的管理权限将自动更改为“任课老师”","4");
                break;

        }
    }

    /**
     * 提示
     */
    private void showDiaDissolveTishi(String title, String mess, final String type) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_jurisdi, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        final AlertDialog show = builder.show();
        Button btn_aff = view.findViewById(R.id.btn_aff);
        TextView tv_close = view.findViewById(R.id.tv_close);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_mess = view.findViewById(R.id.tv_mess);
        if ("3".equals(type)) {
            btn_aff.setText("确定");
        }
        tv_title.setText(title);
        tv_mess.setText(mess);
        btn_aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
                finish();
                if ("4".equals(type)) {
                    Intent intent = new Intent(ctx, IDApproveActivity.class);
                    intent.putExtra("type",type);
                    intent.putExtra("classid",classid);
                    intent.putExtra("jcid",jcid);
                    startActivity(intent);
                }else{
                    changeJursid(type);
                }



            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
    }

    /**
     * 改变权限
     */
    private void changeJursid(String type) {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("jcid",jcid);
        map.put("classid",classid);
        map.put("vip",type);
        mPreenter.fetch(map,true, HttpUrl.changeClassJurisd,"");
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
