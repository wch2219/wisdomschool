package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassFileAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.ClassFileBean;
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.utiles.DownFileSave;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.LookPic;
import com.dlwx.wisdomschool.utiles.MediaPlayUtils;
import com.dlwx.wisdomschool.utiles.UpFileUtiles;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 班级文件
 */
public class ClassFileActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_entry)
    RelativeLayout rlEntry;
    @BindView(R.id.ablv_list)
    ListView ablvList;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.ll_noentry)
    LinearLayout llNoentry;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.smallLabel)
    SmartRefreshLayout refreshLayout;
    private MyPopuWindow popuWindow;
    private String classid;
    private ViewHolderDia vhDia;
    private AlertDialog diaShow;
    private List<ClassFileBean.BodyBean.ListBean> fileList;
    private List<Image> images;
    private List<Image> mp3s;

    @Override
    protected void initView() {
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_class_file);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级文件");
        initTabBar(toolBar);
        getFileList();
    }

    @Override
    protected void initListener() {
        initrefresh(refreshLayout, true);
        ablvList.setOnItemClickListener(this);
    }

    @Override
    public void downOnRefresh() {
        getFileList();
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.iv_add)
    public void onViewClicked() {
        showPopu();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ClassFileBean.BodyBean.ListBean listBean = fileList.get(i);
        int type = listBean.getType();//1图片，2音视频，3文档
        Intent intent;
        switch (type) {
            case 1://图片
                //大图显示
                LookPic.showPic(ctx,tvTitle,images,i);
                break;
            case 2://MP3

                MediaPlayUtils playUtils = new MediaPlayUtils(ctx);
                playUtils.showPopu(ablvList,mp3s,i);
                break;
            case 3://txt
               readFile(listBean);
                break;
            case 4://doc
                readFile(listBean);
                break;

            case 99:
                intent = new Intent(ctx, PageFileDescActivity.class);
                intent.putExtra("name", listBean.getName());
                intent.putExtra("cfid", listBean.getCfid());
                intent.putExtra("classid", classid);
                startActivity(intent);
                break;

        }

    }


    private void readFile(final ClassFileBean.BodyBean.ListBean listBean){
        DownFileSave.setDownFIleBack(new DownFileSave.DownFIleBack() {
            @Override
            public void back(File file) {
                Intent intent = new Intent(ctx,ReadWordActivity.class);
                wch("下载文件"+file);
                intent.putExtra("path",file+"");
                intent.putExtra("filename",listBean.getName());
                startActivity(intent);
            }
        });
        DownFileSave.down(ctx,listBean.getFile_pic());
    }
    /**
     * 获得文件列表
     */
    private void getFileList() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classid", classid);
