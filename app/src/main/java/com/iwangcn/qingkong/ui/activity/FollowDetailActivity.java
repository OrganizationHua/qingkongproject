package com.iwangcn.qingkong.ui.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.FollowDetailEvent;
import com.iwangcn.qingkong.ui.adapter.QKTagAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.HeadLineModel;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.iwangcn.qingkong.utils.AbAppUtil;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.ll_follow)
    LinearLayout ll_follow;
    @BindView(R.id.tv_follow)
    TextView tv_follow;
    @BindView(R.id.img)
    ImageView img;
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
        setTitle("消息");
        setRightTitle(getString(R.string.originalText));
        if (getIntent().getIntExtra("type", 1) == 1) {
            data = (HeadLineModel) getIntent().getSerializableExtra("data");
        }
        EventBus.getDefault().register(this);
        initWebView();
    }

    @Override
    public void initData() {
        headLineFollowEvent = new FollowDetailEvent(this);
        headLineFollowEvent.getDetailData(data.getAutoId() + "");
    }

    private void initTag(HeadLineModel data) {
        if (data == null) return;
        mWebView.loadUrl(data.getEventData().getData().getUrl() == null ? "" : data.getEventData().getData().getUrl());

        //是否置顶
        if (TextUtils.equals(data.getTop() + "", "0")) {
            tv_is_top.setText("置顶");
            img_is_top.setImageResource(R.drawable.genjin_btn_top);
        } else if (TextUtils.equals(data.getTop() + "", "1")) {
            tv_is_top.setText("取消置顶");
            img_is_top.setImageResource(R.drawable.genjin_btn_untop);
        }
        mNewsTitle.setText(data.getEventData().getData().getTitle() == null ? "" : data.getEventData().getData().getTitle());
        mNewsFrom.setText(data.getEventData().getData().getSource() == null ? "" : data.getEventData().getData().getSource());
        mNewsTime.setText(AbDateUtil.formatDateStrGetDay(data.getEventData().getData().getUpdateTime()));
        List<QkTagModel> list = new ArrayList<>();
//        list.add(new QkTagModel(0, (String) SpUtils.get(this, data.getEventData().getDataType() + "", "1")));
//        if (data.getEventData().getDataType() == 1 || data.getEventData().getDataType() == 5) {
//            if (!TextUtils.isEmpty(data.getEventData().getData().getKeywords())) {
//                list.add(new QkTagModel(1, data.getEventData().getData().getKeywords()));
//
//            }
//        }
        if (data.getBusinessLabels() != null && data.getBusinessLabels().size() != 0) {
            for (int i = 0; i < data.getBusinessLabels().size(); i++) {
                if (!TextUtils.isEmpty(data.getBusinessLabels().get(i))) {
                    list.add(new QkTagModel(2, data.getBusinessLabels().get(i), 1, data.getAutoId()));
                }
            }
        }
        if (data.getSelfLabels() != null && data.getSelfLabels().size() != 0) {
            for (int j = 0; j < data.getSelfLabels().size(); j++) {
                if (!TextUtils.isEmpty(data.getSelfLabels().get(j))) {
                    list.add(new QkTagModel(3, data.getSelfLabels().get(j), 1, data.getAutoId()));
                }
            }
            list.add(new QkTagModel(4, "图片", 1, data.getAutoId()));
        }

        tagFlowLayout.setAdapter(new QKTagAdapter(this, list));
    }

    @OnClick(R.id.ll_follow)
    public void clickCancel() {
        headLineFollowEvent.doCancleFollow(data.getAutoId() + "");

    }

    @OnClick(R.id.ll_set_top)
    public void clickSetUpTop() {
        //是否置顶
        if (TextUtils.equals(data.getTop() + "", "0")) {
            headLineFollowEvent.doFollowSetUp(data.getAutoId() + "");
        } else if (TextUtils.equals(data.getTop() + "", "1")) {
            headLineFollowEvent.doFollowSetUpCancleTop(data.getAutoId() + "");
        }
    }


    @OnClick(R.id.ll_processed_finished)
    public void clickProcess() {
        headLineFollowEvent.doFollowDone(data.getAutoId() + "");

    }

    private void initWebView() {
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event instanceof FollowDetailEvent) {
            if (event.getId() == 0) {
                initTag((HeadLineModel) event.getObject());
            } else if (event.getId() == 1) {
                finish();
            } else if (event.getId() == 2) {
                data.setTop(1);
                tv_is_top.setText("取消置顶");
                img_is_top.setImageResource(R.drawable.genjin_btn_untop);
            } else if (event.getId() == 3) {
                data.setTop(0);
                tv_is_top.setText("置顶");
                img_is_top.setImageResource(R.drawable.genjin_btn_top);
            }
        }
    }

    @OnClick(R.id.base_act_right_lin)//APP信息
    public void onBtnWebView() {
        AbAppUtil.openBrowser(this, data.getEventData().getData().getUrl() == null ? "" : data.getEventData().getData().getUrl());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
