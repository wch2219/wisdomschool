package com.dlwx.wisdomschool.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.UploadPicUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.base.MyApplication;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.PersionMessBean;
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.dlwx.wisdomschool.utiles.UpFileUtiles;
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
    private String exten_code;

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
        if (teacherOrPatriarch == 1) {//老师
            iv_idtype.setVisibility(View.GONE);
        } else {//家长
            tvName.setText("高飞爸爸");
            llSchname.setVisibility(View.GONE);
            tv_idtype.setText("家长");
        }
        getPersionMess();
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
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_head://头像
                UploadPicUtiles.showDia(ctx);
                break;
            case R.id.ll_name://称呼
                intent = new Intent(ctx, ChangeNamedActivity.class);
                startActivityForResult(intent,10);
                break;
            case R.id.ll_schname://所在学校
                startActivityForResult(new Intent(ctx, SchoolAddressActivity.class),11);
                break;
            case R.id.ll_code://二维码
                startActivity(new Intent(ctx, ExclusiveCodeActivity.class).putExtra("codepath",exten_code));
                break;
            case R.id.ll_phone://我的手机
                startActivity(new Intent(ctx, ChangePhoneActivity.class));
                break;
            case R.id.ll_idtype://身份类型
                if (teacherOrPatriarch == 2) {
                    startActivity(new Intent(ctx, ChangeIdTypeActivity.class));
                }
                break;
            case R.id.ll_changepwd://修改密码
                startActivity(new Intent(ctx, ForgetPwdActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        wch(requestCode + ";" + requestCode);
        Map<String,String> map;
        switch (requestCode) {
            case 1://相机
                UploadPicUtiles.startZoomPic((Activity) ctx,data,300,300,1,1);
                break;
            case 2://相册
                UploadPicUtiles.startZoomPic((Activity) ctx,data,300,300,1,1);
                break;
            case 5://裁剪
                File filePath = UploadPicUtiles.getFilePath(data, ctx);
                wch(filePath);
                UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                    @Override
                    public void success(Response<String> response) {
                        Gson gson = new Gson();

                        UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            wch(upPicBean.getBody().getFile());
                            int fileid = upPicBean.getBody().getFileid();
                            Glide.with(ctx).load(upPicBean.getBody().getFile()).into(ivHead);
                            sp.edit().putString(SpUtiles.Header_pic,upPicBean.getBody().getFile()).commit();
                            Map<String,String> map = new HashMap<>();
                            map.put("header_pic",fileid+"");
                            changeMess(map);
                        }else{
                            Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                UpFileUtiles.start(ctx,filePath,"1",0);

                break;
            case 10://昵称
                if (data == null) {

                return;
                }
                String nickName = data.getStringExtra("nickName");
                map = new HashMap<>();
                map.put("nickname",nickName);
                changeMess(map);
                break;
            case 11://学校
                if (data == null) {

                    return;
                }
                String schoolname = data.getStringExtra("schoolname");
                map = new HashMap<>();
                map.put("school_name",schoolname);
                changeMess(map);
                break;


        }
    }

    private void changeMess(Map<String,String> map){
        HttpType = 2;
        map.put("token",Token);
        mPreenter.fetch(map, false,HttpUrl.Edit_info,"");
    }

    @Override
    public void showData(String s) {
      disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 2) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                //更新个人信息
                getPersionMess();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();

        }else{
            PersionMessBean persionMessBean = gson.fromJson(s, PersionMessBean.class);
            if (persionMessBean.getCode() == 200) {
                PersionMessBean.BodyBean body = persionMessBean.getBody();
                PersionMessBean.BodyBean.InfoBean info = body.getInfo();
                sp.edit().putString(SpUtiles.Token,info.getToken()).commit();
                sp.edit().putString(SpUtiles.Nickname,info.getNickname()).commit();
                sp.edit().putString(SpUtiles.Header_pic,info.getHeader_pic()).commit();
                sp.edit().putString(SpUtiles.Userid,info.getUserid()).commit();
                sp.edit().putString(SpUtiles.Telephone,info.getTelephone()).commit();
                sp.edit().putString(SpUtiles.Exten_code,info.getExten_code()).commit();
                exten_code = info.getExten_code();
                MyApplication.Token = info.getToken();
                Glide.with(ctx).load(info.getHeader_pic()).apply(new RequestOptions().error(R.mipmap.icon_zhucetouxiang)).into(ivHead);//头像
                tvName.setText(info.getNickname());//昵称
                tvSchname.setText(info.getSchool_name());//学校名称
                tvPhone.setText(info.getTelephone());//手机号
                if (info.getRole().equals("1")) {
                    tv_idtype.setText("老师");//身份类型
                }else{
                    tv_idtype.setText("家长");//身份类型
                }


            }else{
                Toast.makeText(ctx, persionMessBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取个人信息
     */
    private void getPersionMess() {
        Map<String,String> map =  new HashMap<>();
        HttpType = 1;
        map.put("token",Token);
        mPreenter.fetch(map,true,HttpUrl.MyMess,HttpUrl.MyMess+Token);
    }
}
