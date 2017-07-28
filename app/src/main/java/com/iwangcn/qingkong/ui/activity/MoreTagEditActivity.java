/*
 * Copyright 2017 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iwangcn.qingkong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.FollowDetailEvent;
import com.iwangcn.qingkong.business.MoreTagEditEvent;
import com.iwangcn.qingkong.business.TagEvent;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.net.NetResponse;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.ui.model.QkTagModel;
import com.iwangcn.qingkong.ui.view.TagWidget.MoreRecycleViewTagAdapter;
import com.iwangcn.qingkong.ui.view.TagWidget.OnRecyclerItemClickListener;
import com.iwangcn.qingkong.ui.view.TagWidget.RecycleViewItemTouchCallback;
import com.iwangcn.qingkong.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Launcher Activity for the cat gallery demo app that demonstrates the usage of the
 * {@link FlexboxLayoutManager} that handles various sizes of views aligned nicely regardless of
 * the device width like the Google Photo app without loading all the images on the memory.
 * Thus compared to using the {@link FlexboxLayout}, it's much less likely to abuse the memory,
 * which some times leads to the OutOfMemoryError.
 */
public class MoreTagEditActivity extends QkBaseActivity implements RecycleViewItemTouchCallback.OnDragListener {
    private RecyclerView recycle_recommend;
    private LinearLayout ll_sure;
    private TagEvent mTagEvent;
    private MoreRecycleViewTagAdapter mAdapter;
    private Context mContext = this;
    private ArrayList<ArrayList<ClientLabel>> mList;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_tag;
    }

    @Override
    public void initView() {
        setTitle("更多业务标签");
        setRightImg(R.drawable.toutiao_btn_edit);

    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        mTagEvent = new TagEvent(this);
        initRecommend();
        mTagEvent.getTagList();
    }

    @OnClick(R.id.base_act_right_lin)
    public void onClickRightButton() {
        Intent intent = new Intent(this, MoreTagDeleteActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sure)
    public void onClickBtnSure() {
        List<ClientLabel> finalRecommendList = new ArrayList<>();
        if (mList.get(0) != null) {
            finalRecommendList = mList.get(0);
        }
        List<ClientLabel> finalmyListList = new ArrayList<>();
        if (mList.get(1) != null) {
            finalmyListList = mList.get(1);
        }
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);//0是跟进，1是头条跟进 2是助手跟进
//        if (type == 0) {
//            doFollowEvent(intent, finalRecommendList, finalmyListList);
//        } else if (type == 2) {
//
//        }
        if(type == 2){
            updateTags(intent, finalRecommendList, finalmyListList);
        }else{
            doFollowEvent(intent, finalRecommendList, finalmyListList,type);
        }
    }

    private void updateTags(Intent intent, List<ClientLabel> finalRecommendList, List<ClientLabel> finalmyListList) {
        long processId = intent.getLongExtra("processId", 0);
        int type = intent.getIntExtra("type", 0);
        MoreTagEditEvent moreTagEditEvent = new MoreTagEditEvent(this);
        moreTagEditEvent.updateLabels(type, String.valueOf(processId), finalRecommendList, finalmyListList, new BaseSubscriber(false) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(Object o) {
                finish();
            }
        });
    }

    /**
     * 头条跟进
     *
     * @param intent
     */
    private void doFollowEvent(Intent intent, final List<ClientLabel> finalRecommendList, final List<ClientLabel> finalmyListList,int type) {
        long eventId = intent.getLongExtra("eventId", 0);
        long newsId = intent.getLongExtra("newsInfoAutoId", 0);
        final FollowDetailEvent followDetailEvent = new FollowDetailEvent(mContext);
        followDetailEvent.doFollowEvent(String.valueOf(eventId), String.valueOf(newsId), finalRecommendList, finalmyListList, type,new BaseSubscriber<NetResponse>(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(NetResponse o) {
                ToastUtil.showToast(mContext, "已跟进");
                Intent intent = new Intent();
                intent.putExtra("finalRecommendList", (Serializable) finalRecommendList);
                intent.putExtra("finalmyListList", (Serializable) finalmyListList);
                if (!TextUtils.isEmpty(o.getData())) {
                    intent.putExtra("autoId", o.getData());
                }
                setResult(RESULT_OK, intent);
                finish();
                //EventBus.getDefault().post(followDetailEvent);
            }
        });
    }

    @OnClick(R.id.btn_cancel)
    public void onClickBtnCancle() {
        finish();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof TagEvent) {
            if (event.getId() == TagEvent.TAG_DELETE) {
                mTagEvent.getTagList(false);
            } else if (event.getId() == TagEvent.TAG_GETLIST) {
                mList = (ArrayList<ArrayList<ClientLabel>>) event.getObject();
                setSelectList();
                mAdapter.setDataList(mList);
            }
        }
    }

    /**
     * 设置回显
     */
    private void setSelectList() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type == 2) {
            List<QkTagModel> list = (List<QkTagModel>) intent.getSerializableExtra("qkTagList");
            if (list == null) {
                return;
            }
            for (int i = 0; i < mList.size(); i++) {
                ArrayList<ClientLabel> tempList = mList.get(i);
                for (ClientLabel clientLabel : tempList) {
                    for (QkTagModel qkTagModel : list
                            ) {
                        if (qkTagModel.getTagText().equals(clientLabel.getName())) {
                            clientLabel.setSelect(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void initRecommend() {

        recycle_recommend = (RecyclerView) findViewById(R.id.recycle_recommend);
        ll_sure = (LinearLayout) findViewById(R.id.ll_sure);
        FlexboxLayoutManager recommendLayoutManager = new FlexboxLayoutManager();
        recommendLayoutManager.setFlexWrap(FlexWrap.WRAP);
        recommendLayoutManager.setFlexDirection(FlexDirection.ROW);
        recommendLayoutManager.setAlignItems(AlignItems.STRETCH);
        recycle_recommend.setLayoutManager(recommendLayoutManager);
        mAdapter = new MoreRecycleViewTagAdapter(this);
        recycle_recommend.setAdapter(mAdapter);

        final ItemTouchHelper recommendItemTouchHelper = new ItemTouchHelper(
                new RecycleViewItemTouchCallback(mAdapter).setOnDragListener(this));
        recommendItemTouchHelper.attachToRecyclerView(recycle_recommend);

        recycle_recommend.addOnItemTouchListener(new OnRecyclerItemClickListener(recycle_recommend) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();

                if (position > mAdapter.getOneTitlePosition() && position < mAdapter.getTwoTitlePosition()) {
                    ClientLabel clientLabel = mList.get(0).get(position - 1);
                    clientLabel.setSelect(!clientLabel.isSelect());
                    setIsAddTag(false);
                    mAdapter.setDataList(mList);
                } else if (position > mAdapter.getTwoTitlePosition() && position < mAdapter.getTwoTitlePosition() + mAdapter.getTwoContentItemCount() + 1) {
                    ClientLabel clientLabel = mList.get(1).get(position - mAdapter.getTwoTitlePosition() - 1);
                    clientLabel.setSelect(!clientLabel.isSelect());
                    setIsAddTag(false);
                    mAdapter.setDataList(mList);
                } else if (position == mAdapter.getTwoTitlePosition() + mAdapter.getTwoContentItemCount() + 1) {
                    setIsAddTag(true);
                    mAdapter.notifyItemChanged(vh.getLayoutPosition());
                } else {
                    setIsAddTag(false);
                    mAdapter.notifyItemChanged(vh.getLayoutPosition());
                }
            }
        });
        mAdapter.setEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {

                if (actionId >= EditorInfo.IME_ACTION_DONE && actionId <= 7) {
                    // ClientLabel label = v.getText().toString();
                    final String string = v.getText().toString();
                    if (TextUtils.isEmpty(string)) {
                        ToastUtil.showToast(mContext, "还没有输入新标签");
                        return true;
                    }
                    mTagEvent.submitTags(string, new BaseSubscriber(true) {
                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable e) {
                            ToastUtil.showToast(mContext, e.codeMessage);
                        }

                        @Override
                        public void onNext(Object o) {
                            ToastUtil.showToast(mContext, "已经提交");
                            ClientLabel clientLabel = new ClientLabel();
                            clientLabel.setName(string);
                            ArrayList<ClientLabel> clientLabels = new ArrayList<ClientLabel>();
                            if (mList.get(1) != null) {
                                mList.get(1).add(clientLabel);
                            } else {
                                clientLabels.add(clientLabel);
                                mList.set(1, clientLabels);
                            }
                            setIsAddTag(false);
                            mAdapter.setDataList(mList);
                            v.setText("");
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }

    private void setIsAddTag(boolean isAdd) {
        mAdapter.isAdd = isAdd;
        if (isAdd) {
            ll_sure.setVisibility(View.GONE);
        } else {
            ll_sure.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFinishDrag() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
