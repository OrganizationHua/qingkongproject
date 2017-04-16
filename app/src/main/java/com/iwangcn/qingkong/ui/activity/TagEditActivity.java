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
import com.iwangcn.qingkong.ui.base.QkBaseActivity;
import com.iwangcn.qingkong.ui.view.TagWidget.OnRecyclerItemClickListener;
import com.iwangcn.qingkong.ui.view.TagWidget.RecycleViewItemTouchCallback;
import com.iwangcn.qingkong.ui.view.TagWidget.RecycleViewTagAdapter;
import com.iwangcn.qingkong.utils.ToastUtil;
import com.iwangcn.qingkong.utils.VibratorUtil;

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

    @Override
    public int layoutChildResID() {
        return R.layout.activity_tag;
    }

    @Override
    public void initView() {
        setTitle("筛选");
        setRightTitle("编辑");
        initRecommend();
    }

    private void initRecommend() {

        recycle_recommend = (RecyclerView) findViewById(R.id.recycle_recommend);
        ll_sure = (LinearLayout) findViewById(R.id.ll_sure);
        FlexboxLayoutManager recommendLayoutManager = new FlexboxLayoutManager();
        recommendLayoutManager.setFlexWrap(FlexWrap.WRAP);
        recommendLayoutManager.setFlexDirection(FlexDirection.ROW);
        recommendLayoutManager.setAlignItems(AlignItems.STRETCH);
        recycle_recommend.setLayoutManager(recommendLayoutManager);
        final RecycleViewTagAdapter adapter = new RecycleViewTagAdapter(this);
        recycle_recommend.setAdapter(adapter);

        final ItemTouchHelper recommendItemTouchHelper = new ItemTouchHelper(
                new RecycleViewItemTouchCallback(adapter).setOnDragListener(this));
        recommendItemTouchHelper.attachToRecyclerView(recycle_recommend);

        recycle_recommend.addOnItemTouchListener(new OnRecyclerItemClickListener(recycle_recommend) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {

                int pos = vh.getLayoutPosition();
                if (pos > adapter.getOneTitlePosition() && pos < adapter.getTwoTitlePosition()) {
                    recommendItemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(TagEditActivity.this, 100);
                } else if (pos > adapter.getTwoTitlePosition() && pos < adapter.getThreeTitlePosition()) {
//                    if (adapter.isEditing) return;
//                    adapter.isEditing = true;
//                    adapter.notifyItemRangeChanged(adapter.getTwoTitlePosition() + 1, adapter.getTwoContentItemCount());
                } else if (pos > adapter.getThreeTitlePosition() && pos < (adapter.getThreeTitlePosition() + adapter.getThreeContentItemCount() + 1)) {
                    if (adapter.isEditing) return;
                    adapter.isEditing = true;
                    adapter.notifyItemRangeChanged(adapter.getThreeTitlePosition() + 1, adapter.getThreeContentItemCount());
                }

            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int pos = vh.getLayoutPosition();
                if (pos == adapter.getThreeContentItemCount() + adapter.getThreeTitlePosition() + 1) {
                    ToastUtil.showToast(TagEditActivity.this, "last");
                    adapter.isAdd = true;
                    adapter.notifyItemChanged(vh.getLayoutPosition());

//                      adapter.results3.add("新增");
//                      adapter.notifyItemInserted(vh.getLayoutPosition());
//                      adapter.notifyItemRangeChanged(vh.getLayoutPosition()+1,adapter.getItemCount()-vh.getLayoutPosition());
                }
                if (adapter.isEditing && pos > adapter.getThreeTitlePosition() && pos < adapter.getThreeTitlePosition() + adapter.getThreeContentItemCount() + 1) {
                    adapter.results3.remove(pos - adapter.getThreeTitlePosition() - 1);
                    adapter.notifyItemRemoved(pos);
                }
            }
        });
    }


    @Override
    public void onFinishDrag() {

    }
}
