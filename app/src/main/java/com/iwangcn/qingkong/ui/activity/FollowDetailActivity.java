package com.iwangcn.qingkong.ui.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.FollowDetailEvent;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.HeadLineModel;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;

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

    @BindView(R.id.tv_is_top)
    TextView tv_is_top;
    @BindView(R.id.img_is_top)
    ImageView img_is_top;
    private HeadLineModel data;
    private FollowDetailEvent headLineFollowEvent;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_follow_detail;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.news_detail));
        setRightTitle(getString(R.string.originalText));
        if (getIntent().getIntExtra("type", 1) == 1) {
            data = (HeadLineModel) getIntent().getSerializableExtra("data");
        }

        initWebView(data.getEventData().getData().getUrl() == null ? "" : data.getEventData().getData().getUrl());
    }

    @Override
    public void initData() {
        headLineFollowEvent = new FollowDetailEvent(this);
        //是否置顶
        if (!TextUtils.equals(data.getTop() + "", "0")) {
            tv_is_top.setText("置顶");
            img_is_top.setImageResource(R.drawable.genjin_btn_top);
        } else if (!TextUtils.equals(data.getTop() + "", "1")) {
            tv_is_top.setText("取消置顶");
            img_is_top.setImageResource(R.drawable.genjin_btn_untop);
        }
        mNewsTitle.setText(data.getEventData().getData().getTitle() == null ? "" : data.getEventData().getData().getTitle());
        mNewsFrom.setText(data.getEventData().getData().getSource() == null ? "" : data.getEventData().getData().getSource());
        mNewsTime.setText(AbDateUtil.formatDateStrGetDay(data.getEventData().getData().getUpdateTime()));
        if (!TextUtils.isEmpty(data.getLabels())) {
            TagAdapter<String> tagAdapter = new TagAdapter<String>(Arrays.asList(data.getLabels().split(","))) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {

                    TextView tv = (TextView) LayoutInflater.from(FollowDetailActivity.this).inflate(R.layout.tv,
                            parent, false);
                    tv.setText(o);
                    return tv;
                }
            };
            tagFlowLayout.setAdapter(tagAdapter);
        }

    }

    @OnClick(R.id.ll_cancle_follow)
    public void clickCancel() {
        headLineFollowEvent.doCancleFollow(data.getAutoId() + "");

    }

    @OnClick(R.id.ll_set_top)
    public void clickSetUpTop() {
        if (!TextUtils.equals(data.getTop() + "", "0")) {
            headLineFollowEvent.doFollowSetUp(data.getAutoId() + "");
        } else if (!TextUtils.equals(data.getTop() + "", "1")) {
            headLineFollowEvent.doFollowSetUpCancleTop(data.getAutoId() + "");
        }
    }


    @OnClick(R.id.ll_processed_finished)
    public void clickProcess() {
        headLineFollowEvent.doFollowDone(data.getAutoId() + "");

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

    @OnClick(R.id.base_act_right_lin)//APP信息
    public void onBtnWebView() {
        ToastUtil.showToast(this, "查看原文");
    }
}
