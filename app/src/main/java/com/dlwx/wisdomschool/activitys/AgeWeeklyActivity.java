package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LoadWEBUtiles;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 0到19岁周刊
 */
public class AgeWeeklyActivity extends BaseActivity {
    private String url;
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
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        setContentView(R.layout.activity_age_weekly);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

        LoadWEBUtiles webUtiles = new LoadWEBUtiles(ctx,rl_webroot);
        webUtiles.setListViewData(url,webView,progress_bar);
    }
    @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
                return true;
            }else{
                finish();
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    @OnClick(R.id.back)
    public void onViewClicked() {
        if (webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
        } else {
            finish();
        }
    }
    @Override
    protected void initListener() {
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
}
