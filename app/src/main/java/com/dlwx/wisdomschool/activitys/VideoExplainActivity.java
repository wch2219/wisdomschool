package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LoadWEBUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.utiles.HttpUrl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 视频讲解
 */
public class VideoExplainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.rl_webroot)
    RelativeLayout rl_webroot;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_video_explain);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

        LoadWEBUtiles loadWEBUtiles = new LoadWEBUtiles(ctx,rl_webroot);
        String url = HttpUrl.VoideUrl + "?token=" + Token;
        loadWEBUtiles.setListViewData(url, webView, progress_bar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
                return true;
            } else {
                finish();
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(ctx, VideoDescActivity.class));
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        if (webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
        } else {
            finish();
        }
    }
}
