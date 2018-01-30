package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.bean.PicFileClassBean;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AllPicAdapter;
import com.dlwx.wisdomschool.adapter.PicSeleteCatalogAdapter;
import com.dlwx.wisdomschool.adapter.PicViewPAgeAdapter;
import com.dlwx.wisdomschool.interfac.Picseltete;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 全部图片
 */
public class AllPicActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_seletepath)
    RelativeLayout rlSeletepath;
    @BindView(R.id.gv_list)
    GridView gvList;
    @BindView(R.id.gv_list1)
    GridView gvList1;
    @BindView(R.id.tv_picpath)
    TextView tv_picpath;
    @BindView(R.id.tv_currentnum)
    TextView tvCurrentnum;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private List<Image> images;
    private List<String> mapimages = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();
    private Map<String, List<String>> mapimg = new HashMap<>();
    private List<String> list = new ArrayList<>();
    private PicFileClassBean picFileClassBean;
    private AllPicAdapter allPicAdapter;
    private int MAXLenth = 9;//选择的最大长度
    private int current;//当前选择数量
    private ArrayList<Image> seletepiclist = new ArrayList<>();//存放选中图片的路径

    private PicViewPAgeAdapter picViewPAgeAdapter;
    private int currnum;

    @Override
    protected void initView() {
        currnum = getIntent().getIntExtra("currnum", 0);
        MAXLenth = getIntent().getIntExtra("MAXNUM",9);
        setContentView(R.layout.activity_all_pic);
        ButterKnife.bind(this);
        MAXLenth = MAXLenth - currnum;
    }

    @Override
    protected void initData() {
        tvTitle.setText("相册胶卷");
        initTabBar(toolBar);
        loadData();
    }

    private void loadData() {
        images = new ArrayList<>();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            Image image = new Image();
            //获取图片的名称
            String path = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            long aLong = cursor.getLong(cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            String size = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
            image.setDate(aLong);
            image.setPath(path);
            image.setSize(size);
            image.setFiletype(1);
            images.add(image);
        }
        Collections.sort(images, new FileComparator());
        for (int i = 0; i < images.size(); i++) {
            String path = images.get(i).getPath();
            String[] split = path.split("/");
            String s = split[split.length - 2];
            map.put(s, "1");
        }
        String[] strs = new String[map.size()];

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        int mm = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            strs[mm] = next.getKey();
            wch("key:" + next.getKey());
            mm++;
        }
        picFileClassBean = new PicFileClassBean();
        List<PicFileClassBean.Body> bodys = new ArrayList<>();
        for (int j = 0; j < strs.length; j++) {

            PicFileClassBean.Body body = new PicFileClassBean.Body();
            List<Image> pathBeans = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                String path = images.get(i).getPath();
                String[] split = path.split("/");
                String s = split[split.length - 2];
                if (s.equals(strs[j])) {
                    body.setTitle(s);
                    Image pathBean = new Image();
                    pathBean.setPath(path);
                    pathBeans.add(pathBean);
                }
            }

            body.setPathBeans(pathBeans);
            bodys.add(body);
        }
        picFileClassBean.setBody(bodys);
        Gson gson = new Gson();
        String s = gson.toJson(picFileClassBean);
        wch(s);
        wch("length:" + strs.length);
        allPicAdapter = new AllPicAdapter(ctx, images);
        gvList.setAdapter(allPicAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(this);

        Picseltete.setSletePicInterface(new Picseltete.SletePicInterface() {
            @Override
            public void checkback(int pos, boolean check) {
                //当前已选择个数
                seletepiclist.clear();
                current = 0;
                for (int i = 0; i < images.size(); i++) {
                    Image image = images.get(i);
                    boolean check1 = image.isCheck();
                    if (check1) {
                        current++;
                        seletepiclist.add(image);
                    }
                }
                if (current <= MAXLenth) {
                    images.get(pos).setCheck(check);
                    if (current == 0) {
                        tvCurrentnum.setVisibility(View.GONE);
                    } else {
                        tvCurrentnum.setVisibility(View.VISIBLE);
                        tvCurrentnum.setText(current + "");
                    }
                } else {
                    boolean check1 = images.get(pos).isCheck();
                    if (check1) {
                        images.get(pos).setCheck(false);
                    } else {
                        images.get(pos).setCheck(false);
                        Toast.makeText(ctx, "当前最多只能选择"+MAXLenth+"张图片", Toast.LENGTH_SHORT).show();
                    }
                }
                allPicAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    /**
     * 所有图片的条目点击事件
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
          opencamera((Activity) ctx, getPackageName());
        } else {
            gvList.setVisibility(View.GONE);
            vpView.setVisibility(View.VISIBLE);
            picViewPAgeAdapter = new PicViewPAgeAdapter(ctx, images);
            vpView.setAdapter(picViewPAgeAdapter);
            vpView.setCurrentItem(i - 1);
            picViewPAgeAdapter.setPicOnclick(new PicViewPAgeAdapter.PicOnclick() {
                @Override
                public void close() {
                    vpView.setVisibility(View.GONE);
                    gvList.setVisibility(View.VISIBLE);
                }
            });
        }
    }
    @OnClick({R.id.rl_seletepath, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_seletepath:
                showSeletePopu();
                break;
            case R.id.btn_submit:
                //todo
                if (seletepiclist.size() == 0) {
                    Toast.makeText(ctx, "请先选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> images = new ArrayList<>();
                for (int i = 0; i < seletepiclist.size(); i++) {
                    wch(seletepiclist.get(i).getPath());
                    images.add(seletepiclist.get(i).getPath());
                }
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("imagesPar",seletepiclist);
                intent.putStringArrayListExtra("images",images);
                setResult(2,intent);
              finish();
                break;
        }
    }
    /**
     * 显示不同路径下的图片列表
     */
    private void showSeletePopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_seletepic, null);
        final MyPopuWindow popupWindow = new MyPopuWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(toolBar, 0, 0);
        ListView lvList = view.findViewById(R.id.lv_list);
        final List<PicFileClassBean.Body> body = picFileClassBean.getBody();
        lvList.setAdapter(new PicSeleteCatalogAdapter(ctx, body, images));
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    popupWindow.dismiss();
                    gvList.setVisibility(View.VISIBLE);
                    vpView.setVisibility(View.GONE);
                    allPicAdapter  = new AllPicAdapter(ctx, images);
                    tv_picpath.setText("所有图片");
                } else {
                    vpView.setVisibility(View.GONE);
                    PicFileClassBean.Body body1 = picFileClassBean.getBody().get(i - 1);
                    popupWindow.dismiss();
                    tv_picpath.setText(body1.getTitle());
                    images = body1.getPathBeans();
                    allPicAdapter  = new AllPicAdapter(ctx,images);
                    gvList.setAdapter(allPicAdapter);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                String picCamera = getPicCamera(data, (Activity) ctx);
                wch(picCamera+"图片");
                Uri uri = Uri.fromFile(new File(picCamera));
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                this.sendBroadcast(intent);
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                            handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                break;
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
             switch (msg.what){
                        case 1:
                            loadData();
                            break;
                    }
        }
    };
    @Override
    public void onBackPressed() {
        int visibility = vpView.getVisibility();
        if (visibility == 0) {
            vpView.setVisibility(View.GONE);
            gvList.setVisibility(View.VISIBLE);
        }else{
            super.onBackPressed();
        }
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
    private static File outputImage;
    private static Uri uriForFile;//图片保存地址
    /**
     * 打开相机
     */
    public static void opencamera(Activity activity,String packname) {

        //创建一个File对象用于存储拍照后的照片
        outputImage = new File(Environment.getExternalStorageDirectory()+"/camera/",System.currentTimeMillis()+".jpg");
        outputImage.getParentFile().mkdirs();

        //判断Android版本是否是Android7.0以上
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            uriForFile = FileProvider.getUriForFile(activity, packname+".fileprovider", outputImage);
        }else{
            uriForFile=Uri.fromFile(outputImage);
        }
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        activity.startActivityForResult(openCameraIntent, 1);
    }
    /**
     * 获得拍照的图片
     * @param data
     * @param act
     * @return
     */
    public static String getPicCamera(Intent data,Activity act){
        String path = outputImage.getAbsolutePath();
        return path;
    }
}
