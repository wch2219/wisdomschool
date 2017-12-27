package com.dlwx.baselib.utiles;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/17/017.
 */

public class LoadWEBUtiles {
    private Context ctx;
    private String LoginUrl = "https://login.m.taobao.com/login.htm?tpl_redirect_url=https%3A%2F%2Fuland.taobao.com%2Fcoupon%2Fedetail%3Fe%3DirueqkzFMb4GQASttHIRqSm47PUpUPKNINgbbcFjeXS8WrnxDkeYTabS5XLebz9sg%252BbQWDpqZ6EhU280YHnNnr9fwBwwUiqlM7kvoPUmKIIIdKZiLzMdZ%252FSs4zOlZ4se";
    public LoadWEBUtiles(Context ctx) {
        super();
        this.ctx = ctx;
    }
    /**
     * 用来控制字体大小
     */
    private int fontSize = 1;
    public void setListViewData(String url, WebView webView, final ProgressBar progress) {
        Log.i("wch",url);
//        //执行JavaScript脚本
//        webView.getSettings().setJavaScriptEnabled(true);
        //得到一个WebView的设置对象,
        WebSettings setting = webView.getSettings();
        //setJavaScriptEnabled:使webView可以支持JavaScript
        setting.setJavaScriptEnabled(true);
        //setSupportZoom:使WebView允许网页缩放,记住这个方法前,要有让WebView支持JavaScript的设定,否则会不起作用
        setting.setSupportZoom(true);
        setting.setTextSize(WebSettings.TextSize.NORMAL);
        //打开页面时， 自适应屏幕：
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        webView.loadUrl(url);
        webView.setSaveEnabled(false);
        webView.addJavascriptInterface(new JSInterface(),"window");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                if (url.contains("login.m.taobao.com")) {
//                    LogUtiles.LogI("拦截登录成功");
//                    return true;
//                }
                LogUtiles.LogI("url:"+url);
                return super.shouldOverrideUrlLoading(view, url);
            }

        });



//触摸焦点起作用,如果不设置则在点击网页文本输入框的时候不能弹出软键盘及一些点击事件
        webView.requestFocus();
        //该事件是指UI界面发生改变时进行监听
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //通过代码让ProgressBar显示出来
                if (progress != null) {
                    //对ProgressBar设置加载进度的参数
                    //通过代码让ProgressBar显示出来
                    progress.setVisibility(View.VISIBLE);
                    //对ProgressBar设置加载进度的参数
                    progress.setProgress(newProgress);
                    if (newProgress == 100) {
                        //如果ProgressBar加载到100,就让他隐藏

                        progress.setVisibility(View.GONE);
                    }
                }

                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.d("message", message);
                Toast.makeText(ctx, message+"0000"+url, Toast.LENGTH_SHORT).show();
                if (message != null) {

                }
                result.cancel();
                return true;

            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });


    }
    class JSInterface {
        Handler handler = new Handler();

        @JavascriptInterface
        public void getDateTime() {
            handler.post(new Runnable() {
                public void run() {
                    // 此处调用 HTML 中的javaScript 函数
                    ((Activity)ctx).finish();
                }
            });
        }
    }
    @JavascriptInterface
    public void getCoupons(){

        Toast.makeText(ctx, "sadasdas", Toast.LENGTH_SHORT).show();
    }
}
