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
import com.iwangcn.qingkong.ui.model.EventDataVo;
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
    private List<EventDataVo> mList;
    private Context mContext = this;
    private int currentPosition = 0;
    private TagEvent mTagEvent;
    private List<LabelError> errorTagList = new ArrayList<LabelError>();
    private int REQUEST_CODE = 10;
    @BindView(R.id.news_detail_follow_lin)
    LinearLayout mLinFollow;//跟进按钮
    private PopupWindow mPopupWindow;

    @BindView(R.id.news_detail_lin_hasfollow)
    LinearLayout mLinHasFollow;//已跟进布局
    @BindView(R.id.news_detail_lin_Nofollow)
    LinearLayout mLinNoFollow;//未跟进布局
    private FollowDetailEvent mFollowDetailEvent;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.news_detail));
        setRightTitle(getString(R.string.originalText));
        mTagEvent = new TagEvent(this);
        mFollowDetailEvent = new FollowDetailEvent(this);
        mList = (List<EventDataVo>) getIntent().getSerializableExtra("eventDataVoList");
        currentPosition = getIntent().getIntExtra("frontPageposition", 0);
        FragmentManager fm = getSupportFragmentManager();
        mAdapter = new NewsDetailPageAdapter(fm);
        mAdapter.setList(mList);
        //绑定自定义适配器
        mViewPage.setAdapter(mAdapter);
        mViewPage.setCurrentItem(currentPosition);
        EventBus.getDefault().register(this);
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventDataVo eventDataVo = mList.get(position);
                if (eventDataVo.isFollowup()) {
                    mLinHasFollow.setVisibility(View.VISIBLE);
                    mLinNoFollow.setVisibility(View.GONE);
                } else {
                    mLinHasFollow.setVisibility(View.GONE);
                    mLinNoFollow.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        NewsInfo newsInfo = mList.get(mViewPage.getCurrentItem()).getData();
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
                mTagEvent.reportLabels(String.valueOf(mList.get(mViewPage.getCurrentItem()).getAutoId()), listData);
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
                        EventDataVo eventDataVo = mList.get(mViewPage.getCurrentItem());
                        NewsInfo newsInfo = eventDataVo.getData();
                        final FollowDetailEvent followDetailEvent = new FollowDetailEvent(mContext);
                        followDetailEvent.doFollowEvent(String.valueOf(eventDataVo.getEventId()), String.valueOf(newsInfo.getAutoId()), finalRecommendList, finalmyListList, new BaseSubscriber(true) {
                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                ToastUtil.showToast(mContext, e.codeMessage);
                            }

                            @Override
                            public void onNext(Object o) {
                                if(mPopupWindow!=null&&mPopupWindow.isShowing()){
                                    mPopupWindow.dismiss();
                                    mPopupWindow=null;
                                }
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
                        EventDataVo eventDataVo = mList.get(mViewPage.getCurrentItem());
                        NewsInfo newsInfo = eventDataVo.getData();
                        Intent intent = new Intent(mContext, MoreTagEditActivity.class);
                        intent.putExtra("eventId", eventDataVo.getEventId());
                        intent.putExtra("newsInfoAutoId", newsInfo.getAutoId());
                        intent.putExtra("type", 0);
                        startActivityForResult(intent, REQUEST_CODE);

                    }
                });
            }
        }
    }

    @OnClick(R.id.ll_cancle_follow)
    public void clickCancel() {
        ToastUtil.showToast(this, "头条取消跟进");
        //mFollowDetailEvent.doCancleFollow(data.getAutoId() + "");
        mLinHasFollow.setVisibility(View.GONE);
        mLinNoFollow.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.ll_set_top)
    public void clickSetUpTop() {
        ToastUtil.showToast(this, "头条置顶");
//        if (!TextUtils.equals(data.getTop() + "", "0")) {
//            mFollowDetailEvent.doFollowSetUp(data.getAutoId() + "");
//        } else if (!TextUtils.equals(data.getTop() + "", "1")) {
//            mFollowDetailEvent.doFollowSetUpCancleTop(data.getAutoId() + "");
//        }
    }


    @OnClick(R.id.ll_processed_finished)
    public void clickProcess() {
        ToastUtil.showToast(this, "处理完成");
        // mFollowDetailEvent.doFollowDone(data.getAutoId() + "");

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
