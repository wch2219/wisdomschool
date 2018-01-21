package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LoadWEBUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.HttpUrl;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 视频讲解
 */
public class VideoExplainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    //    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tool_bar)
//    Toolbar toolBar;
//    @BindView(R.id.lv_list)
//    ListView lvList;
    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_video_explain);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
//        tvTitle.setText("智慧驾校攻略区");
//        initTabBar(toolBar);
//        lvList.setAdapter(new VideoExpAdapter(ctx));
        LoadWEBUtiles loadWEBUtiles = new LoadWEBUtiles(ctx);
        String url = HttpUrl.VoideUrl+"?token="+Token;
        loadWEBUtiles.setListViewData(url,webView,null);
    }

    @Override
    protected void initListener() {
//        lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(ctx, VideoDescActivity.class));
    }
}
