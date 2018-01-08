package com.dlwx.wisdomschool.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.wisdomschool.R;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hyphenate.util.FileUtils.getMIMEType;

/**
 * 显示word文档
 */
public class ReadWordActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.m_web_view)
    FrameLayout webview;
    @BindView(R.id.tv_otherapp)
    TextView tvOtherApp;
    private String path;
    private TbsReaderView readerView;
    private String filename;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_read_word);
        ButterKnife.bind(this);
        path = getIntent().getStringExtra("path");
        filename = getIntent().getStringExtra("filename");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            openFile(path);
        }

    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(filename);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    /**
     * 打开文件
     */
    private void openFile(String path) {
        readerView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        //通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        //传递文件路径
        bundle.putString("filePath", path);
        //加载插件保存的路径
        bundle.putString("tempPath", Environment.getExternalStorageDirectory() + File.separator + "temp");
        //加载文件前的初始化工作,加载支持不同格式的插件
        boolean b = readerView.preOpen(getFileType(path), false);

        if (b) {
            readerView.openFile(bundle);
        }
        webview.addView(readerView);
    }

    @Override
    protected void onDestroy() {
        readerView.onStop();
        super.onDestroy();
    }

    /***
     * 获取文件类型
     *
     * @param path 文件路径
     * @return 文件的格式
     */
    private String getFileType(String path) {
        String str = "";

        if (TextUtils.isEmpty(path)) {
            return str;
        }
        int i = path.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = path.substring(i + 1);
        return str;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            openFile(path);
        }
    }

    /**
     * 其他应用打开
     */
    @OnClick(R.id.tv_otherapp)
    public void onViewClicked() {

        openFile(ctx,new File(path));
    }
    /**
     * 调用系统应用打开文件
     * @param context
     * @param file
     */
    public static void openFile(Context context, File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        //跳转
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtiles.LogE("FileUtil:"+ e);
            Toast.makeText(context, "找不到打开此文件的应用！", Toast.LENGTH_SHORT).show();
        }
    }
}
