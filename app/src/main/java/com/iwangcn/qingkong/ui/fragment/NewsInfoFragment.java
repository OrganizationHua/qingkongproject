package com.iwangcn.qingkong.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.BaseFragment;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 新闻fragment
 * Created by czh on 2017/4/21.
 */

public class NewsInfoFragment extends BaseFragment {
    @BindView(R.id.news_title)
    TextView mNewsTitle;
    @BindView(R.id.news_from)
    TextView mNewsFrom;//来源
    @BindView(R.id.news_time)
    TextView mNewsTime;//新闻事件
    @BindView(R.id.news_webView)
    WebView mWebView;

    @BindView(R.id.tag_flowlayout)
    public TagFlowLayout tagFlowLayout;//标签

    @BindView(R.id.activity_news_detail)
    public RelativeLayout tagFlowLayout2;//标签

    private Context mContext;

    private int position; //页号
    private NewsInfo mNewsInfo;
    public static NewsInfoFragment newInstance(int position, NewsInfo newsInfo) {
        NewsInfoFragment fragment = new NewsInfoFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("newsInfo", newsInfo);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里我只是简单的用num区别标签，其实具体应用中可以使用真实的fragment对象来作为叶片
        position = getArguments() != null ? getArguments().getInt("position") : 1;
        mNewsInfo = getArguments() != null ? (NewsInfo) getArguments().getSerializable("newsInfo") :null;
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_news_detail_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mContext = getActivity();
        mNewsTitle.setText("新闻标题新闻标题新闻标题");
        mNewsFrom.setText("新闻来源");
        mNewsTime.setText("20170104" + "        " + "13:14");
        initWebView("https://www.baidu.com/");
        initTabLayout();
    }

    private void initWebView(String url) {
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

    private void initTabLayout() {
        List<String> itemData = new ArrayList<String>(3);

        for (int i = 0; i < 10; i++) {
            itemData.add("规范");

        }
        TagAdapter<String> tagAdapter = new TagAdapter<String>(itemData) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {

                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                        parent, false);
                tv.setText(o);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);
    }
}
