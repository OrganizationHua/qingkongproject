package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.FollowDetailEvent;
import com.iwangcn.qingkong.business.TagEvent;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.ui.adapter.NewsDetailPageAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.ui.model.LabelError;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.iwangcn.qingkong.utils.AbAppUtil;
import com.iwangcn.qingkong.utils.PopupWindowUtil;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 头条详情界面
 */
public class NewsDetailActivity extends QkBaseActivity {
    @BindView(R.id.viewpager)
    public ViewPager mViewPage;
    private NewsDetailPageAdapter mAdapter;
    private List<NewsInfo> mList;
    private Context mContext = this;
    private int currentPosition = 0;
    private long autoId;//事件ID
    private TagEvent mTagEvent;
    private List<LabelError> errorTagList = new ArrayList<LabelError>();
    private int REQUEST_CODE = 10;
    @BindView(R.id.news_detail_follow_lin)
    LinearLayout mLinFollow;//跟进按钮
    private PopupWindow mPopupWindow;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.news_detail));
        setRightTitle(getString(R.string.originalText));
        mTagEvent = new TagEvent(this);
        mList = (List<NewsInfo>) getIntent().getSerializableExtra("NewsInfoList");
        currentPosition = getIntent().getIntExtra("frontPageposition", 0);
        autoId = getIntent().getLongExtra("autoId", 0);
        FragmentManager fm = getSupportFragmentManager();
        mAdapter = new NewsDetailPageAdapter(fm);
        mAdapter.setList(mList);
        //绑定自定义适配器
        mViewPage.setAdapter(mAdapter);
        mViewPage.setCurrentItem(currentPosition);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        mLinTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityResult();
            }
        });
    }

    @OnClick(R.id.base_act_right_lin)//APP信息
    public void onBtnWebView(View view) {
        NewsInfo newsInfo = mList.get(mViewPage.getCurrentItem());
        if (!TextUtils.isEmpty(newsInfo.getUrl())) {
            AbAppUtil.openBrowser(this, newsInfo.getUrl());
        }
    }

    @OnClick(R.id.news_detail_wrong_lin)//我要报错
    public void onBtnWrong(final View v) {
        mTagEvent.getErrorLabels(new BaseSubscriber<NetResponse<LabelError>>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(NetResponse<LabelError> o) {
                if (o.getDataList() != null && o.getDataList().size() != 0) {
                    errorTagList = o.getDataList();
                    showPopupWindow(v, errorTagList);
                }
            }
        });

    }

    // 右上角弹窗
    private void showPopupWindow(View parent, final List<LabelError> listData) {

        PopupWindowUtil.showPopupErrorWindow(this, parent, mRelMak, listData, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTagEvent.reportLabels(String.valueOf(autoId), listData);
            }
        });
    }

    @OnClick(R.id.news_detail_follow_lin)//跟进
    public void onBtnFollow(View v) {
        mTagEvent.getTagList();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setActivityResult();
        }
        return false;
    }

    private void setActivityResult() {
        Intent intent = new Intent();
        intent.putExtra("position", mViewPage.getCurrentItem());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof TagEvent) {
            if (event.getId() == TagEvent.TAG_GETLIST) {
                ArrayList<ArrayList<ClientLabel>> list = (ArrayList<ArrayList<ClientLabel>>) event.getObject();
                List<ClientLabel> recommendList = new ArrayList<>();
                List<ClientLabel> myList = new ArrayList<>();
                boolean isMore = false;
                if (list.get(0) != null && list.get(1) != null) {
                    if (list.get(0).size() > 6 || list.get(1).size() > 3) {
                        isMore = true;
                    }
                }
                if (list.get(0) != null) {
                    recommendList = list.get(0);
                    if (recommendList.size() >= 6) {
                        recommendList = recommendList.subList(0, 6);
                    }
                }
                if (list.get(1) != null) {
                    myList = list.get(1);
                    if (myList.size() >= 3) {
                        myList = myList.subList(0, 3);
                    }
                }

                final List<ClientLabel> finalRecommendList = recommendList;
                final List<ClientLabel> finalmyListList = myList;
                mPopupWindow = PopupWindowUtil.showMorePopupWindow(this, mLinFollow, mRelMak, recommendList, myList, isMore, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewsInfo newsInfo = mList.get(mViewPage.getCurrentItem());
                        final FollowDetailEvent followDetailEvent = new FollowDetailEvent(mContext);
                        followDetailEvent.doFollowEvent(String.valueOf(autoId), String.valueOf(newsInfo.getAutoId()), finalRecommendList, finalmyListList, new BaseSubscriber(true) {
                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                ToastUtil.showToast(mContext, e.codeMessage);
                            }

                            @Override
                            public void onNext(Object o) {
                                ToastUtil.showToast(mContext, "已跟进");
                                mList.remove(mViewPage.getCurrentItem());
                                mAdapter.setList(mList);
                                EventBus.getDefault().post(followDetailEvent);

                            }
                        });
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        NewsInfo newsInfo = mList.get(mViewPage.getCurrentItem());
                        Intent intent = new Intent(mContext, MoreTagEditActivity.class);
                        intent.putExtra("autoId", autoId);
                        intent.putExtra("newsInfoAutoId", newsInfo.getAutoId());
                        intent.putExtra("type", 0);
                        startActivityForResult(intent, REQUEST_CODE);

                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                mList.remove(mViewPage.getCurrentItem());
                mAdapter.setList(mList);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
