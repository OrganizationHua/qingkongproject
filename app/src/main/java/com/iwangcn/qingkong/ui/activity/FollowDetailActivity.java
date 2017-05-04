package com.iwangcn.qingkong.ui.activity;

import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 头条详情界面
 */
public class FollowDetailActivity extends QkBaseActivity {
    @BindView(R.id.news_title)
    TextView mNewsTitle;
    @BindView(R.id.news_from)
    TextView mNewsFrom;
    @BindView(R.id.news_time)
    TextView mNewsTime;
    @BindView(R.id.news_webView)
    WebView mWebView;

    @BindView(R.id.tag_flowlayout)
    public TagFlowLayout tagFlowLayout;//标签
    @Override
    public int layoutChildResID() {
        return R.layout.activity_follow_detail;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.news_detail));
        setRightTitle(getString(R.string.originalText));
        initWebView("https://www.baidu.com");
    }

    @Override
    public void initData() {
        mNewsTitle.setText("新闻标题新闻标题新闻标题");
        mNewsFrom.setText("新闻来源");
        mNewsTime.setText("20170104"+"        "+"13:14");

    }
    private void initWebView(String url){
        WebSettings webSettings = this.mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSettings.setSupportZoom(false);//是否可以缩放，默认true
        webSettings.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.setDomStorageEnabled(true);//DOM Storage
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
        this.mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        this.mWebView.loadUrl(url);
    }
    @OnClick(R.id.base_act_right_lin)//APP信息
    public void onBtnWebView() {
        ToastUtil.showToast(this, "查看原文");
    }
}
