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
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwangcn.qingkong.R;
import com.iwangcn.qingkong.ui.model.ClientLabel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter class that handles the data set with the {@link RecyclerView.LayoutManager}
 */
public class MoreRecycleViewTagAdapter extends BaseMultipleItemAdapter implements RecycleViewItemTouchCallback.ItemTouchAdapter {
    public boolean isEditing = false;
    public boolean isAdd = false;
    public static List<ClientLabel> results1 = new ArrayList<>();
    public static List<ClientLabel> results2 = new ArrayList<>();
    private Context mContext;
    private TextView.OnEditorActionListener editorActionListener;
    public MoreRecycleViewTagAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setEditorActionListener(TextView.OnEditorActionListener editorActionListener) {
        this.editorActionListener = editorActionListener;
    }

    public void setDataList(ArrayList<ArrayList<ClientLabel>> list) {
        if (list.get(0) != null) {
            results1 = list.get(0);
        }
        if (list.get(1) != null) {
            results2 = list.get(1);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ONE_TITLE) {
            return onCreateHeaderView(parent);
        } else if (viewType == ITEM_TYPE_ONE_CONTENT) {
            return onCreateContentView(parent);
        } else if (viewType == ITEM_TYPE_TWO_TITLE) {
            return onCreateHeaderView(parent);
        } else if (viewType == ITEM_TYPE_TWO_CONTENT) {
            return onCreateContentView(parent);
        } else if (viewType == ITEM_TYPE_THREE_TITLE) {
            return onCreateHeaderView(parent);
        } else if (viewType == ITEM_TYPE_THREE_CONTENT) {
            return onCreateContentView(parent);
        } else if (viewType == ITEM_TYPE_LAST) {
            return onCreateLastView(parent);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("",position+" "+getOneTitlePosition()+" "+getTwoTitlePosition()+"..");
        if (position == 0) {//头部View
            return ITEM_TYPE_ONE_TITLE;
        } else if (position > getOneTitlePosition() && position < getTwoTitlePosition()) {
            return ITEM_TYPE_ONE_CONTENT;
        } else if (position == getTwoTitlePosition()) {
              return ITEM_TYPE_TWO_TITLE;
        } else if (position > getTwoTitlePosition() && position < getTwoTitlePosition() + getTwoContentItemCount()+1) {
            return ITEM_TYPE_TWO_CONTENT;
        }else{
            return ITEM_TYPE_LAST;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            if (position == getOneTitlePosition()) {
                ((HeaderViewHolder) holder).bindTo("推荐标签");
            } else if (position == getTwoTitlePosition()) {
                ((HeaderViewHolder) holder).bindTo("自定义标签");
            }

        } else if (holder instanceof FlexboxViewHolder) {
            if (position > getOneTitlePosition() && position < getTwoTitlePosition()) {
                ((FlexboxViewHolder) holder).bindTo(results1.get(position - getOneTitlePosition() - 1), mContext,1);
            } else if (position > getTwoTitlePosition() && position < getThreeTitlePosition()) {
                ((FlexboxViewHolder) holder).bindTo(results2.get(position - getTwoTitlePosition() - 1), mContext,2);
            }
        } else if (holder instanceof LastViewHolder) {
            ((LastViewHolder) holder).bindTo(editorActionListener,isAdd,mContext);
        }
    }

    @Override
    public int getItemCount() {
        return getTwoTitlePosition() + getTwoContentItemCount() + 1+1;
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
        return 0;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (toPosition == 0 || toPosition >= getTwoTitlePosition()) return;
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results1, i - 1, i);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {

                Collections.swap(results1, i - 1, i - 2);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results1.remove(position);
        notifyItemRemoved(position);
    }
}
