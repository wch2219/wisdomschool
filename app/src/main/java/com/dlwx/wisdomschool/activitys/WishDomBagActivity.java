package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Context;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 智慧书包
 */
public class WishDomBagActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.lv_list)
    ListView lv_list;
    private MyPopuWindow popupWindow;
    private AlertDialog diaShow;
    private ViewHolderDia vhDia;
    private List<ClassFileBean.BodyBean.ListBean> fileList;
    private List<Image> images;
    private List<Image> mp3s;
    private boolean ischose;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        ischose = intent.getBooleanExtra("ischose", false);
        setContentView(R.layout.activity_wish_dom_bag);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧书包");
        initTabBar(toolBar);
        getFileList();

    }


    @Override
    protected void initListener() {
        lv_list.setOnItemClickListener(this);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_wish, null);
        ViewHolderpopu vh = new ViewHolderpopu(view);
        popupWindow = new MyPopuWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(ivAdd, -20, 20, Gravity.RIGHT);
        vh.tv_uppic.setOnClickListener(this);
        vh.tv_upfile.setOnClickListener(this);
        vh.tv_new.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_uppic:
                startActivityForResult(new Intent(ctx, AllPicActivity.class), 2);

                break;
            case R.id.tv_upfile:
                startActivityForResult(new Intent(ctx, SeleteFileActivity.class), 101);
                break;
            case R.id.tv_addfile:
                showDia();
                break;
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
                map.put("name", filename);
                mPreenter.fetch(map, false, HttpUrl.CreateBookbag, "");
                diaShow.dismiss();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ClassFileBean.BodyBean.ListBean listBean = fileList.get(i);
        int type = listBean.getType();//1图片，2音视频，3txt 4 doc 99文件夹
        Intent intent;
        switch (type) {
            case 1://图片
                //大图显示
                if (ischose) {
                    intent = new Intent();
                   intent.putExtra("ufid",listBean.getCfid());
                   setResult(102,intent);
                   finish();
                }else{
                    LookPic.showPic(ctx,tvTitle,images,i);
                }
                break;
            case 2://MP3
                MediaPlayUtils playUtils = new MediaPlayUtils(ctx);
                playUtils.showPopu(lv_list,mp3s,i);
                break;
            case 3://txt
                readFile(listBean);
                break;
            case 4://doc
                readFile(listBean);
                break;
            case 99:
                intent = new Intent(ctx, BookBagFileDescActivity.class);
                intent.putExtra("name", listBean.getName());
                intent.putExtra("cfid", listBean.getCfid());
                intent.putExtra("ischose",true);
                startActivityForResult(intent,102);
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



    private class ViewHolderpopu {
        public View rootView;
        public TextView tv_uppic;
        public TextView tv_upfile;
        public TextView tv_new;

        public ViewHolderpopu(View rootView) {
            this.rootView = rootView;
            this.tv_uppic = (TextView) rootView.findViewById(R.id.tv_uppic);
            this.tv_upfile = (TextView) rootView.findViewById(R.id.tv_upfile);
            this.tv_new = (TextView) rootView.findViewById(R.id.tv_new);
        }
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

    private String type = "";//1图片，2音频，3文档（搜索时候传）
    private String seach = "";//

    /**
     * 全部文件列表
     */
    private void getFileList() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("type", type);
        map.put("name", seach);
        mPreenter.fetch(map, true, HttpUrl.getBookBag, HttpUrl.getBookBag + Token + type + seach);
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

            ClassFileBean.BodyBean body = classFileBean.getBody();

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
                } else if (type == 2) {
                    Image image = new Image();
                    image.setPath(body.getList().get(i).getFile_pic());
                    image.setOldposition(i);
                    image.setName(body.getList().get(i).getName());
                    mp3s.add(image);
                }
            }
            lv_list.setAdapter(new ClassFileAdapter(ctx, fileList));
        } else {
            Toast.makeText(ctx, classFileBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private int tag = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 2:
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
                                map.put("name", filename);
                                map.put("type", filetype + "");
                                map.put("fileid", fileid + "");
                                map.put("size", size + "");
                                addfile(ctx,map);
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
                wch("类型：" + filetype + "\n" + "size:" + size);
                UpFileUtiles.start(ctx, file, filetype + "", size);
                break;
            case 102:
                String ufid = data.getStringExtra("ufid");
                Intent intent = new Intent();
                intent.putExtra("ufid",ufid);
                setResult(102,intent);
                finish();
                break;
        }
    }

    private void addfile(final Context ctx, Map<String,String> map){
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        PostRequest<String> post = OkGo.<String>post(HttpUrl.BookBagAddFile);
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            post.params(next.getKey(), next.getValue());
        }
        post.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                getFileList();
            }

            @Override
            public void onError(Response<String> response) {
                Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
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
