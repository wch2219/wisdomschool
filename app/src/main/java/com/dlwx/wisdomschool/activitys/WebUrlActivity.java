package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LoadWEBUtiles;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 加载web
 */
public class WebUrlActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.rl_webroot)
    RelativeLayout rl_webroot;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    private boolean isback;
    private boolean unTitle;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        isback = intent.getBooleanExtra("isback", false);
        unTitle = intent.getBooleanExtra("unTitle", false);
        setContentView(R.layout.activity_web_url);
        ButterKnife.bind(this);
        LoadWEBUtiles webUtiles = new LoadWEBUtiles(this, rl_webroot);
        webUtiles.setTitle(tvTitle);
        webUtiles.setListViewData(url, webview, progressBar);
    }

    @Override
    protected void initData() {
        if (unTitle)
            toolBar.setVisibility(View.GONE);
        else
            initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        if (webview.canGoBack()) {
            webview.goBack();// 返回前一个页面

        } else {
            finish();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isback) {
                finish();
            }
            if (webview.canGoBack()) {
                webview.goBack();// 返回前一个页面
                return true;
            } else {
                finish();
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
