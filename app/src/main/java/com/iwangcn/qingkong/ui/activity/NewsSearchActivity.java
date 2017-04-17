package com.iwangcn.qingkong.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.dao.model.SearchModel;
import com.iwangcn.qingkong.ui.adapter.SearchHistoryAdapter;
import com.iwangcn.qingkong.ui.adapter.SearchResultAdapter;
import com.iwangcn.qingkong.ui.base.BaseActivity;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.iwangcn.qingkong.ui.view.ClearEditText;

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
    ClearEditText mClearEditText;
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

    private List<SearchModel> mListHistory = new ArrayList<SearchModel>();
    private List<NewsInfo> mListResult = new ArrayList<NewsInfo>();
    private SearchHistoryAdapter mAdapterHistory;//历史搜索
    private SearchResultAdapter mAdapterResult;//搜索结果

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
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mListViewHistory.setVisibility(View.GONE);
                    searchNoResult();
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
        for (int i = 0; i < 10; i++) {
            SearchModel model = new SearchModel();
            model.setContent("新闻搜索历史记录");
            mListHistory.add(model);
        }
        mAdapterHistory = new SearchHistoryAdapter(this);
        mAdapterHistory.setDataList(mListHistory);
        mListViewHistory.setAdapter(mAdapterHistory);
        mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        initListViewHot();
    }

    /**
     * 大家都在搜
     */
    private void initListViewHot() {
        for (int i = 0; i < 10; i++) {
            SearchModel model = new SearchModel();
            model.setContent("新闻搜索历史记录");
            mListHistory.add(model);
        }
        mAdapterHistory = new SearchHistoryAdapter(this);
        mAdapterHistory.setDataList(mListHistory);
        mListViewHot.setAdapter(mAdapterHistory);
        mListViewHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    /**
     * 搜索结果
     */
    private void initListSerchResult() {
        for (int i = 0; i < 10; i++) {
            NewsInfo model = new NewsInfo();
            model.setTitle("新闻搜索历史记录");
            model.setPubtime(22222);
            model.setPubtime(1111);
            model.setContent("事件来源");
            mListResult.add(model);
        }
        mAdapterResult = new SearchResultAdapter(this);
        mAdapterResult.setDataList(mListResult);
        mListViewResult.setAdapter(mAdapterResult);
        mListViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @OnClick(R.id.base_act_right_lin)//搜索按钮
    public void onBtnSearch() {
        mListViewHistory.setVisibility(View.GONE);
        mLinNoResult.setVisibility(View.GONE);
        mListViewResult.setVisibility(View.VISIBLE);
        initListSerchResult();
    }

    @OnClick(R.id.search_btn_no_result)//搜索按钮
    public void btnSearch() {
        mBtnHelper.setText(getString(R.string.search_helper_finish));
        mBtnHelper.setBackground(getResources().getDrawable(R.drawable.search_button_helper_finish));
    }

    /**
     * 搜索无结果
     */
    private void searchNoResult() {
        mLinNoResult.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
