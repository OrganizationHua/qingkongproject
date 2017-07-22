package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.business.NewsSearchEvent;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.ui.adapter.SearchHistoryAdapter;
import com.iwangcn.qingkong.ui.adapter.SearchResultAdapter;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.EventDataVo;
import com.iwangcn.qingkong.ui.model.SearchResultVo;
import com.iwangcn.qingkong.ui.view.ClearEditText;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;
import com.iwangcn.qingkong.utils.AbAppUtil;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索界面
 */
public class NewsSearchActivity extends BaseActivity {
    @BindView(R.id.edit_search)
    ClearEditText mClearEditText;//搜索框
    @BindView(R.id.search_no_result_sum)
    LinearLayout mLinNoResult;//搜索无结果
    @BindView(R.id.search_btn_no_result)
    Button mBtnHelper;//小助手按钮
    @BindView(R.id.serch_listView_result)
    ListView mListViewResult;//搜索结果
    @BindView(R.id.serch_listView_history)
    ListView mListViewHistory;//历史搜索
    @BindView(R.id.serch_listView_hot)
    ListView mListViewHot;//大家都在搜

    @BindView(R.id.mPullRefreshView)
    ReloadRefreshLayout mAbPullToRefreshView;//上拉加载更多


    private List<String> mListHistory;
    private List<SearchResultVo> mListResult = new ArrayList<SearchResultVo>();
    private List<String> mListHot = new ArrayList<>();
    private SearchHistoryAdapter mHotAdapter;//热搜
    private SearchHistoryAdapter mAdapterHistory;//历史搜索
    private SearchResultAdapter mAdapterResult;//搜索结果
    private Context mContext = this;
    private NewsSearchEvent mSearchEvent;
    private String strKeyWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @OnClick(R.id.base_act_left_lin)//返回按钮
    public void goBack() {
        onBackPressed();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        mSearchEvent = new NewsSearchEvent(this);
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mListViewHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 历史搜索历史
     */
    private void initData() {
        initHistory();
        initListViewHot();
        initListSerchResult();
        mClearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchList(mClearEditText.getText().toString().trim());
                }
                return false;
            }
        });
    }

    /**
     * 初始化历史搜索
     */
    private void initHistory() {
        mListHistory = mSearchEvent.getListHistory();
        mAdapterHistory = new SearchHistoryAdapter(this);
        mAdapterHistory.setDataList(mListHistory);
        SwingLeftInAnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(mAdapterHistory);
        animAdapter.setAbsListView(mListViewHistory);
        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(300);
        mListViewHistory.setAdapter(animAdapter);
        mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mClearEditText.setText(mListHistory.get(position));
            }
        });
    }

    /**
     * 大家都在搜
     */
    private void initListViewHot() {
        mHotAdapter = new SearchHistoryAdapter(this);
        mHotAdapter.setDataList(mListHot);
        SwingBottomInAnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(mHotAdapter);
        animAdapter.setAbsListView(mListViewHot);
        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(300);
        mListViewHot.setAdapter(animAdapter);
        mListViewHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mClearEditText.setText(mListHot.get(position));
                AbAppUtil.closeSoftInput(mContext);
            }
        });
    }

    /**
     * 搜索结果
     */
    private void initListSerchResult() {
        mAdapterResult = new SearchResultAdapter(this);
        mAdapterResult.setDataList(mListResult);
        SwingLeftInAnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(mAdapterResult);
        animAdapter.setAbsListView(mListViewResult);
        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(300);
        mListViewResult.setAdapter(animAdapter);
        mListViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //跟进和未跟进分别处理
                SearchResultVo searchResultVo = mListResult.get(i);
                if (searchResultVo.isFollowUp()) {//已跟进
                    Intent intent = new Intent(mContext, FollowDetailActivity.class)
                            .putExtra("data", mSearchEvent.searchResultVoToHeadLineModel(searchResultVo))
                            .putExtra("type", 1);
                    startActivity(intent);
                } else {//未跟进
                    ArrayList<EventDataVo> list = new ArrayList<EventDataVo>();
                    list.add(mSearchEvent.searchResultVoToNewsInfo(mListResult.get(i)));
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra("eventDataVoList", (Serializable) list);
                    intent.putExtra("autoId", mListResult.get(i).getEventId());//事件ID
                    startActivity(intent);
                }
            }
        });
        mAbPullToRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                //搜索加载更多
                super.onLoadMore(refreshLayout);
                mSearchEvent.getMoreEvent(strKeyWord);
            }
        });
    }

    @OnClick(R.id.base_act_right_lin)//搜索按钮
    public void onBtnSearch(View v) {
        mListViewHistory.setVisibility(View.GONE);
        mLinNoResult.setVisibility(View.GONE);
        mListViewResult.setVisibility(View.VISIBLE);
        getSearchList(mClearEditText.getText().toString().trim());
    }

    /**
     * 网络搜索
     *
     * @param keyword
     */
    private void getSearchList(String keyword) {
        strKeyWord = keyword;
        mSearchEvent.getRefreshEventList(keyword);
        AbAppUtil.closeSoftInput(mContext);
        //保存历史记录
        if (!TextUtils.isEmpty(mClearEditText.getText().toString().trim())) {
            mSearchEvent.saveSearchHistory(mClearEditText.getText().toString().trim());
        }
    }

    @OnClick(R.id.search_btn_no_result)//搜索按钮
    public void btnSearch() {
        mSearchEvent.noticeHelper(mClearEditText.getText().toString().trim(), new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, "通知小助手失败");
            }

            @Override
            public void onNext(Object o) {
                ToastUtil.showToast(mContext, "已通知小助手");
                mBtnHelper.setText(getString(R.string.search_helper_finish));
                mBtnHelper.setClickable(false);
                mBtnHelper.setBackground(getResources().getDrawable(R.drawable.search_button_helper_finish));
            }
        });

    }

    /**
     * 搜索无结果
     */
    private void searchNoResult() {
        mLinNoResult.setVisibility(View.VISIBLE);
        mBtnHelper.setText(getString(R.string.search_notify_helper));
        mBtnHelper.setClickable(true);
        mBtnHelper.setBackground(getResources().getDrawable(R.drawable.login_button_bg));
        mSearchEvent.getHotList(new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {
                mListHot = (List<String>) o;
                mHotAdapter.setDataList(mListHot);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof NewsSearchEvent) {
            List<SearchResultVo> list = (List<SearchResultVo>) event.getObject();
            if (list.size() == 0 && mListResult.size() == 0) {//表示没有搜到
                searchNoResult();
                return;
            }
            if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                mAbPullToRefreshView.setEnableLoadmore(false);
            }
            if (event.isMore()) {
                mAbPullToRefreshView.finishLoadmore();
            } else {
                mListResult.clear();
            }
            mListResult.addAll(list);
            mAdapterResult.setDataList(mListResult);
        } else if (event instanceof LoadFailEvent) {
            mAbPullToRefreshView.finishLoadmore();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
