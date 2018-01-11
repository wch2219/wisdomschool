package com.dlwx.wisdomschool.activitys;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.SpUtiles;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 您的专属二维码
 */
public class ExclusiveCodeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_savetophone)
    Button btnSavetophone;
    @BindView(R.id.iv_code)
    ImageView iv_code;
    @BindView(R.id.ll_code)
    LinearLayout ll_code;
    private String codepath;

    @Override
    protected void initView() {
        codepath = getIntent().getStringExtra("codepath");
        setContentView(R.layout.activity_exclusive_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("您的专属二维码");
        initTabBar(toolBar);
        Glide.with(ctx).load(codepath).into(iv_code);
        Glide.with(ctx).load(sp.getString(SpUtiles.Header_pic,"")).into(ivHead);
        tvName.setText(sp.getString(SpUtiles.Nickname,""));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_savetophone)
    public void onViewClicked() {
        screenshot();
    }


    private void screenshot()
    {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        iv_code.setImageBitmap(bmp);
        if (bmp != null)
        {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "下载链接上次                                                                     .png";

                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                Log.i("wch", "已经保存");
                sendRecord(file);
            } catch (Exception e) {
            }
        }
    }
}
