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
import com.iwangcn.qingkong.business.TagEvent;
import com.iwangcn.qingkong.net.BaseSubscriber;
import com.iwangcn.qingkong.net.ExceptionHandle;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.ui.view.TagWidget.MoreRecycleViewTagAdapter;
import com.iwangcn.qingkong.ui.view.TagWidget.OnRecyclerItemClickListener;
import com.iwangcn.qingkong.ui.view.TagWidget.RecycleViewItemTouchCallback;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.iwangcn.qingkong.utils.VibratorUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private boolean isActivated = false;//界面是否激活
    private TagEvent mTagEvent;
    private MoreRecycleViewTagAdapter mAdapter;
    private Context mContext = this;
    private ArrayList<ArrayList<ClientLabel>> mList;
    private long autoId;//事件ID
    private long newsId;
    private int position;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_tag;
    }

    @Override
    public void initView() {
        setTitle("更多业务标签");
        setRightImg(R.drawable.toutiao_btn_edit);
        Intent intent = getIntent();
        autoId = intent.getLongExtra("autoId", 0);
        newsId = intent.getLongExtra("newsInfoAutoId", 0);
        position = intent.getIntExtra("position", 0);
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
        //switchActivated();
    }

    @OnClick(R.id.btn_sure)
    public void onClickBtnSure() {
        final FollowDetailEvent followDetailEvent = new FollowDetailEvent(mContext);
        List<ClientLabel> finalRecommendList = new ArrayList<>();
        if (mList.get(0) != null) {
            finalRecommendList = mList.get(0);
        }
        List<ClientLabel> finalmyListList = new ArrayList<>();
        if (mList.get(1) != null) {
            finalmyListList = mList.get(1);
        }
        followDetailEvent.doFollowEvent(String.valueOf(autoId), String.valueOf(newsId), finalRecommendList, finalmyListList, new BaseSubscriber(true) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                ToastUtil.showToast(mContext, e.codeMessage);
            }

            @Override
            public void onNext(Object o) {
                ToastUtil.showToast(mContext, "已跟进");
                Intent intent = new Intent();
                intent.putExtra("position", position);
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
            } else if(event.getId()==TagEvent.TAG_GETLIST){
                mList = (ArrayList<ArrayList<ClientLabel>>) event.getObject();
                mAdapter.setDataList(mList);
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
                /*激活界面*/
                isActivated = true;
                setRightTitle("完成");

                int pos = vh.getLayoutPosition();
                if (pos > mAdapter.getOneTitlePosition() && pos < mAdapter.getTwoTitlePosition()) {//
                    recommendItemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(MoreTagEditActivity.this, 100);
                } else if (pos > mAdapter.getTwoTitlePosition() && pos < mAdapter.getThreeTitlePosition()) {
//                    if (mAdapter.isEditing) return;
//                    mAdapter.isEditing = true;
//                    mAdapter.notifyItemRangeChanged(mAdapter.getTwoTitlePosition() + 1, mAdapter.getTwoContentItemCount());
                } else if (pos > mAdapter.getThreeTitlePosition() && pos < (mAdapter.getThreeTitlePosition() + mAdapter.getThreeContentItemCount() + 1)) {
                    if (mAdapter.isEditing) return;
                    mAdapter.isEditing = true;
                    mAdapter.notifyItemRangeChanged(mAdapter.getThreeTitlePosition() + 1, mAdapter.getThreeContentItemCount());
                }

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
