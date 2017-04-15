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

package com.iwangcn.qingkong.ui.view.TagWidget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.iwangcn.qingkong.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter class that handles the data set with the {@link RecyclerView.LayoutManager}
 */
public class RecycleViewTagAdapter extends BaseMultipleItemAdapter implements RecycleViewItemTouchCallback.ItemTouchAdapter {
    public boolean isEditing = false;

    public static List<String> results1 = new ArrayList<>();
    public static List<String> results2 = new ArrayList<>();
    public static List<String> results3 = new ArrayList<>();
    private Context mContext;

    public RecycleViewTagAdapter(Context context) {
        super(context);
        this.mContext=context;
        initData();
    }
    private void initData() {

        for(int i=0;i<15;i++){
            results1.add(1+"你敢吗");
            results2.add(2+"你敢吗");
            results3.add(3+"你敢吗");
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            if (position == getOneTitlePosition()) {
                ((HeaderViewHolder) holder).bindTo("推荐标签");
            } else if (position == getTwoTitlePosition()) {
                ((HeaderViewHolder) holder).bindTo("业务标签");
            } else if (position == getThreeTitlePosition()) {
                ((HeaderViewHolder) holder).bindTo("自定义标签");
            }

        } else if (holder instanceof FlexboxViewHolder) {
            if (position > getOneTitlePosition() && position < getTwoTitlePosition()) {
                ((FlexboxViewHolder) holder).bindTo(results1.get(position-getOneTitlePosition()-1), false);
            } else if (position > getTwoTitlePosition() && position < getThreeTitlePosition()) {
                ((FlexboxViewHolder) holder).bindTo(results2.get(position-getTwoTitlePosition()-1), isEditing);
            } else if (position > getThreeTitlePosition() && position <getThreeTitlePosition() + getThreeContentItemCount()) {
                ((FlexboxViewHolder) holder).bindTo(results3.get(position-getThreeTitlePosition()-1), false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {
        return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.viewholder_header, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        return new FlexboxViewHolder(mLayoutInflater.inflate(R.layout.viewholder_content, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateLastView(ViewGroup parent) {
        return new LastViewHolder(mLayoutInflater.inflate(R.layout.viewholder_last, parent, false));

    }

    @Override
    public int getOneTitlePosition() {
        return 0;
    }

    @Override
    public int getTwoTitlePosition() {

        return getOneContentItemCount() + 1;
    }

    @Override
    public int getThreeTitlePosition() {

        return getTwoTitlePosition() + getTwoContentItemCount() + 1;
    }

    @Override
    public int getOneContentItemCount() {
        return results1.size();
    }

    @Override
    public int getTwoContentItemCount() {
        return results2.size();
    }

    @Override
    public int getThreeContentItemCount() {
        return results3.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (toPosition == 0) return;
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results1, i-1, i);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {

                Collections.swap(results1, i-1, i - 2);
            }
        }
//        Collections.swap(results1,fromPosition,toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results1.remove(position);
        notifyItemRemoved(position);
    }
}
