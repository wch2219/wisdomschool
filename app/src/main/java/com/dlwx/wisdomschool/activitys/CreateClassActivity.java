package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.UploadPicUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.UpFileUtiles;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 创建班级
 */
public class CreateClassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.rl_pic)
    RelativeLayout rlPic;
    @BindView(R.id.et_classname)
    EditText etClassname;
    @BindView(R.id.btn_next)
    Button btnNext;
    private int fileid = -1;
    private String fileurl;
    private String cheatgroup;
    private EMGroup group;

    @Override
    protected void initView() {
        cheatgroup = getIntent().getStringExtra("type");
        setContentView(R.layout.activity_create_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

        initTabBar(toolBar);
        if (!TextUtils.isEmpty(cheatgroup)) {
            tvTitle.setText("创建群聊");
        }else{
            tvTitle.setText("创建班级");
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.iv_pic, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pic:
                UploadPicUtiles.showDia(ctx);

                break;
            case R.id.btn_next:
                if (fileid == -1) {
                    if (!TextUtils.isEmpty(cheatgroup)) {
                        Toast.makeText(ctx, "请先上传群徽", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ctx, "请先上传班徽", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                final String classname = etClassname.getText().toString().trim();
                if (TextUtils.isEmpty(classname)) {
                    if (!TextUtils.isEmpty(cheatgroup)) {
                        Toast.makeText(ctx, "请先填写群名称", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ctx, "请先填写班级名称", Toast.LENGTH_SHORT).show();
                    }

                    return;
                }
                if (!TextUtils.isEmpty(cheatgroup)) {
                    //创建群聊
                    final EMGroupOptions option = new EMGroupOptions();
                    option.maxUsers = 200;
                    option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                group = EMClient.getInstance().groupManager().createGroup(classname, classname, new String[]{}, null, option);
                                wch(group.getGroupName());
                                handler.sendEmptyMessage(1);
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }else{
                    Intent intent = new Intent(ctx, ClassPropertyActivity.class);
                    intent.putExtra("classname",classname);
                    intent.putExtra("fileid",fileid);
                    intent.putExtra("filepath",fileurl);
                    startActivity(intent);
                }

                break;
        }
    }
        private void dissolveGroup(){
            new Thread(){
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().groupManager().destroyGroup(group.getGroupId());//需异步处理
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
            finish();
        }else {
            dissolveGroup();
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            Map<String,String> map = new HashMap<>();
            map.put("token",Token);
            map.put("groupid",group.getGroupId());
            map.put("imgid",fileid+"");
            map.put("group_name",group.getGroupName());
            mPreenter.fetch(map,true, HttpUrl.Add_group,"");

        }
    };
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://相机
                UploadPicUtiles.startZoomPic((Activity) ctx,data,300,300,1,1);
                break;
            case 2://相册
                UploadPicUtiles.startZoomPic((Activity) ctx,data,300,300,1,1);
                break;
            case 5://裁剪
                File filePath = UploadPicUtiles.getFilePath(data, ctx);
                wch("path:"+filePath);

                UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                    @Override
                    public void success(Response<String> response) {
                        wch("返回"+response.body());
                        String body = response.body();
                        Gson gson = new Gson();
                        UpPicBean upPicBean = gson.fromJson(body, UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            fileid = upPicBean.getBody().getFileid();
                            fileurl = upPicBean.getBody().getFile();
                            Glide.with(ctx).load(fileurl).into(ivPic);
                        }
                        Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                    }
                });
                UpFileUtiles.start(ctx,filePath,"1",0);

                break;

        }
    }
}
