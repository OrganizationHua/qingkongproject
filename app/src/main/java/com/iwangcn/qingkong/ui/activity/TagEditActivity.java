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

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.LinearLayout;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.business.Event;
import com.iwangcn.qingkong.business.TagEvent;
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.model.ClientLabel;
import com.iwangcn.qingkong.ui.view.TagWidget.OnRecyclerItemClickListener;
import com.iwangcn.qingkong.ui.view.TagWidget.RecycleViewItemTouchCallback;
import com.iwangcn.qingkong.ui.view.TagWidget.RecycleViewTagAdapter;
import com.iwangcn.qingkong.utils.VibratorUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * Launcher Activity for the cat gallery demo app that demonstrates the usage of the
 * {@link FlexboxLayoutManager} that handles various sizes of views aligned nicely regardless of
 * the device width like the Google Photo app without loading all the images on the memory.
 * Thus compared to using the {@link FlexboxLayout}, it's much less likely to abuse the memory,
 * which some times leads to the OutOfMemoryError.
 */
public class TagEditActivity extends QkBaseActivity implements RecycleViewItemTouchCallback.OnDragListener {
    private RecyclerView recycle_recommend;
    private LinearLayout ll_sure;
    private boolean isActivated = false;//界面是否激活
    private TagEvent mTagEvent;
    private RecycleViewTagAdapter mAdapter;

    @Override
    public int layoutChildResID() {
        return R.layout.activity_tag;
    }

    @Override
    public void initView() {
        setTitle("筛选");
        setRightTitle("编辑");

    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        mTagEvent = new TagEvent(this);
        initRecommend();
        mTagEvent.getTagList();
    }

    @OnClick(R.id.base_tv_right)
    public void onClickRightButton() {
        switchActivated();
    }

    private void switchActivated() {
        if (isActivated) {
            isActivated = false;
            setRightTitle("编辑");
//            ll_sure.setVisibility(View.GONE);
        } else {
            isActivated = true;
            setRightTitle("完成");
//            ll_sure.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof TagEvent) {
            ArrayList<ArrayList<ClientLabel>> list = (ArrayList<ArrayList<ClientLabel>>) event.getObject();
            mAdapter.setDataList(list);
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
        mAdapter = new RecycleViewTagAdapter(this);
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
                    VibratorUtil.Vibrate(TagEditActivity.this, 100);
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
                int pos = vh.getLayoutPosition();
                if (pos == mAdapter.getThreeContentItemCount() + mAdapter.getThreeTitlePosition() + 1) {//点击加号时
//                    ToastUtil.showToast(TagEditActivity.this, "last");
                    mAdapter.isAdd = true;
                    mAdapter.notifyItemChanged(vh.getLayoutPosition());

//                      mAdapter.results3.add("新增");
//                      mAdapter.notifyItemInserted(vh.getLayoutPosition());
//                      mAdapter.notifyItemRangeChanged(vh.getLayoutPosition()+1,mAdapter.getItemCount()-vh.getLayoutPosition());
                }
                if (mAdapter.isEditing && pos > mAdapter.getThreeTitlePosition() && pos < mAdapter.getThreeTitlePosition() + mAdapter.getThreeContentItemCount() + 1) {//点击自定义标签item时
                    mAdapter.results3.remove(pos - mAdapter.getThreeTitlePosition() - 1);
                    mAdapter.notifyItemRemoved(pos);
                }
            }
        });
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
