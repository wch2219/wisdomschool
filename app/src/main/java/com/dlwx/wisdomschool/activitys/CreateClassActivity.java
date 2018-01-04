package com.dlwx.wisdomschool.activitys;

import android.app.Activity;
import android.content.Intent;
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
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.utiles.UpPicUtiles;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void initView() {
        setContentView(R.layout.activity_create_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("创建班级");
        initTabBar(toolBar);
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
                    Toast.makeText(ctx, "请先上传班徽", Toast.LENGTH_SHORT).show();
                    return;
                }
                String classname = etClassname.getText().toString().trim();
                if (TextUtils.isEmpty(classname)) {
                    Toast.makeText(ctx, "请先填写班级名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ctx, ClassPropertyActivity.class);
                intent.putExtra("classname",classname);
                intent.putExtra("fileid",fileid);
                intent.putExtra("filepath",fileurl);
                startActivity(intent);
                break;
        }
    }

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

                UpPicUtiles.setBackInterface(new UpPicUtiles.BackInterface() {
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
                UpPicUtiles.start(ctx,filePath);

                break;

        }
    }
}