//        map.put("type",type);
//        map.put("name",name);
        mPreenter.fetch(map, true, HttpUrl.getClassFile, HttpUrl.getClassFile + Token + classid);

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            fileList(s, gson);
        } else if (HttpType == 2) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                getFileList();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 文件列表
     *
     * @param s
     * @param gson
     */
    private void fileList(String s, Gson gson) {
        ClassFileBean classFileBean = gson.fromJson(s, ClassFileBean.class);
        if (classFileBean.getCode() == 200) {
            long totalsize = 1073741824;
            ClassFileBean.BodyBean body = classFileBean.getBody();
            long use_size = body.getUse_size();
            progressbar.setMax((int) totalsize);
            progressbar.setProgress((int) (use_size));
            long m = use_size / 1024;
            long mm = use_size % 1024;
            wch(mm);
            long g = m / 1024;
            long gg = m % 1024;
            if (g > 0) {
                tvSize.setText(g + "." + gg + "G/1G");
            } else if (m > 0) {
                tvSize.setText(m + "." + mm + "M/1G");
            } else {
                tvSize.setText(use_size + "KB/1G");
            }
            fileList = body.getList();
            images = new ArrayList<>();
            mp3s = new ArrayList<>();
            for (int i = 0; i < body.getList().size(); i++) {
                int type = body.getList().get(i).getType();
                if (type == 1) {
                    Image image = new Image();
                    image.setPath(body.getList().get(i).getFile_pic());
                    image.setOldposition(i);
                    images.add(image);
                }else if(type == 2){
                    Image image = new Image();
                    image.setPath(body.getList().get(i).getFile_pic());
                    image.setOldposition(i);
                    image.setName(body.getList().get(i).getName());
                    mp3s.add(image);
                }
            }
            ablvList.setAdapter(new ClassFileAdapter(ctx, fileList));
        } else {
            Toast.makeText(ctx, classFileBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_fileadd, null);
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
        popuWindow.showAsDropDown(ivAdd, -10, 10, Gravity.RIGHT);
    }

    private class ViewHolderPopu implements View.OnClickListener {
        public View rootView;
        public TextView tv_uppic;
        public TextView tv_upfile;
        public TextView tv_addfile;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_uppic = (TextView) rootView.findViewById(R.id.tv_uppic);
            this.tv_upfile = (TextView) rootView.findViewById(R.id.tv_upfile);
            this.tv_addfile = (TextView) rootView.findViewById(R.id.tv_addfile);
            tv_uppic.setOnClickListener(this);
            tv_upfile.setOnClickListener(this);
            tv_addfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            popuWindow.dismiss();
            switch (view.getId()) {
                case R.id.tv_uppic:
                    startActivityForResult(new Intent(ctx, AllPicActivity.class), 100);

                    break;
                case R.id.tv_upfile:
                    startActivityForResult(new Intent(ctx, SeleteFileActivity.class), 101);
                    break;
                case R.id.tv_addfile:
                    showDia();
                    break;

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                diaShow.dismiss();
                break;
            case R.id.tv_create:
                String filename = vhDia.et_filename.getText().toString().trim();
                if (TextUtils.isEmpty(filename)) {
                    Toast.makeText(ctx, "请填写文件名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("token", Token);
                HttpType = 2;
                map.put("classid", classid);
                map.put("name", filename);
                mPreenter.fetch(map, false, HttpUrl.CreateClassFile, "");
                diaShow.dismiss();
                break;

        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_wishbag, null);
        vhDia = new ViewHolderDia(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        builder.setCancelable(false);
        diaShow = builder.show();
        vhDia.tv_close.setOnClickListener(this);
        vhDia.tv_create.setOnClickListener(this);
    }

    private class ViewHolderDia {
        public View rootView;
        public EditText et_filename;
        public TextView tv_close;
        public TextView tv_create;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.et_filename = (EditText) rootView.findViewById(R.id.et_filename);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_create = (TextView) rootView.findViewById(R.id.tv_create);
        }

    }

    private int tag = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 100:

                ArrayList<String> images = data.getStringArrayListExtra("images");
                upPic(images);
                break;
            case 101:
                final String filepath = data.getStringExtra("path");
                String[] split = filepath.split("/");
                final String filename = split[split.length - 1];
                final int filetype = data.getIntExtra("filetype", 0);
                final int size = Integer.valueOf(data.getStringExtra("size")) / 1024;
                showLoading();
                tag = 0;
                UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                    @Override
                    public void success(Response<String> response) {
                        Gson gson = new Gson();
                        UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            if (tag == 0) {
                                tag = 1;
                                int fileid = upPicBean.getBody().getFileid();
                                Map<String, String> map = new HashMap<>();
                                map.put("token", Token);
                                map.put("classid", classid);
                                map.put("name", filename);
                                map.put("type", filetype + "");
                                map.put("fileid", fileid + "");
                                map.put("size", size + "");
                                UpFileUtiles.addfile(ctx, map);
                            } else {
                                disLoading();
                                Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                                getFileList();
                            }
                        } else {
                            disLoading();
                            Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                UpFileUtiles.TYPE = 1;
                File file = new File(filepath);
                wch("类型："+filetype+"\n"+"size:"+size);
                UpFileUtiles.start(ctx, file, filetype + "", size);
                break;
        }
    }

    /**
     * 多图上传
     *
     * @param images
     */
    private void upPic(ArrayList<String> images) {
        showLoading();
        List<File> lists = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            lists.add(new File(images.get(i)));
        }
        wch(lists.size());
        PostRequest post = OkGo.<String>post(HttpUrl.UploadFile);
        for (int i = 0; i < images.size(); i++) {
            post.params("file" + i, lists.get(i));
        }
        post.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                wch(response.body());
                disLoading();
//                getFileList();
            }

            @Override
            public void onError(Response<String> response) {
                disLoading();
            }
        });
    }
}




