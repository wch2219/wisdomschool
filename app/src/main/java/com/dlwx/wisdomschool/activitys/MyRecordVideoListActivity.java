package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AllPicAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecordVideoListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.gv_list)
    GridView gvList;
    private List<Image> filelist = new ArrayList<>();

    @Override
    protected void initView() {
        String files = getIntent().getStringExtra("files");
        setContentView(R.layout.activity_my_record_video_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        Cursor cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            Image image = new Image();
            //路径
            String path = cursor
                    .getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            long aLong = cursor.getLong(cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN));
            long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DESCRIPTION));
            image.setDate(aLong);
            image.setPath(path);
            image.setDuration(duration);
            filelist.add(image);
        }
        wch(filelist.size());
        AllPicAdapter allPicAdapter = new AllPicAdapter(ctx, filelist);
        gvList.setAdapter(allPicAdapter);
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
        Image image = filelist.get(i-1);
        wch(image.getPath());
        Intent intent = new Intent(ctx,PublishGroupUpActivity.class);
        intent.putExtra("videofile",image.getPath());
        startActivity(intent);
    }

}
