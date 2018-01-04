package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.ClassDescBean;
import com.dlwx.wisdomschool.bean.FindClassBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 成员分享
 */
public class MemberShareActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_classcode)
    TextView tvClasscode;
    @BindView(R.id.tv_peoplenum)
    TextView tvPeoplenum;
    @BindView(R.id.tv_teachname)
    TextView tvTeachname;
    @BindView(R.id.ll_sharemember)
    LinearLayout ll_sharemember;
    @BindView(R.id.et_seach)
    EditText et_seach;
    @BindView(R.id.ll_class)
    LinearLayout ll_class;
    private List<ClassDescBean.BodyBean.AddUserBean> add_user;
    private List<FindClassBean.BodyBean> body;
    private String jcid;
    private String cnid;

    @Override
    protected void initView() {
        add_user = (List<ClassDescBean.BodyBean.AddUserBean>) getIntent().getSerializableExtra("adduser");
        setContentView(R.layout.activity_member_share);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("成员分享");
        tvNum.setText("全班"+add_user.size()+"人");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_share, R.id.tv_check, R.id.ll_sharemember,R.id.ll_class})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                showDia();
                break;
            case R.id.tv_check:
                checkClass();
                break;
            case R.id.ll_sharemember:
                startActivityForResult(new Intent(ctx,WillShareMemberActivity.class),1);
                break;
            case R.id.ll_class:

                break;
        }
    }
    /**
     * 查找班级
     */
    private void checkClass() {
        String seach = et_seach.getText().toString().trim();
        if (TextUtils.isEmpty(seach)) {
            Toast.makeText(ctx, "请输入目标班级班级号", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpType = 1;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("code",seach);
        mPreenter.fetch(map,true, HttpUrl.findClass,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            checkClass(s, gson);
        }else{
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }


    }

    private void checkClass(String s, Gson gson) {
        FindClassBean findClassBean = gson.fromJson(s, FindClassBean.class);
        if (findClassBean.getCode() == 200) {
            body = findClassBean.getBody();
            if (body != null&& body.size()!=0) {
                ll_class.setVisibility(View.VISIBLE);
                FindClassBean.BodyBean bodyBean = body.get(0);
                Glide.with(ctx).load(bodyBean.getClass_pic()).into(ivPic);
                tvClassnum.setText(bodyBean.getClass_name());
                tvClasscode.setText(bodyBean.getClass_no());
                tvPeoplenum.setText(bodyBean.getTotal_user());
                tvTeachname.setText(bodyBean.getTeacher_name());
                cnid = bodyBean.getCnid();
            }else{
                ll_class.setVisibility(View.GONE);
                Toast.makeText(ctx, findClassBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{
            ll_class.setVisibility(View.GONE);
            Toast.makeText(ctx, findClassBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_classshare
                ,null);
        AlertDialog.Builder builder =  new AlertDialog.Builder(ctx);
        builder.setView(view);
        final AlertDialog show = builder.show();
        Button btn_sett = view.findViewById(R.id.btn_sett);
        TextView tv_nosett = view.findViewById(R.id.tv_nosett);
        btn_sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(cnid)) {
                    Toast.makeText(MemberShareActivity.this, "请先查找目标班级", Toast.LENGTH_SHORT).show();
                    return;
                }
                show.dismiss();
                HttpType = 2;
                Map<String,String> map = new HashMap<>();
                map.put("token",Token);
                map.put("jcid",jcid);
                map.put("end_classid",cnid);
                mPreenter.fetch(map,true,HttpUrl.batch_adduser,"");
            }
        });
        tv_nosett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode){
                    case 1:
                        if (data == null) {
                            return;
                        }
                        jcid = data.getStringExtra("jcid");
                        break;
                }
    }
}
