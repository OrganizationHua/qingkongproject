package com.iwangcn.qingkong.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.LoadFailEvent;
import com.iwangcn.qingkong.business.MessageListEvent;
import com.iwangcn.qingkong.net.NetConst;
import com.iwangcn.qingkong.sp.SpUtils;
import com.iwangcn.qingkong.ui.adapter.MessageListAdapter;
import com.iwangcn.qingkong.ui.adapter.QKTagAdapter;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.HelperFeedbackDetail;
import com.iwangcn.qingkong.ui.model.HelperListModel;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.iwangcn.qingkong.ui.view.freshwidget.RefreshListenerAdapter;
import com.iwangcn.qingkong.ui.view.freshwidget.ReloadRefreshLayout;
import com.iwangcn.qingkong.utils.AbDateUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 留言列表界面
 */
public class MessageListActivity extends QkBaseActivity {
    @BindView(R.id.news_title)
    TextView mNewsTitle;
    @BindView(R.id.news_from)
    TextView mNewsFrom;
    @BindView(R.id.news_time)
    TextView mNewsTime;
    @BindView(R.id.tag_flowlayout)
    public TagFlowLayout tagFlowLayout;//标签

    @BindView(R.id.home_list_news)
    RecyclerView mListView;
    @BindView(R.id.img_send)
    ImageView img_send;

    @BindView(R.id.tv_content)
    EditText tv_content;

    @BindView(R.id.mReloadRefreshView)
    ReloadRefreshLayout mReloadRefreshView;
    private MessageListAdapter mNewsAdapter;
    private List<HelperFeedbackDetail> mList = new ArrayList<>();
    private MessageListEvent helperEvent;
    private String autoId;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_message_list;
    }

    private HelperListModel helperInfo;

    @Override
    public void initView() {
        setTitle("留言");
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        helperInfo = (HelperListModel) getIntent().getSerializableExtra("message");
        autoId = helperInfo.getHelperProcess().getAutoId() + "";
        initTag(helperInfo);
        helperEvent = new MessageListEvent(this, autoId);
        helperEvent.getRefreshEventList();
        mNewsAdapter = new MessageListAdapter(this, mList, helperEvent);
        mListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mNewsAdapter);
        mReloadRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(ReloadRefreshLayout refreshLayout) {
                mReloadRefreshView.setEnableRefresh(true);
                helperEvent.getRefreshEventList();
            }

            @Override
            public void onLoadMore(ReloadRefreshLayout refreshLayout) {
                helperEvent.getMoreEvent();
            }
        });
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof MessageListEvent) {
            Log.e("fjg", "====");
            if (helperEvent.getId() == 0) {
                mReloadRefreshView.finishRefreshing();
                List<HelperFeedbackDetail> list = new ArrayList<>();
                if (event.getObject() != null) {

                    list = (List<HelperFeedbackDetail>) event.getObject();
                }
                if (list.size() < NetConst.page) {//如果小于page条表示加载完成不能加载更多
                    mReloadRefreshView.finishLoadmore();
                }
                if (event.isMore()) {
                    mReloadRefreshView.setEnableLoadmore(false);
                } else {
                    mReloadRefreshView.setEnableRefresh(true);
                    mList.clear();

                }
                mList.addAll(list);
                mNewsAdapter.notifyDataSetChanged();
            } else if (helperEvent.getId() == 1) {//提交留言
                helperEvent.getRefreshEventList();
            }
        } else if (event instanceof LoadFailEvent) {
            mReloadRefreshView.finishRefreshing();
            mReloadRefreshView.finishLoadmore();
        }
    }

    @OnClick(R.id.img_send)//APP信息
    public void onSendMessage() {
        helperEvent.commitMessage(autoId, tv_content.getText().toString().trim());
        tv_content.setText("");
    }

    public void initTag(HelperListModel helperInfo) {
        if (helperInfo != null) {

            mNewsTitle.setText(helperInfo.getHelperInfo().getTitle());
            mNewsFrom.setText(helperInfo.getHelperInfo().getSource());
            mNewsTime.setText(AbDateUtil.formatDateStrGetDay(helperInfo.getHelperInfo().getUpdateTime()));
            List<QkTagModel> list = new ArrayList<>();
            list.add(new QkTagModel(0, (String) SpUtils.get(this, helperInfo.getHelperInfo().getDataType() + "", "1")));

            if (helperInfo.getHelperProcess().getBusinessLabels() != null && helperInfo.getHelperProcess().getBusinessLabels().size() != 0) {
                for (int i = 0; i < helperInfo.getHelperProcess().getBusinessLabels().size(); i++) {
                    if (!TextUtils.isEmpty(helperInfo.getHelperProcess().getBusinessLabels().get(i))) {
                        list.add(new QkTagModel(2, helperInfo.getHelperProcess().getBusinessLabels().get(i)));
                    }
                }
            }
            if (helperInfo.getHelperProcess().getSelfLabels() != null && helperInfo.getHelperProcess().getSelfLabels().size() != 0) {
                for (int j = 0; j < helperInfo.getHelperProcess().getSelfLabels().size(); j++) {
                    if (!TextUtils.isEmpty(helperInfo.getHelperProcess().getSelfLabels().get(j))) {
                        list.add(new QkTagModel(3, helperInfo.getHelperProcess().getSelfLabels().get(j)));
                    }
                }
            }

            tagFlowLayout.setAdapter(new QKTagAdapter(this, list));

        }
    }

}
