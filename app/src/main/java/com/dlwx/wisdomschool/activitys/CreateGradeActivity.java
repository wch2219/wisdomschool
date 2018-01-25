package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SeleteFileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建成绩
 */
public class CreateGradeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private List<Image> images;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_create_grade);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择成绩单");
        initTabBar(toolBar);
//        lvList.setAdapter(new CreateGradeAdapter(ctx));
        showLoading();
        new Thread() {
            @Override
            public void run() {
                getAllSysFile();
            }
        }.start();
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image image = images.get(position);
                Intent inIntent = new Intent();
                inIntent.putExtra("file",image.getPath());
                setResult(1,inIntent);
                finish();
            }
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    private void getAllSysFile() {

        images = new ArrayList<>();
        Uri uri = MediaStore.Files.getContentUri("external");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            Image image = new Image();
            //获取图片的名称
            String path = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
            String name = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
            String size = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
            int position_do = path.lastIndexOf(".");
            if (position_do == -1) continue;
            String type = path.substring(position_do + 1, path.length());
            int doc_type = 0;
            if ("0".equals(size) || size == null)
                continue;
            if (type.contains("xls")) {
                image.setPath(path);
                image.setName(name);
                image.setFiletype(5);
                image.setSize(size);
                images.add(image);
            }
            if (type.contains("xlsx")) {
                image.setPath(path);
                image.setName(name);
                image.setFiletype(5);
                image.setSize(size);
                images.add(image);
            }
        }
        wch(images.size());
        cursor.close();
        Collections.sort(images, new FileComparator());
        handler.sendEmptyMessage(1);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    disLoading();
                    lvList.setAdapter(new SeleteFileAdapter(ctx, images));
                    break;
            }
        }
    };
    private class FileComparator implements Comparator<Image> {
        @Override
        public int compare(Image image, Image t1) {
            File file = new File(image.getPath());
            File file1 = new File(t1.getPath());
            if (file.lastModified() < file1.lastModified()) {
                return 1;//最后修改的照片在前
            } else {
                return -1;
            }
        }
    }
}
