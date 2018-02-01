package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.dlwx.wisdomschool.bean.ClassFileBean;
import com.dlwx.wisdomschool.bean.MorePicBean;
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
 * 班级文件文件夹详细列表
 */
public class PageFileDescActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener
{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rl_entry)
    RelativeLayout rlEntry;
    @BindView(R.id.ablv_list)
    ListView ablvList;
    @BindView(R.id.smallLabel)
    SmartRefreshLayout smallLabel;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.et_seach)
    EditText etSeach;
    private String name;
    private String cfid;
    private String classid;
    private List<ClassFileBean.BodyBean.ListBean> fileList;
    private MyPopuWindow popuWindow;
    private List<Image> images;
    private List<Image> mp3s;
    private String tag1;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        cfid = intent.getStringExtra("cfid");
        classid = intent.getStringExtra("classid");
        tag1 = intent.getStringExtra("tag");
        setContentView(R.layout.activity_page_file_desc);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(name);
        initTabBar(toolBar);
        getData("");
    }
    @Override
    protected void initListener() {
        initrefresh(smallLabel, true);
        ablvList.setOnItemClickListener(this);
        ablvList.setOnItemLongClickListener(this);
        etSeach.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(PageFileDescActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    search();
                }
                return false;
            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final ClassFileBean.BodyBean.ListBean listBean = fileList.get(i);
        int type = listBean.getType();
        if (type == 3 || type == 4) {
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
        }else if (type == 1) {
            //大图显示
            LookPic.showPic(ctx,tvTitle,images,i);
        }else if (type == 2) {
            MediaPlayUtils playUtils = new MediaPlayUtils(ctx);
            playUtils.showPopu(ablvList,mp3s,i);
        }
    }
    /**
     * 长按编辑删除
     * @param adapterView
     * @param view
     * @param i
     * @param l
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    private void search() {
        String seach = etSeach.getText().toString().trim();
        if (TextUtils.isEmpty(seach)) {
            Toast.makeText(ctx, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        getData(seach);
    }

    @Override
    public void downOnRefresh() {
        getData("");
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

    /**
     * 获取当前文件下的列表
     */
    private void getData(String seach) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("cfid", cfid);
        map.put("name", seach);
        if (tag1 == null) {
            map.put("classid", classid);
            mPreenter.fetch(map, true, HttpUrl.getClassFile, HttpUrl.getClassFile + Token + classid + cfid + seach);
        }else{
            map.put("type",type);
            mPreenter.fetch(map, true, HttpUrl.getBookBag, HttpUrl.getBookBag + Token + type + seach);
        }

    }
    private String type;
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        fileList(s, gson);
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
            long totalsize = 1048576;
            ClassFileBean.BodyBean body = classFileBean.getBody();
            double use_size = body.getUse_size();
            progressbar.setMax((int) totalsize);
            progressbar.setProgress((int) (use_size));
            double m = use_size / 1024;
            double mm = use_size % 1024;
            wch(mm);
            double g = m / 1024;
            double gg = m % 1024;
            if (g > 0) {
                tvSize.setText(g + "G/1G");
            } else if (m > 0) {
                tvSize.setText(m + "M/1G");
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
            tv_addfile.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {
            popuWindow.dismiss();
            switch (view.getId()) {
                case R.id.tv_uppic:
                    startActivityForResult(new Intent(ctx, AllPicActivity.class), 2);
                    break;
                case R.id.tv_upfile:
                    startActivityForResult(new Intent(ctx, SeleteFileActivity.class),101);
                    break;
            }
        }
    }
    private int tag = 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 2:
                ArrayList<String> images = data.getStringArrayListExtra("images");
                upPic(images);
                break;
            case 101:
                String ufid = data.getStringExtra("ufid");
                final String filepath = data.getStringExtra("path");

                final int filetype = data.getIntExtra("filetype", 0);

                showLoading();

                UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                    @Override
                    public void success(Response<String> response) {
                        Gson gson = new Gson();
                        UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                        if (upPicBean.getCode() == 200) {
                            if (tag == 0) {
                                tag = 1;
                                String[] split = filepath.split("/");
                                final String filename = split[split.length - 1];
                                int fileid = upPicBean.getBody().getFileid();
                                final int size = Integer.valueOf(data.getStringExtra("size")) / 1024;
                                Map<String, String> map = new HashMap<>();
                                map.put("token", Token);
                                map.put("classid", classid);
                                map.put("name", filename);
                                map.put("folderid",cfid);
                                map.put("type", filetype + "");
                                map.put("fileid", fileid + "");
                                map.put("size", size + "");
                                UpFileUtiles.addfile(ctx, map);
                            } else {
                                disLoading();
                                Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                               getData("");
                            }
                        } else {
                            disLoading();
                            Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if (!TextUtils.isEmpty(ufid)) {//从智慧书包上传
                    Map<String, String> map = new HashMap<>();
                    map.put("token", Token);
                    map.put("classid", classid);
                    map.put("folderid",cfid);
                    map.put("ufid", ufid);
                    UpFileUtiles.addfile(ctx, map);
                    tag = 1;
                    return;
                }
                UpFileUtiles.TYPE = 1;
                File file = new File(filepath);
                tag = 0;
                UpFileUtiles.start(ctx, file, filetype + "", 0);
                break;
        }
    }

    /**
     * 多图上传
     *
     * @param
     */
    private void upPic(final  ArrayList<String> images) {
        loading.show();
        PostRequest<String> post = OkGo.<String>post(HttpUrl.UploadAll);
        for (int i = 0; i < images.size(); i++) {
            post.params(i+"",new File(images.get(i)));
        }
        post.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                loading.dismiss();
                wch(response);
                Gson gson = new Gson();
                MorePicBean morePicBean = gson.fromJson(response.body(), MorePicBean.class);
                if (morePicBean.getCode() == 200) {
                    List<MorePicBean.BodyBean> body = morePicBean.getBody();
                    upPicMore(body);
                }
                Toast.makeText(ctx, morePicBean.getResult(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response<String> response) {
                loading.dismiss();
            }
        });
    }
    private int pos = 0;
    private void upPicMore(final List<MorePicBean.BodyBean> body){

        MorePicBean.BodyBean bodyBean = body.get(pos);
        OkGo.<String>post(HttpUrl.AddFile)
                .params("token",Token)
                .params("classid",classid)
                .params("folderid",cfid)
                .params("name",bodyBean.getName())
                .params("type","1")
                .params("fileid",bodyBean.getId())
                .params("size",bodyBean.getSize())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        pos ++;
                        if (pos == body.size()) {
                            pos = 0;
                           getData("");
                            Toast.makeText(ctx, "上传完成", Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            upPicMore(body);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        upPicMore(body);
                    }
                });

    }
}
