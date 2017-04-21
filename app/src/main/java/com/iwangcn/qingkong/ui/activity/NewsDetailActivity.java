package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.view.TagWidget.TagModel;
import com.iwangcn.qingkong.utils.AbAppUtil;
import com.iwangcn.qingkong.utils.PopupWindowUtil;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 头条详情界面
 */
public class NewsDetailActivity extends QkBaseActivity {
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

    private Context mContext = this;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_news_detail;
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
        mNewsTime.setText("20170104" + "        " + "13:14");
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

    @OnClick(R.id.base_act_right_lin)//APP信息
    public void onBtnWebView(View view) {
        String url = "https://www.baidu.com";
        AbAppUtil.openBrowser(this, url);
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

    @OnClick(R.id.news_detail_wrong_lin)//我要报错
    public void onBtnWrong(View v) {
        List<TagModel> listData = new ArrayList<TagModel>(3);
        for (int i = 0; i < 5; i++) {
            TagModel model = new TagModel();
            if (i % 2 == 0) {
                model.setTag(i + "个标签");
            } else {
                model.setTag("新闻");
            }

            model.setSelect(false);
            listData.add(model);

        }
        showPopupWindow(v, 1, listData);
        ToastUtil.showToast(this, "我要报错");

    }

    // 右上角弹窗
    private void showPopupWindow(View parent, int direct, final List<TagModel> listData) {
        PopupWindowUtil.showPopupWindow(this, parent, mRelMak, direct, listData, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "选中了" + checkSelectTags(listData).size() + "条数据");
            }
        });
    }

    private List<TagModel> checkSelectTags(List<TagModel> listData) {
        List<TagModel> list = new ArrayList<>();
        for (TagModel model : listData) {
            if (model.isSelect()) {
                list.add(model);
            }
        }
        return list;
    }

    @OnClick(R.id.news_detail_follow_lin)//跟进
    public void onBtnFollow(View v) {
        ToastUtil.showToast(this, "已跟进");
        List<TagModel> listData = new ArrayList<TagModel>(3);
        for (int i = 0; i < 5; i++) {
            TagModel model = new TagModel();
            if (i % 2 == 0) {
                model.setTag(i + "个标签");
            } else {
                model.setTag("新闻");
            }

            model.setSelect(true);
            listData.add(model);

        }
        showPopupWindow(v, 0, listData);
    }
}
