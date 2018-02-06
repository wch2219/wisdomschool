package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.MermberMessBean;
import com.dlwx.wisdomschool.utiles.GlideuploadUtils;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 联系人信息
 */
public class MemberMessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_remarkname)
    TextView tvRemarkname;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.tv_jurisdiction)
    TextView tvJurisdiction;
    @BindView(R.id.ll_jurisdiction)
    LinearLayout llJurisdiction;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.iv_sendmess)
    ImageView ivSendmess;
    @BindView(R.id.ll_remarkmess)
    LinearLayout llRemarkmess;
    @BindView(R.id.ll_clearclass)
    LinearLayout llClearclass;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.ll_send)
    LinearLayout ll_send;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    private String jcid;
    private MermberMessBean.BodyBean body;
    private String classid;
    private String userid;

    @Override
    protected void initView() {
        jcid = getIntent().getStringExtra("jcid");
        classid = getIntent().getStringExtra("classid");
        userid = getIntent().getStringExtra("userid");
        setContentView(R.layout.activity_member_mess);
        ButterKnife.bind(this);
        register();
    }

    @Override
    protected void initData() {
        tvTitle.setText("联系人信息");
        initTabBar(toolBar);
        int TeacherOrPatriarch = sp.getInt(SpUtiles.TeacherOrPatriarch, 0);
//        if (TeacherOrPatriarch == 1) {
//            llRemarkmess.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.ll_remark, R.id.ll_jurisdiction, R.id.ll_remarkmess, R.id.ll_clearclass,
            R.id.ll_phone,R.id.ll_send})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_remark://班级内名称备注
                break;
            case R.id.ll_jurisdiction://班级内权限
                intent = new Intent(ctx, ClassJurisdictionActivity.class);
                intent.putExtra("classid",classid);
                intent.putExtra("jcid",jcid);
                startActivity(intent);
                break;
            case R.id.ll_remarkmess://备注信息
                startActivity(new Intent(ctx, RemarkMessActivity.class));
                break;
            case R.id.ll_clearclass://请出班级
                deletemember();
                break;
            case R.id.ll_phone://给他打电话
                    call(body.getTelephone());
                break;
            case R.id.ll_send://发送私聊
                intent = new Intent(ctx, ChatActivity.class);
                intent.putExtra("title",body.getJoin_role());
                intent.putExtra(SpUtiles.OtherHeadPic,body.getHeader_pic());
                intent.putExtra(SpUtiles.OtherNickName,body.getJoin_role());
                intent.putExtra(EaseConstant.EXTRA_USER_ID, userid);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                startActivity(intent);
                break;
        }
    }

    /**
     * 请出班级
     */
    private void deletemember() {
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("jcid",jcid);
        mPreenter.fetch(map,true,HttpUrl.DeleteMember,"");
    }

    /**
     * 获取联系人数据
     */
    private void getData() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        if (!TextUtils.isEmpty(jcid)) {

            map.put("jcid", jcid);
        }else{
            map.put("userid",userid);
        }
        mPreenter.fetch(map, true, HttpUrl.LookMemberMess, null);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        if (HttpType == 1) {

            mermbermess(s);
        }else{
            Gson gson = new Gson();
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                finish();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void mermbermess(String s) {
        Gson gson = new Gson();
        MermberMessBean mermberMessBean = gson.fromJson(s, MermberMessBean.class);
        if (mermberMessBean.getCode() == 200) {
            body = mermberMessBean.getBody();
            GlideuploadUtils.glideUPload(ctx, body.getHeader_pic()).into(ivPic);
            tvName.setText(body.getJoin_role());
            tvRemarkname.setText(body.getJoin_role());
            switch (body.getJoin_user_vip()) {
                case 1:
                    tvJurisdiction.setText("普通成员");
                    break;
                case 2:
                    tvJurisdiction.setText("可发消息成员");
                    break;
                case 3:
                    tvJurisdiction.setText("任课老师");
                    break;

            }

        } else {
            Toast.makeText(ctx, mermberMessBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
