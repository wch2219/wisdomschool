package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SeleteFileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择文件
 */
public class SeleteFileActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_allfile)
    TextView tvAllfile;
    @BindView(R.id.lv_list)
    ListView lvList;
    private MyPopuWindow popuWindow;
    private List<Image> images;
    private List<Image> docs = new ArrayList<>();
    private List<Image> pics = new ArrayList<>();
    private List<Image> mp3s = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_selete_file);
        ButterKnife.bind(this);
        View headView = LayoutInflater.from(ctx).inflate(R.layout.head_seletefile, null);
        lvList.addHeaderView(headView);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择文件");
        showLoading();
        initTabBar(toolBar);
        new Thread() {
            @Override
            public void run() {
                getAllSysFile();
            }
        }.start();

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


    @Override
    protected void initListener() {
            lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.tv_allfile)
    public void onViewClicked() {
        showPopu();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_seletefileadd, null);
        ViewHolderPopu vhpopu = new ViewHolderPopu(view);
        popuWindow = new MyPopuWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popuWindow.setOutsideTouchable(true);
        popuWindow.setFocusable(true);
        backgroundAlpha(0.5f);
        popuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popuWindow.dismiss();
                backgroundAlpha(1);
            }
        });
        popuWindow.showAsDropDown(tvAllfile, -10, 10, Gravity.RIGHT);

    }

    private class ViewHolderPopu implements View.OnClickListener {
        public View rootView;
        public TextView tv_allfile;
        public TextView tv_documentfile;
        public TextView tv_picfile;
        public TextView tv_voicefile;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_allfile = (TextView) rootView.findViewById(R.id.tv_allfile);
            this.tv_documentfile = (TextView) rootView.findViewById(R.id.tv_documentfile);
            this.tv_picfile = (TextView) rootView.findViewById(R.id.tv_picfile);
            this.tv_voicefile = (TextView) rootView.findViewById(R.id.tv_voicefile);
            tv_allfile.setOnClickListener(this);
            tv_documentfile.setOnClickListener(this);
            tv_picfile.setOnClickListener(this);
            tv_voicefile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            popuWindow.dismiss();
            switch (view.getId()) {
                case R.id.tv_allfile:
                    lvList.setAdapter(new SeleteFileAdapter(ctx, images));
                    break;
                case R.id.tv_documentfile:
                    lvList.setAdapter(new SeleteFileAdapter(ctx, docs));
                    break;
                case R.id.tv_picfile:
                    lvList.setAdapter(new SeleteFileAdapter(ctx, pics));
                    break;
                case R.id.tv_voicefile:
                    lvList.setAdapter(new SeleteFileAdapter(ctx, mp3s));
                    break;

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Image image = images.get(i-1);
        int filetype = image.getFiletype();
        wch(filetype+image.getPath());

        back(image.getPath(),image.getSize(),image.getFiletype());
    }
    private void back(String path,String size,int filetype){
        Intent intent = new Intent();
        intent.putExtra("path",path);
        intent.putExtra("size",size);
        intent.putExtra("filetype",filetype);
        setResult(101,intent);
        finish();
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
            if (type.contains("mp3")) {
                image.setPath(path);
                image.setName(name);
                image.setFiletype(2);
                image.setSize(size);
                images.add(image);
                mp3s.add(image);
            } else if (type.contains("MP3")) {
                image.setPath(path);
                image.setName(name);
                image.setFiletype(2);
                image.setSize(size);
                images.add(image);
                mp3s.add(image);
            } else if (type.contains("txt")) {
                image.setPath(path);
                image.setName(name);
                image.setSize(size);
                image.setFiletype(3);
                images.add(image);
                docs.add(image);
            } else if (type.contains("doc")) {
                image.setPath(path);
                image.setName(name);
                image.setSize(size);
                image.setFiletype(4);
                images.add(image);
                docs.add(image);
            }
            else if (type.contains("docx")) {
                image.setPath(path);
                image.setName(name);
                image.setSize(size);
                image.setFiletype(4);
                images.add(image);
                docs.add(image);
            }
            else if (type.contains("jpg")) {
                image.setPath(path);
                image.setName(name);
                image.setFiletype(1);
                image.setSize(size);
                images.add(image);
                pics.add(image);
            } else if (type.contains("jpeg")) {
                image.setPath(path);
                image.setName(name);
                image.setFiletype(1);
                image.setSize(size);
                images.add(image);
                pics.add(image);
            } else {
                continue;
            }
        }
        wch("群补文件" + images.size());
        cursor.close();
        Collections.sort(images, new FileComparator());
        Collections.sort(mp3s, new FileComparator());
        Collections.sort(pics, new FileComparator());
        Collections.sort(docs, new FileComparator());
        handler.sendEmptyMessage(1);
    }

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
