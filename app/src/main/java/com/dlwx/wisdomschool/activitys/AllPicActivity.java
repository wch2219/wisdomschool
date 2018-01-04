package com.dlwx.wisdomschool.activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.ImgesLisetBean;
import com.dlwx.baselib.utiles.UploadPicUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AllPicAdapter;
import com.dlwx.wisdomschool.bean.PicFileClassBean;
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

/**
 * 全部图片
 */
public class AllPicActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_seletepath)
    RelativeLayout rlSeletepath;
    @BindView(R.id.gv_list)
    GridView gvList;
    private List<ImgesLisetBean.Image> images;
    private List<String> mapimages = new ArrayList<>();

    private Map<String,String> map = new HashMap<>();
    private Map<String,List<String>> mapimg = new HashMap<>();
    private List<String> list = new ArrayList<>();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_all_pic);
        ButterKnife.bind(this);
        rlSeletepath.setOnClickListener(this);
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
            ImgesLisetBean.Image image = new ImgesLisetBean.Image();
            //获取图片的名称
            String path = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            long aLong = cursor.getLong(cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            image.setDate(aLong);
            image.setPath(path);
            images.add(image);
        }
        Collections.sort(images,new FileComparator());
        for (int i = 0; i < images.size(); i++) {
            String path = images.get(i).getPath();
            String[] split = path.split("/");
            String s = split[split.length - 2];
            map.put(s,"1");
        }
        String [] strs = new String[map.size()];

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        int mm =0;
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            strs[mm] = next.getKey();
            wch("key:"+next.getKey());
            mm++;
        }
        PicFileClassBean picFileClassBean = new PicFileClassBean();
        List<PicFileClassBean.Body> bodys = new ArrayList<>();
        for (int j = 0; j < strs.length; j++) {

            PicFileClassBean.Body body = new PicFileClassBean.Body();
            List<PicFileClassBean.Body.PathBean > pathBeans = new ArrayList<>();
            PicFileClassBean.Body.PathBean pathBean = new PicFileClassBean.Body.PathBean();
            for (int i = 0; i < images.size(); i++) {
                String path = images.get(i).getPath();
                String[] split = path.split("/");
                String s = split[split.length - 2];

                if (s.equals(strs[j])) {
                    body.setTitle(s);
                    pathBean.setPath(path);
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
        wch("length:"+strs.length);
        gvList.setAdapter(new AllPicAdapter(ctx,images));
    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {

            UploadPicUtiles.opencamera((Activity) ctx,getPackageName());
        }else{


        }
    }
    private class FileComparator implements Comparator<ImgesLisetBean.Image> {
        @Override
        public int compare(ImgesLisetBean.Image image, ImgesLisetBean.Image t1) {
            File file = new File(image.getPath());
            File file1 = new File(t1.getPath());
            if(file.lastModified()<file1.lastModified()){
                return 1;//最后修改的照片在前
            }else{
                return -1;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode){
                    case 1:
                        loadData();
                        break;
                }
    }
}
