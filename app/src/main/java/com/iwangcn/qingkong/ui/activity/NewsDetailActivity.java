package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.adapter.NewsDetailPageAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.NewsInfo;
import com.iwangcn.qingkong.ui.view.TagWidget.TagModel;
import com.iwangcn.qingkong.utils.AbAppUtil;
import com.iwangcn.qingkong.utils.PopupWindowUtil;
import com.iwangcn.qingkong.utils.ToastUtil;

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

    @Override
    public int layoutChildResID() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.news_detail));
        setRightTitle(getString(R.string.originalText));

        mList = (List<NewsInfo>) getIntent().getSerializableExtra("NewsInfoList");
        currentPosition = getIntent().getIntExtra("frontPageposition", 0);
        FragmentManager fm = getSupportFragmentManager();
        mAdapter = new NewsDetailPageAdapter(fm);
        mAdapter.setList(mList);
        //绑定自定义适配器
        mViewPage.setAdapter(mAdapter);
        mViewPage.setCurrentItem(currentPosition);
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
        NewsInfo newsInfo=mList.get(mViewPage.getCurrentItem());
        if(!TextUtils.isEmpty(newsInfo.getUrl())){
            AbAppUtil.openBrowser(this, newsInfo.getUrl());
        }
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
}
